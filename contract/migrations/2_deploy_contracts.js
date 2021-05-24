
var ECRecovery = artifacts.require("./ECRecovery.sol");
var SCHToken = artifacts.require("./SCHToken.sol");
var ERC20Standard = artifacts.require("./ERC20Standard.sol");
var PublicToken = artifacts.require("./PublicToken.sol");
var ERC20Lib = artifacts.require("./ERC20Lib.sol");
var ChannelLibrary = artifacts.require("./ChannelLibrary.sol");
var EndpointRegistryContract = artifacts.require("./EndpointRegistryContract.sol");
var ChannelManagerContract = artifacts.require("./ChannelManagerContract.sol");
var ChannelApiStub = artifacts.require("./ChannelApiStub.sol");

var fs = require('fs');
var addressFile = './deployedAddress.json';
var file = require(addressFile);

module.exports = function(deployer) {
	//const totalSupply = 500000000 * Math.pow(10, 18)

    deployer.deploy(ECRecovery);
    deployer.deploy(ChannelLibrary);

    deployer.deploy(EndpointRegistryContract);
    deployer.link(ECRecovery, ChannelLibrary);
    deployer.link(ChannelLibrary, ChannelManagerContract);


    deployer.deploy(ChannelApiStub).then(function() {
        deployer.deploy(SCHToken, ["0x1f11D1753eB0C0EFf5eD1807C4A568e244302aa5"],
        ["1000000000000"], 1000000000000).then(function() {
            deployer.deploy(ChannelManagerContract, SCHToken.address, ChannelApiStub.address);
            //console.log('SCHToken Address ' + SCHToken.address);
            //console.log('ChannelApiStub Address ' + ChannelApiStub.address);
        });
    });

    deployer.deploy(ERC20Standard);
    deployer.deploy(ChannelManagerContract, SCHToken.address, ChannelApiStub.address);




};
