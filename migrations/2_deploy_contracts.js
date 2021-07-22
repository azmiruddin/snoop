var ECRecovery = artifacts.require("./ECRecovery.sol");
var SCHToken = artifacts.require("./SCHToken.sol");
var ERC20Standard = artifacts.require("./ERC20Standard.sol");
var ChannelLibrary = artifacts.require("./ChannelLibrary.sol");
var EndpointRegistryContract = artifacts.require("./EndpointRegistryContract.sol");
var ChannelManagerContract = artifacts.require("./ChannelManagerContract.sol");
var ChannelApiStub = artifacts.require("./ChannelApiStub.sol");

module.exports = function(deployer) {
    deployer.deploy(ECRecovery);
    deployer.deploy(ChannelLibrary);
    deployer.deploy(EndpointRegistryContract);
    deployer.link(ECRecovery, ChannelLibrary);
    deployer.link(ChannelLibrary, ChannelManagerContract);

    deployer.deploy(ChannelApiStub).then(function() {
        deployer.deploy(SCHToken, ["0x02c675be7624FC682A95eB29346722E231B34863"],
        ["1000000"], 1000000).then(function() {               
            deployer.deploy(ChannelManagerContract, SCHToken.address, ChannelApiStub.address);
        })    
    });

    deployer.deploy(ERC20Standard);
    deployer.deploy(ChannelManagerContract, SCHToken.address, ChannelApiStub.address);
}; 