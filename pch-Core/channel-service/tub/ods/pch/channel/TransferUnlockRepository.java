package tub.ods.pch.channel;

import org.springframework.stereotype.Repository;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import papyrus.channel.node.server.persistence.ClusteredCassandraRepository;
import tub.ods.common.data.model.SignedTransferUnlock;

@Repository
public class TransferUnlockRepository extends ClusteredCassandraRepository<Address, Uint256, SignedTransferUnlock> {
    public TransferUnlockRepository() {
        super(SignedTransferUnlock.class, Uint256.class);
    }
}
