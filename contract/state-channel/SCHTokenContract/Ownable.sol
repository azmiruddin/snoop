pragma solidity ^0.4.4;

/** @title Ownership Contract */
contract Ownable {

    // keeps the address of the account that created the contract
    address internal owner;

    /** @dev Constructor: instantiates onwership for derived contracts */
    function Ownable() public {owner = msg.sender;}

    // checks whether the sender is the owner of the contract. If not, the action is reversed and no gas is consumed.
    modifier onlyOwner() {
        require(owner == msg.sender);
        _;
    }

    /** @dev passes the ownership of the current contract to another address. Only the current owner can call this function.
        @param _newOwner The address of the new owner. 
        */
    function passOwnership(address _newOwner) internal onlyOwner { 
        require(_newOwner != 0x0);
        owner = _newOwner; 
    }

    /** @dev deactivates the current contract and returns all remaining ethers to the owner's address. Only the current owner can call this function. */
    function kill() internal onlyOwner {selfdestruct(owner);}

    /** @dev retrieves the address of the current owner.
        @return The address of the current owner. 
        */
    function getOwner() internal constant returns (address) {return owner;}    

}