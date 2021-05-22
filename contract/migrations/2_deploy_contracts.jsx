// var BigNumber = require('bignumber');
// var ECRecovery = artifacts.require("zeppelin-solidity/contracts/ECRecovery.sol");
// var PapyrusToken = artifacts.require("./PapyrusToken.sol");

// Optional Deps
// var StandardToken = artifacts.require("./StandardToken.sol");
// var ChannelApi = artifacts.require("./ChannelApi.sol");
// var ChannelContract = artifacts.require("./ChannelContract.sol");

var ECRecovery = artifacts.require("./ECRecovery.sol");
var SCHToken = artifacts.require("./SCHToken.sol");
var ChannelLibrary = artifacts.require("./ChannelLibrary.sol");
var EndpointRegistryContract = artifacts.require("./EndpointRegistryContract.sol");
var ChannelManagerContract = artifacts.require("./ChannelManagerContract.sol");
var ChannelApiStub = artifacts.require("./ChannelApiStub.sol");
var ERC20Standard = artifacts.require("./ERC20Standard.sol");

module.exports = function(deployer) {
    deployer.deploy(ECRecovery);
    deployer.link(ECRecovery, ChannelLibrary);
    deployer.deploy(ChannelLibrary);
    deployer.deploy(EndpointRegistryContract);
    // deployer.deploy(SCHToken, ["0x965A8d944F9A37ff87936c0A8BBC1b94a4815deF"], ["1000000000000"], 1000000000000);
    deployer.link(ChannelLibrary, ChannelManagerContract);
    deployer.deploy(ChannelApiStub)
    .then(function() {
        // deployer.deploy(SCHToken, ["0x8dfe397a7F465525Ffc33B838c378f63718D9941", "0x1d4e4A85109b522589D098Cd5311305043dFc118"], ["1000000000000", "1000000000000"], ["1000000000000", "1000000000000"]).then(function() {
        deployer.deploy(SCHToken, ["0x515061D7Fa9c544Fb91B8D28BBF62d1aa4e1F8ad"], ["1000000000000"], 1000000000000).then(function() {
            // deployer.deploy(ChannelManagerContract, SCHToken.address);
            deployer.deploy(ChannelManagerContract, SCHToken.address, ChannelApiStub.address);
            
        });
    });
    deployer.deploy(ERC20Standard);
};
