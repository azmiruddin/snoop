package tub.ods.pch.channel.state;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.google.common.base.Preconditions;

import papyrus.channel.Error;
import tub.ods.pch.channel.node.EthereumConfig;
import tub.ods.pch.channel.model.ChannelContract;
import tub.ods.pch.channel.model.ChannelManagerContract;
import tub.ods.pch.channel.state.ChannelProperties;
import tub.ods.pch.channel.BlockchainChannel;
import tub.ods.pch.channel.SignedChannelState;
import tub.ods.pch.channel.node.ContractsManager;
import tub.ods.pch.channel.node.ContractsManagerFactory;
import tub.ods.pch.channel.node.EthereumService;
import tub.ods.pch.channel.node.TokenService;
import tub.ods.pch.channel.PeerConnection;
import tub.ods.pch.channel.PeerConnectionManager;
import papyrus.channel.protocol.ChannelOpenedRequest;
import papyrus.channel.protocol.ChannelOpenedResponse;
import papyrus.channel.protocol.ChannelUpdateRequest;

@Component
public class OutgoingChannelCoordinator {
	private static final Logger log = LoggerFactory.getLogger(OutgoingChannelCoordinator.class);

	private final EthereumConfig ethereumConfig;
	private final OutgoingChannelRepository channelRepository;
	private final EthereumService ethereumService;
	private final ContractsManagerFactory contractsManagerFactory;
	private final PeerConnectionManager peerConnectionManager;

	private final Map<Address, ChannelCoordinator> coordinatorsMap = new ConcurrentHashMap<>();
	private final Set<ChannelCoordinator> coordinators = Collections.synchronizedSet(new HashSet<>());
	private final ContractsManager mainContractManager;

	@Autowired
	public OutgoingChannelCoordinator(EthereumConfig ethereumConfig, ContractsManagerFactory contractsManagerFactory,
			OutgoingChannelRepository channelRepository, EthereumService ethereumService,
			PeerConnectionManager peerConnectionManager) {
		this.ethereumConfig = ethereumConfig;
		this.contractsManagerFactory = contractsManagerFactory;
		this.channelRepository = channelRepository;
		this.ethereumService = ethereumService;
		this.peerConnectionManager = peerConnectionManager;
		mainContractManager = contractsManagerFactory.getContractManager(ethereumConfig.getMainAddress());
	}

	public void register(OutgoingChannelState channel, OutgoingChannelPolicy policy) {
		ChannelCoordinator coordinator = new ChannelCoordinator(channel, policy);
		Address address = channel.getChannelAddress();
		if (address != null) {
			if (coordinatorsMap.putIfAbsent(address, coordinator) == null) {
				coordinators.add(coordinator);
				coordinator.start();
			}
		} else {
			Preconditions.checkState(channel.getStatus() == OutgoingChannelState.Status.NEW);
			coordinators.add(coordinator);
			coordinator.start();
		}
	}

	public void setPolicy(OutgoingChannelState channel, OutgoingChannelPolicy policy) {
		if (channel.getChannelAddress() != null) {
			ChannelCoordinator coordinator = coordinatorsMap.get(channel.getChannelAddress());
			if (coordinator != null)
				coordinator.setPolicy(policy);
		} else {
			for (ChannelCoordinator coordinator : coordinators) {
				if (coordinator.channel == channel) {
					coordinator.setPolicy(policy);
					break;
				}
			}
		}
	}

	public Optional<OutgoingChannelState> getChannel(Address channelAddress) {
		return Optional.ofNullable(coordinatorsMap.get(channelAddress)).map(c -> c.channel);
	}

	public Stream<OutgoingChannelState> getByParticipants(Address senderAddress, Address receiverAddress) {
		return all().filter(c -> c.getChannel().getSenderAddress().equals(senderAddress))
				.filter(c -> c.getChannel().getReceiverAddress().equals(receiverAddress));
	}

	public OutgoingChannelState loadChannel(Address address) {
		ChannelContract contract = mainContractManager.load(ChannelContract.class, address);
		BlockchainChannel channel = BlockchainChannel.fromExistingContract(mainContractManager.channelManager(),
				contract);
		return new OutgoingChannelState(channel);
	}

	public Stream<OutgoingChannelState> all() {
		return coordinators.stream().map(c -> c.channel);
	}

	@PreDestroy
	public void destroy() throws InterruptedException {
		for (Thread thread : coordinatorsMap.values()) {
			thread.interrupt();
		}
	}

	private static String watcherName(OutgoingChannelState channel) {
		return "Channel watcher " + channel.getAddressSafe() + " from:" + channel.getChannel().getSenderAddress()
				+ " to:" + channel.getChannel().getReceiverAddress();
	}

	private class ChannelCoordinator extends Thread {
		final OutgoingChannelState channel;
		final TokenService tokenService;
		final Credentials credentials;
		final ChannelProperties channelProperties;
		final Address receiverAddress;
		final ContractsManager contractManager;
		volatile OutgoingChannelPolicy policy;
		BigInteger approvedDeposit;

		public ChannelCoordinator(OutgoingChannelState channel, OutgoingChannelPolicy policy) {
			super(watcherName(channel));
			this.channel = channel;
			this.policy = policy;
			Address senderAddress = channel.getChannel().getSenderAddress();
			contractManager = contractsManagerFactory.getContractManager(senderAddress);
			credentials = ethereumConfig.getCredentials(senderAddress);
			tokenService = contractManager.getTokenService();
			channelProperties = channel.getChannel().getProperties();
			receiverAddress = channel.getChannel().getReceiverAddress();
		}

