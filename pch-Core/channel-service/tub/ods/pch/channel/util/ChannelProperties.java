package tub.ods.pch.channel.util;

import java.util.Optional;

import org.web3j.abi.datatypes.Address;

import papyrus.channel.ChannelPropertiesMessage;
import OutgoingChannelPoolBean;

public class ChannelProperties extends DataObject {
    private long closeTimeout;
    private long settleTimeout;
    private long auditTimeout;
    private Address auditor;

    public ChannelProperties() {
    }

    public ChannelProperties(ChannelPropertiesMessage properties) {
        this.closeTimeout = properties.getCloseTimeout();
        this.settleTimeout = properties.getSettleTimeout();
        this.auditTimeout = properties.getAuditTimeout();
        auditor = properties.getAuditorAddress() != null && !properties.getAuditorAddress().isEmpty() ? new Address(properties.getAuditorAddress()) : null;
    }

    public ChannelProperties(OutgoingChannelPoolBean bean) {
        this.closeTimeout = bean.getCloseTimeout();
        this.settleTimeout = bean.getSettleTimeout();
        this.auditTimeout = bean.getAuditTimeout();
        this.auditor = bean.getAuditor();
    }

    public long getCloseTimeout() {
        return closeTimeout;
    }

    public void setCloseTimeout(long closeTimeout) {
        this.closeTimeout = closeTimeout;
    }

    public long getSettleTimeout() {
        return settleTimeout;
    }

    public void setSettleTimeout(long settleTimeout) {
        this.settleTimeout = settleTimeout;
    }

    public long getAuditTimeout() {
        return auditTimeout;
    }

    public void setAuditTimeout(long auditTimeout) {
        this.auditTimeout = auditTimeout;
    }

    public Optional<Address> getAuditor() {
        return Optional.ofNullable(auditor);
    }

    public boolean hasAuditor() {
        return auditor.getValue().signum() != 0;
    }

    public void setAuditor(Address auditor) {
        this.auditor = auditor;
    }

    public ChannelPropertiesMessage toMessage() {
        ChannelPropertiesMessage.Builder builder = ChannelPropertiesMessage.newBuilder();
        builder.setCloseTimeout(closeTimeout);
        builder.setSettleTimeout(settleTimeout);
        if (auditor != null) {
            builder.setAuditorAddress(auditor.toString());
        }
        return builder.build();
    }
}