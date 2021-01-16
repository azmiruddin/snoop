# Juli - Blop
Blockchain Micropayment is an open source project that aims to increase blockchain transaction throughput by using just a handful of main chain transactions to move an entire peer-to-peer network of activity off the main chain.

## Project Status

At this state, Blockchain Micropayment is under development, and ready for testing early in February 2021.
Basic features available, that we can try it and start to get involved, are covered the following feature
1. Off chain transaction
2. Channel Management
3. System Configuration

## Description

The Blockchain Micropayment is multi-user state channel node that can be used for opening,
transacting on and closing state channels. It builds on top of Perun-ETH-Contracts and HSG88.
The current feature plan to implements the following functionalities:

1. Off chain transaction and use bi-directional payment channels.
2. Channle Management: For the user to define a list of known channel in the off-chain network.
3. System management: for managing the sytem e.g., initial setup and cryptographic keys of the user.
4. User API interface: for client app (front end) to interact with the core payment channel.

The current version provides the following features:

|Feature | Implementation |
|:--|:--|
|Blockchain Backend|Ethereum|
|Key management|Eth keystore |
|ID Provider|Local |
|User API|Two Party Payment API |
|User API Adapter|REST |
|Persistence|Redis and IPFS|

This project currently contains two client test located in the rest client and front end directory.


![Blop Integration View](docs/blops-integration-v2.png?raw=true "Integration View")