		public void setPolicy(OutgoingChannelPolicy policy) {
			this.policy = policy;
		}

		@Override
		public void run() {
			while (!Thread.interrupted()) {
				try {
					boolean updated = false;
					OutgoingChannelState.Status status = channel.getStatus();
					try {
						if (channel.checkTransitionInProgress())
							continue;

						if (channel.isNeedsSync()) {
							// TODO make async call
							syncChannel();
						}

						if (!channel.isCloseRequested() && policy.isNone()) {
							log.info("No policy defined for channel {}, closing it", channel.getAddressSafe());
							channel.setNeedClose();
						} else {
							makeTransitions();
						}

						if (channel.getStatus() != status) {
							updated = true;
							save();
//							log.info("Outgoing channel:{} to receiver:{} updated from:{} to:{}", channel.getAddressSafe(), channel.getChannel().getReceiverAddress(), status, channel.getStatus());
							log.info("Outgoing channel:{} to receiver:{} updated from:{} to:{}",
									channel.getAddressSafe(), channel.getChannel().getReceiverAddress());
						}
					} catch (Exception e) {
						log.info("Channel {} update completed exceptionally", channel.getAddressSafe(), e);
					}
					if (!updated) {
						Thread.sleep(1000L);
					}
				} catch (InterruptedException e) {
					break;
				} catch (Throwable e) {
					log.error("Cycle failed", e);
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e1) {
						break;
					}
				}
			}
		}

		private void makeTransitions() throws Exception {
			boolean needClose = channel.isNeedClose();
			switch (channel.getStatus()) {
			case NEW:
				if (needClose) {
					channel.makeDisposable();
				} else {
					startCreating();
				}
				break;
			case CREATED:
				if (needClose) {
					channel.doRequestClose();
				} else {
					approvedDeposit = policy.getDeposit();
					channel.approveDeposit(tokenService, approvedDeposit);
				}
				break;
			case DEPOSIT_APPROVED:
				if (approvedDeposit == null) {
					approvedDeposit = tokenService.allowance(channel.getChannelAddress());
					if (approvedDeposit.compareTo(policy.getDeposit()) < 0) {
						log.info("Contract {} approved deposit is less that needed. Approved: %s, needed: %s",
								channel.getAddressSafe(), approvedDeposit);
						channel.resetDepositApproval();
						break;
					}
				}
				channel.deposit(approvedDeposit);
				break;
			case OPENED:
				if (needClose) {
					channel.doRequestClose();
				} else {
					PeerConnection connection = peerConnectionManager.getConnection(receiverAddress);
					ChannelOpenedResponse response = connection.getChannelPeer().opened(ChannelOpenedRequest
							.newBuilder().setChannelId(channel.getChannelAddress().toString()).build());
					if (response.hasError()) {
						log.warn("Failed to notify counterparty {}", response.getError().getMessage());
					} else {
						channel.setActive();
					}
				}
				break;
			case ACTIVE:
				if (!needClose && policy.getCloseBlocksCount() > 0) {
					long ageBlocks = ethereumService.getBlockNumber() - channel.getOpenBlock();
					if (ageBlocks > policy.getCloseBlocksCount()) {
						log.info("Channel {} age is {}, closing", channel.getAddressSafe(), ageBlocks);
						needClose = true;
					}
				}
				if (needClose) {
					channel.doRequestClose();
				}
				break;
			case CLOSE_REQUESTED:
				channel.closeIfPossible(ethereumService.getBlockNumber());
				break;
			case CLOSED:
				channel.settleIfPossible(ethereumService.getBlockNumber());
				break;
			case SETTLED:
				break;
			case DISPUTING:
				break;
			}
		}

		private void syncChannel() {
			PeerConnection connection = peerConnectionManager.getConnection(receiverAddress);
			SignedChannelState state = channel.createState();
			state.sign(credentials.getEcKeyPair());
			Error error = connection.getChannelPeer()
					.update(ChannelUpdateRequest.newBuilder().setState(state.toMessage()).build()).getError();
			if (error.getMessage().isEmpty()) {
				channel.syncCompleted(state);
				save();
			}
		}		
		
		private void startCreating() {
            Preconditions.checkState(channel.getChannel().getContract() == null);
            Future<TransactionReceipt> future = contractManager.channelManager().newChannel(
                channel.getChannel().getClientAddress(),
                receiverAddress,
                new Uint256(channelProperties.getCloseTimeout()),
                new Uint256(channelProperties.getSettleTimeout()),
                new Uint256(channelProperties.getAuditTimeout()),
                channelProperties.getAuditor().orElse(Address.DEFAULT)
            );
            //todo store transaction hash instead of future
            channel.startDeploying(CompletableFuture.supplyAsync(() -> {
                try {
                    TransactionReceipt receipt = future.get();
                    List<ChannelManagerContract.ChannelNewEventResponse> events = contractManager.channelManager().getChannelNewEvents(receipt);
                    if (events.isEmpty()) {
                        throw new IllegalStateException("Channel contract was not created");
                    }
//                    Address address = events.get(events.size() - 1).channel_address;
                    Address address = new Address(events.get(events.size() - 1).channel_address);
                    ChannelContract contract = contractManager.load(ChannelContract.class, address);
                    contract.setTransactionReceipt(receipt);
                    channel.getChannel().linkNewContract(contract);
                    setName(watcherName(channel));
                    coordinatorsMap.put(address, this);
                    return contract;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }));
        }

		private void save() {
			if (channel.getChannelAddress() != null) {
				 channelRepository.save(channel.getPersistentState());
			}
		}

	}
}
