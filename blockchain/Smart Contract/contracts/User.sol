pragma solidity >=0.4.22 <0.6.0;

contract User {

    struct Triple {
        string ipfsHash;
    }

  mapping (string => Triple) private entries;

  /**
   * @dev associate a multihash entry with the sender address
   * @param _digest hash digest produced by hashing content using hash function
   */

  function setEntry(string memory _digest) public {

    Triple memory data = Triple({
        ipfsHash: _digest
    });

    entries[_digest] = data;
  }
}
