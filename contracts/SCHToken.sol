pragma solidity >=0.4.22 <0.9.0;
//pragma solidity ^0.4.26;

import './SafeMath.sol';
import './ERC20Standard.sol';
import './Ownable.sol';

/// @title SCH token contract (SCH)
contract SCHToken is ERC20Standard, Ownable {
    using SafeMath for uint256;

    // EVENTS

    event TransferableChanged(bool transferable);

    // PUBLIC FUNCTIONS

    function SCHToken(address[] _wallets, uint256[] _amounts, uint256 totalSupply) public {
        require(_wallets.length == _amounts.length && _wallets.length > 0);
        uint i;
        uint256 sum = 0;
        for (i = 0; i < _wallets.length; ++i) {
            sum = sum.add(_amounts[i]);
        }
        require(sum == __SCH_LIMIT);
        totalSupply = __SCH_LIMIT;
        for (i = 0; i < _wallets.length; ++i) {
            balances[_wallets[i]] = _amounts[i];
            // balanceOf(_wallets[i]);
        }
    }

    // If ether is sent to this address, send it back
    // function() public { revert(); }
    function() public { revert(); }

    // Check transferable state before transfer
    // function transfer(address _to, uint _value) canTransfer public returns (bool) {
    //     return super.transfer(_to, _value);
    // }
    function transfer(address _to, uint _value) public returns(bool) {
	    require(balances[msg.sender] >= _value && _value > 0);
	    balances[msg.sender] = balances[msg.sender].sub(_value);
	    balances[_to] = balances[_to].add(_value);
	    emit Transfer(msg.sender, _to, _value);        
        }

    // Check transferable state before approve
    // function approve(address _spender, uint256 _value) canTransfer public returns (bool) {
    //     return super.approve(_spender, _value);
    // }
    function  approve(address _spender, uint _value) public {
		allowed[msg.sender][_spender] = _value;
		emit Approval(msg.sender, _spender, _value);
	}

    // Check transferable state before transfer
    // function transferFrom(address _from, address _to, uint _value) canTransfer public returns (bool) {
    //     return super.transferFrom(_from, _to, _value);
    // }
	function transferFrom(address _from, address _to, uint _value) public returns(bool) {
        require(balances[_from] >= _value && allowed[_from][msg.sender] >= _value && _value > 0);
            balances[_to] = balances[_to].add(_value);
            balances[_from] = balances[_from].sub(_value);
            allowed[_from][msg.sender] = allowed[_from][msg.sender].sub(_value);
            emit Transfer(_from, _to, _value);
        }

    /// @dev Called by the owner to change ability to transfer tokens by users.
    function setTransferable(bool _transferable) onlyOwner public {
        require(transferable != _transferable);
        transferable = _transferable;
        //NOTE EDited 25 to 24 warning
        emit TransferableChanged(transferable);
    }

    // MODIFIERS

    modifier canTransfer() {
        require(transferable || msg.sender == owner);
        _;
    }

    // FIELDS

    // Standard fields used to describe the token
    string public name = "SCH Token";
    string public symbol = "SCH";
    string public version = "H0.1";
    uint8 public decimals = 18;

    // At the start of the token existence it is transferable
    bool public transferable = true;

    // Amount of supplied tokens is constant and equals to 1 000 000 000 SCH
    uint256 private constant __SCH_LIMIT = 1000000;
}


