module.exports = {
  networks: {
    development: {
      host: "127.0.0.1",
      port: 7545,
      network_id: "5777", // Match any network id
      from: "38F55db0fC619eD761c96F379E1c6d64aB5A8ca0",
      //from: "0x3984Bc76Cb775D7866d1cd55C4F49E3d13D411D4",
      gas: 6721975
    }
  },

/*
  rinkeby: {
  	host: "127.0.0.1", // Connect to geth on the specified
  	port: 8545,
  	from: "ac80c575a6df01ae8b869f42a594b681018cb68b", // default address to use for any transaction Truffle makes during migrations
  	network_id: "*",
  	gas: 4612388 // Gas limit used for deploys
  },
*/

  mocha: {
        enableTimeouts: false,
        before_timeout: 120000 // Here is 2min but can be whatever timeout is suitable for you.
  },

  compilers: {
    solc: {
      settings: {
        optimizer: {
          enabled: true, // Default: false
          runs: 200      // Default: 200
        },
      }
    }
  }
};
