package tub.ods.pch.channel;

import org.springframework.stereotype.Repository;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import papyrus.channel.node.server.persistence.ClusteredCassandraRepository;

@Repository
public class TransferRepository extends ClusteredCassandraRepository<Address, Uint256, SignedTransfer> {
    public TransferRepository() {
        super(SignedTransfer.class, Uint256.class);
    }
}
