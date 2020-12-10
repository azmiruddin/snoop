pragma solidity >=0.4.21 < 0.7.2;

import "./ChannelLibrary.sol";

contract ChannelContract {
    using ChannelLibrary for ChannelLibrary.Data;
    ChannelLibrary.Data data;

    event ChannelNewBalance(address token_address, address participant, uint balance, uint block_number);
    event ChannelCloseRequested(address closing_address, uint block_number);
    event ChannelClosed(address closing_address, uint block_number);
    event TransferUpdated(address node_address, uint block_number);
    event ChannelSettled(uint block_number);
    event ChannelAudited(uint block_number);
    event ChannelSecretRevealed(bytes32 secret, address receiver_address);

    modifier onlyManager() {
        require(msg.sender == address(data.manager));
        _;
    }

    function ChannelContract(
        address manager_address,
        address sender,
        address client,
        address receiver,
        uint close_timeout,
        uint settle_timeout,
        uint audit_timeout,
        address auditor
    ) public
    {
        //allow creation only from manager contract
        require(msg.sender == manager_address);
        require (sender != receiver);
        require (client != receiver);
        require (audit_timeout >= 0);
        require (settle_timeout > 0);
        require (close_timeout >= 0);

        data.sender = sender;
        data.client = client;
        data.receiver = receiver;
        data.auditor = auditor;
        data.manager = ChannelManagerContract(manager_address);
        data.close_timeout = close_timeout;
        data.settle_timeout = settle_timeout;
        data.audit_timeout = audit_timeout;
        data.opened = block.number;
    }

    /// @notice Caller makes a deposit into their channel balance.
    /// @param amount The amount caller wants to deposit.
    /// @return True if deposit is successful.
    function deposit(uint256 amount) public returns (bool) {
        bool success;
        uint256 balance;

        (success, balance) = data.deposit(amount);

        if (success == true) {
            emit ChannelNewBalance(data.manager.token(), msg.sender, balance, 0);
        }

        return success;
    }

    /// @notice Get the address and balance of both partners in a channel.
    /// @return The address and balance pairs.
    function addressAndBalance()
        public
        view returns (
        address sender,
        address receiver,
        uint balance)
    {
        sender = data.sender;
        receiver = data.receiver;
        balance = data.balance;
    }

    /// @notice Request to close the channel. 
    function request_close () public 
    {
        data.request_close();
        emit ChannelCloseRequested(msg.sender, data.closed);
    }

    /// @notice Close the channel. 
    function close (
        uint nonce,
        uint256 completed_transfers,
        bytes signature
    ) public {
        data.close(address(this), nonce, completed_transfers, signature);
        emit ChannelClosed(msg.sender, data.closed);
    }

    /// @notice Settle the transfers and balances of the channel and pay out to
    ///         each participant. Can only be called after the channel is closed
    ///         and only after the number of blocks in the settlement timeout
    ///         have passed.
    function settle() public {
        data.settle();
        emit ChannelSettled(data.settled);
    }

    /// @notice Settle the transfers and balances of the channel and pay out to
    ///         each participant. Can only be called after the channel is closed
    ///         and only after the number of blocks in the settlement timeout
    ///         have passed.
    function audit(address auditor) public onlyManager {
        data.audit(auditor);
        emit ChannelAudited(data.audited);
    }
 
    function destroy() public onlyManager {
        require(data.settled > 0);
        require(data.audited > 0 || block.number > data.closed + data.audit_timeout);
        selfdestruct(0);
    }

    function sender() public view returns (address) {
        return data.sender;
    }

    function receiver() public view returns (address) {
        return data.receiver;
    }

    function client() public view returns (address) {
        return data.client;
    }

    function auditor() public view returns (address) {
        return data.auditor;
    }

    function closeTimeout() public view returns (uint) {
        return data.close_timeout;
    }

    function settleTimeout() public view returns (uint) {
        return data.settle_timeout;
    }

    function auditTimeout() public view returns (uint) {
        return data.audit_timeout;
    }

    /// @return Returns the address of the manager.
    function manager() public view returns (address) {
        return data.manager;
    }

    function balance() public view returns (uint) {
        return data.balance;
    }

    function nonce() public view returns (uint) {
        return data.nonce;
    }

    function completedTransfers() public view returns (uint) {
        return data.completed_transfers;
    }

    /// @notice Returns the block number for when the channel was opened.
    /// @return The block number for when the channel was opened.
    function opened() public view returns (uint) {
        return data.opened;
    }

    function closeRequested() public view returns (uint) {
        return data.close_requested;
    }

    function closed() public view returns (uint) {
        return data.closed;
    }

    function settled() public view returns (uint) {
        return data.settled;
    }

    function audited() public view returns (uint) {
        return data.audited;
    }

    // function () public { revert(); }
}