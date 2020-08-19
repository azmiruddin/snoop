
## Ethereum Private Network

Here is the guide to setup Ethereum Private Network Cluster. In a cluster we require 3 types of nodes. In this guideline we are going to setup a 3 node cluster with each of the following type of nodes mentioned below. In Experimentation environment we suggest to have atleast 5 nodes cluster with Miners nodes (N+1)/2 being the majority in the network.

1. __Bootnode__: It is used to enable discovery of nodes within the network.
2. __Miners__: Nodes are responsible for Sealing new blocks of transactions
3. __Full-Clients__: These are used to sync data across network and keep up-to-date record ledger. And our system is also going to communicate via these client to be able to submit transactions.


### Requirements
* Python
* Go Language
* [Node & NPM](https://nodejs.org/en/download/)
* [Truffle](https://truffleframework.com/docs/truffle/getting-started/installation) (to deploy Smart Contract)
* [Geth](https://geth.ethereum.org/)

### Steps to Setup

We are setting up Ethereum using [Geth& Tools 1.8.27](https://gethstore.blob.core.windows.net/builds/geth-alltools-linux-arm7-1.8.27-4bcc0a37.tar.gz) Library. As we are deploying Ethereum on Raspberry Pi devices which are ARM boards so we need Geth ARMv7 compatible Library. Geth can be downloaded from [here](https://geth.ethereum.org/downloads/).

After downloading Geth & Tools, we have to extract the .tar file to /home/pi location under geth folder.

Using the geth library we then setup 3 types of nodes in our private network.

First, Bootnode is to be setup because through these bootnodes a node can join the network and find other nodes.

#### Setup Bootnode
Creating a Bootnode Key and Starting the bootnode service. Further information can be found [here](https://github.com/ethereum/go-ethereum/wiki/Setting-up-private-network-or-local-cluster).

```
> /home/pi/geth/bootnode -genkey bootnode.key --writeaddress LOCALIP:30301
I0216 09:53:08.076155 p2p/discover/udp.go:227] Listening, enode://890b6b5367ef6072455fedbd7a24ebac239d442b18c5ab9d26f58a349dad35ee5783a0dd543e4f454fed22db9772efe28a3ed6f21e75674ef6203e47803da682@LOCALIP:30301
```

Using enode://890b6b5367ef6072455fedbd7a24ebac239d442b18c5ab9d26f58a349dad35ee5783a0dd543e4f454fed22db9772efe28a3ed6f21e75674ef6203e47803da682@LOCALIP:30301 this we can provide other nodes (miner and full nodes) to initialize and become a poart of the network

#### Create Genesis File

The Genesis block is the start block of the Blockchain — the first block, block 0, and the only block that does not point to a predecessor block. the genesis block is hard coded into clients, but in Ethereum it can be whatever you like.

The Genesis file is a JSON file that defines the characteristics of that initial block and subsequently the rest of the blockchain.

A sample genesis is provided in the repository.

#### Initializing the genesis block on every node of ethereum (except bootnode)


```
mkdir /home/pi/ethnet

/home/pi/geth/geth --datadir "/home/pi/ethnet" init genesis.json
```

#### Creating Wallet
```
/home/pi/geth/geth --datadir "/home/pi/ethnet" account new --password "PASSWORD"
```
It will return with Account address.

#### Starting Miner Nodes
```
/home/pi/geth/geth --datadir "/home/pi/ethnet" --mine --networkid 4224 --bootnodes enode://890b6b5367ef6072455fedbd7a24ebac239d442b18c5ab9d26f58a349dad35ee5783a0dd543e4f454fed22db9772efe28a3ed6f21e75674ef6203e47803da682@BOOTNODE_IP:30301
```

The above command will start ethereum miners nodes

#### Starting Full Node Client
```
/home/pi/geth/geth --datadir "/home/pi/ethnet" --networkid 4224 --rpc --rpcport "8545" --port "30303" --rpccorsdomain "*" --nat "any" --rpcapi eth,web3,clique,personal,txpool,admin,debug,net,clique,miner --bootnodes "enode://890b6b5367ef6072455fedbd7a24ebac239d442b18c5ab9d26f58a349dad35ee5783a0dd543e4f454fed22db9772efe28a3ed6f21e75674ef6203e47803da682@BOOTNODE_IP:30301" -unlock 0 --password "PASSWORD" --syncmode "full" --rpcaddr 0.0.0.0 --ipcdisable
```

The above code will start ethereum full node client with remote procedure calls enabled

___

## Deploying Smart Contract

We developed our Smart Contract using Solidity Programming Language and Truffle Framework was used to test and deploy the smart contract.

```
cd /home/pi/Semantics2019/blockchain/Smart contract
```

This folder contains *truffle-config.js* where we define our ethereum network client address and also need to define Gas Limit

After setting the clients address and Gas limit, we have to compile our smart contract in ABI and the deploy as a transaction on ethereum network

***NOTE***: Please provide client address which has pre-loaded Ethers/Tokens

```
$ truffle compile
$ truffle migrate --reset
$ truffle deploy
```

After successfully deploying Smart Contract please note down the Smart Contract Address which further needs to be provided to our Java client.
