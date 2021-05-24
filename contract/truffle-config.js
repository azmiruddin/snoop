
require('dotenv').config();
//var HDWalletProvider = require("@truffle/hdwallet-provider");
//var HDWalletProvider = require("truffle-hdwallet-provider");
//var mnemonic = process.env["mnemonic"];
var mnemonic = "either priority fitness salon dolphin coral liquid nominee toy panther piece moon";
var infuraKey = "7081e5074e514ce8b5ea8ab9e8196ac4";

const HDWalletProvider = require('@truffle/hdwallet-provider');

module.exports = {

	networks: {
		development: {
			host: "127.0.0.1",
			port: 8545,
			network_id: "*",
			from: "0x7203561d49be898e16fED9A6FEc851647d6CFF20",
			gas: 2000000,
			gasPrice: 0x01
		},

		rinkeby: {
			host: "127.0.0.1",
			port: 8545,
			//from: "0x53fE24bb8f96e68636d9A1154dc49aDa5C333097",
			provider: () => new HDWalletProvider({
				mnemonic: {
					phrase: mnemonic
				},
				providerOrUrl: "https://rinkeby.infura.io/v3/7081e5074e514ce8b5ea8ab9e8196ac4",
				//numberOfAddresses: 1,
				}),
			network_id: "*",
			gas: 2000000
		},
	},

 compilers: {
      solc: {
  		version: "0.4.26",
  		//version: ">=0.4.26 < 0.7.2",
        settings: {
          optimizer: {
            enabled: true, // Default: false
            runs: 200      // Default: 200
          },
        }
      }
  }

};

//truffle migrate network development
//truffle deploy --network rinkeby --reset
//https://stackoverflow.com/questions/48694192/contract-has-not-been-deployed-to-detected-network-network-artifact-mismatch-o



/**
//gcloud compute ssh statechannel --zone=europe-west3-c
//sudo su root


sudo apt-get remove nodejs
sudo apt-get remove npm

**/
