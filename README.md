# Juli - Blop
Blockchain Micropayment is an open source project that aims to increase blockchain transaction throughput by using just a handful of main chain transactions to move an entire peer-to-peer network of activity off the main chain.
We develop micropayment by implementing the following core component:
1. Semantic data store: RDF4LED
2. Smart contract: HSG88, Perun-ETH and, reused State Channel
3. Blockchain library: Web3J

## Project Status

At this state, Blockchain Micropayment is under active development, and few feature ready for testing early in January 2021.
Basic features available, that we can try it and start to get involved, are covered the following feature
1. Off-chain Transaction
2. Channel Management
3. System Configuration

## Description

The Blockchain Micropayment is multi-user state channel node that can be used for opening, transacting on and closing state channels. It builds on top of HSG88 and Perun-ETH smart contracts.
The current feature plan to implements the following functionalities:

1. Off chain transaction and use bi-directional payment channels.
2. Channle Management: for the user to define a list of known channel in the off-chain network.
3. System management: for managing the sytem e.g., initial setup and cryptographic keys of the user.
4. User API interface: for client app (front end) to interact with the core payment channel.

The current version provides the following features:

|Feature | Implementation |
|:--|:--|
|Blockchain Network|Ethereum|
|Key management|Eth keystore |
|ID Provider|Local |
|User API|Two Party Transaction API |
|API Adapter|REST |
|Persistence|Redis and IPFS|
|Language|JDK 1.8|

This project currently contains two client test located in the rest client and front end directory.


![Blop Integration View](docs/blops-integration-v2.png?raw=true "Integration View")
