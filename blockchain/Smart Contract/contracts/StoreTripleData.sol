pragma solidity >=0.4.22 <0.6.0;

contract StoreTripleData {

    struct Triple {
        bytes32 triple;
        string ipfsHash;
    }

    mapping (bytes32 => Triple) private entries;



  /**
   * @dev associate a multihash entry with the sender address
   * @param _digest hash digest produced by hashing content using hash function
   */

  function insertIPFS(bytes32 _triple, string memory _digest) public {

    Triple memory data = Triple({
        triple: _triple,
        ipfsHash: _digest
    });

    entries[_triple] = data;
  }

  /**
   * @dev retrieve multihash entry associated with an ipfs hash digest
   * @param _triple address used as key
   */

    function buyData(bytes32 _triple, address payable receiver) payable public{
        require(msg.value > 0);
        receiver.transfer(msg.value);
    }

    function fetchIPFS(bytes32 _triple) external view returns (string memory ipfsHash) {
        Triple storage ipfs = entries[_triple];
        return ipfs.ipfsHash;
    }


}
