pragma solidity ^0.4.26;

import './StandardToken.sol';
import ./'Ownable.sol';

/// @title SCH token contract (SCH)
contract SCHToken is StandardToken, Ownable {
    using SafeMath for uint256;

    // EVENTS

    event TransferableChanged(bool transferable);

    // PUBLIC FUNCTIONS

    function SCHToken(address[] _wallets, uint256[] _amounts) constant returns(uint256 _totalSupply) {
        require(_wallets.length == _amounts.length && _wallets.length > 0);
        uint i;
        uint256 sum = 0;
        for (i = 0; i < _wallets.length; ++i) {
            sum = sum.add(_amounts[i]);
        }
        require(sum == __SCH_LIMIT);
        _totalSupply = __SCH_LIMIT;
        for (i = 0; i < _wallets.length; ++i) {
            // balanceOf(_wallets[i] = _amounts[i]);
            balanceOf(_wallets[i]);
        }
    }

    // If ether is sent to this address, send it back
    function() { revert(); }

    // Check transferable state before transfer
    function transfer(address _to, uint _value) canTransfer returns (bool) {
        return super.transfer(_to, _value);
    }

    // Check transferable state before approve
    function approve(address _spender, uint256 _value) canTransfer returns (bool) {
        return super.approve(_spender, _value);
    }

    // Check transferable state before transfer
    function transferFrom(address _from, address _to, uint _value) canTransfer returns (bool) {
        return super.transferFrom(_from, _to, _value);
    }

    /// @dev Called by the owner to change ability to transfer tokens by users.
    function setTransferable(bool _transferable) onlyOwner {
        require(transferable != _transferable);
        transferable = _transferable;
        TransferableChanged(transferable);
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
    uint256 private constant __SCH_LIMIT = 1000000000;
}

library SafeMath {

  /**
  * @dev Multiplies two numbers, throws on overflow.
  */
  function mul(uint256 a, uint256 b) internal pure returns (uint256 c) {
    if (a == 0) {
      return 0;
    }
    c = a * b;
    assert(c / a == b);
    return c;
  }

  /**
  * @dev Integer division of two numbers, truncating the quotient.
  */
  function div(uint256 a, uint256 b) internal pure returns (uint256) {
    // assert(b > 0); // Solidity automatically throws when dividing by 0
    // uint256 c = a / b;
    // assert(a == b * c + a % b); // There is no case in which this doesn't hold
    return a / b;
  }

  /**
  * @dev Subtracts two numbers, throws on overflow (i.e. if subtrahend is greater than minuend).
  */
  function sub(uint256 a, uint256 b) internal pure returns (uint256) {
    assert(b <= a);
    return a - b;
  }

  /**
  * @dev Adds two numbers, throws on overflow.
  */
  function add(uint256 a, uint256 b) internal pure returns (uint256 c) {
    c = a + b;
    assert(c >= a);
    return c;
  }
}