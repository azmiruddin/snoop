package tub.ods.pch.channel.state;

import org.springframework.stereotype.Repository;
import org.web3j.abi.datatypes.Address;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

import papyrus.channel.node.server.persistence.ClusteredCassandraRepository;
import tub.ods.common.data.model.OutgoingChannelPoolBean;

@Repository
public class OutgoingChannelPoolRepository extends ClusteredCassandraRepository<Address, Address, OutgoingChannelPoolBean> {

    private Queries queries;

    public OutgoingChannelPoolRepository() {
        super(OutgoingChannelPoolBean.class, Address.class);
    }

    @Override
    protected void init() {
        super.init();
        queries = mappingManager.createAccessor(Queries.class);
    }

    public Iterable<OutgoingChannelPoolBean> all() {
        return queries.getAll();
    }

    public void markShutdown(Address sender, Address receiver) {
        queries.updateShutdown(true, sender, receiver);
    }
    
    @Accessor
    interface Queries {
        @Query("select * from outgoing.channel_pool")
        Result<OutgoingChannelPoolBean> getAll();

        @Query("update outgoing.channel_pool set shutdown=? where sender=? and receiver=?")
        void updateShutdown(boolean shutdown, Address sender, Address receiver);
    }
}
