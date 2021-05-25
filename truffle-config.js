//var mnemonic = "either priority fitness salon dolphin coral liquid nominee toy panther piece moon";
const mnemonic = "priority nominee cloud they adapt fortune mean change boat march extra success"
var infuraKey = "7081e5074e514ce8b5ea8ab9e8196ac4";

const HDWalletProvider = require('@truffle/hdwallet-provider');

module.exports = {
	//contracts_directory: "./contracts/*SCHToken.sol",
  networks: {
      development: {
        host: "127.0.0.1",
        port: 8545,
        network_id: "*", // Match any network id
        from:"0x02c675be7624FC682A95eB29346722E231B34863" ,
        gas: 6721975,
        gasPrice: 20000000000
      },

      // ganachecli: {
      //   host: "127.0.0.1",
      //   port: 8545,
      //   network_id: "*", // Match any network id
      //   from:"0x4334361adcCbeFF1205d34AeA9F3aCd3EfD6f737",
      //   gas: 2000000,
      //   gasPrice: 0x01
      // },

      rinkeby: {
        host: "127.0.0.1",
        port: 8545,
        //from: "0x53fE24bb8f96e68636d9A1154dc49aDa5C333097",
        provider: () => new HDWalletProvider({
          mnemonic: {
            phrase: mnemonic
          },
          providerOrUrl: `https://rinkeby.infura.io/v3/${infuraKey}`,
          //numberOfAddresses: 1,
          }),
        network_id: "*",
        gas: 2000000
      },
    },
    
/*
  test: {
      host: "dev.ropsten.infura.io",
      port: 80,
      network_id: "*" // Match any network id
    },

  infura_ropsten: {
      provider: engine,
      network_id: "*", // Match any network id
      from: address
  },
*/
  compilers: {
      solc: {
  		//version: "^0.7.0",
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
