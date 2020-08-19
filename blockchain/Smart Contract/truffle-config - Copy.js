module.exports = {
  networks: {
    development: {
      host: "ETHEREUM_FULL_CLIENT_ADDRESS",
      port: 8545,
      network_id: "4224", // Match any network id
      gas: ETHEREUM_NETWORK_GAS_LIMIT
    }
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
