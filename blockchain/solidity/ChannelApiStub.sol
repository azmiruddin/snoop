pragma solidity >=0.4.21 < 0.7.2;

import './ChannelApi.sol';

contract ChannelApiStub is ChannelApi {

    event ChannelAudit(address from, address to, uint impressionsCount, uint fraudCount);
    
    function applyRuntimeUpdate(address from, address to, uint impressionsCount, uint fraudCount) public override  {
        emit ChannelAudit(from, to, impressionsCount, fraudCount);
    }
    function applyAuditorsCheckUpdate(address from, address to, uint fraudCountDelta) public override {}
}