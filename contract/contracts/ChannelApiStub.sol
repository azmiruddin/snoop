pragma solidity >=0.4.22 <0.9.0;

import './ChannelApi.sol';

contract ChannelApiStub is ChannelApi {

    event ChannelAudit(address from, address to, uint impressionsCount, uint fraudCount);
    
    function applyRuntimeUpdate(address from, address to, uint impressionsCount, uint fraudCount) {
        ChannelAudit(from, to, impressionsCount, fraudCount);
    }
    function applyAuditorsCheckUpdate(address from, address to, uint fraudCountDelta) {}
}
