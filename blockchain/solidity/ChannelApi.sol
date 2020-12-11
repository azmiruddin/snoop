pragma solidity >=0.4.21 < 0.7.2;

contract ChannelApi {
    function applyRuntimeUpdate(address from, address to, uint impressionsCount, uint fraudCount) public;

    function applyAuditorsCheckUpdate(address from, address to, uint fraudCountDelta) public;
}