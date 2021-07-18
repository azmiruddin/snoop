root@statechannel:/applications/contract# truffle migrate --reset


Compiling your contracts...
===========================
> Everything is up to date, there is nothing to compile.



Starting migrations...
======================
> Network name:    'development'
> Network id:      1622725872509
> Block gas limit: 6721975 (0x6691b7)


1_initial_migration.js
======================

   Replacing 'Migrations'
   ----------------------
   > transaction hash:    0x318f4f2abd8bc8f30b82041f1718e1e8ab786779354addb73b4062f25704c0ee
   > Blocks: 0            Seconds: 0
   > contract address:    0x850d512e0850BcDBde88aFf552Cc197753EaBadF
   > block number:        10
   > block timestamp:     1622726072
   > account:             0x02c675be7624FC682A95eB29346722E231B34863
   > balance:             99.94558146
   > gas used:            175813 (0x2aec5)
   > gas price:           20 gwei
   > value sent:          0 ETH
   > total cost:          0.00351626 ETH


   > Saving migration to chain.
   > Saving artifacts
   -------------------------------------
   > Total cost:          0.00351626 ETH


2_deploy_contracts.js
=====================

   Replacing 'ECRecovery'
   ----------------------
   > transaction hash:    0xd1bd34d8e8f7071ea833b9818ce007709257c7157d65d196120e8de4af57fc08
   > Blocks: 0            Seconds: 0
   > contract address:    0x38420ef50C44578028176195A24Ffc2aBCAb920a
   > block number:        12
   > block timestamp:     1622726072
   > account:             0x02c675be7624FC682A95eB29346722E231B34863
   > balance:             99.94333892
   > gas used:            69912 (0x11118)
   > gas price:           20 gwei
   > value sent:          0 ETH
   > total cost:          0.00139824 ETH


   Replacing 'ChannelLibrary'
   --------------------------
   > transaction hash:    0xcc8257ef6fc3b543669ad7674ff82549dd3b090aff704329aa27ec65e7533f65
   > Blocks: 0            Seconds: 0
   > contract address:    0xbE360B4c1D3C001176a361a5f5c1294Faa039d39
   > block number:        13
   > block timestamp:     1622726073
   > account:             0x02c675be7624FC682A95eB29346722E231B34863
   > balance:             99.92954596
   > gas used:            689648 (0xa85f0)
   > gas price:           20 gwei
   > value sent:          0 ETH
   > total cost:          0.01379296 ETH


   Replacing 'EndpointRegistryContract'
   ------------------------------------
   > transaction hash:    0xbdbee31ef818cbd42ad7ef10c56e8b28173feefedb07cec7ec9da050fc9305a4
   > Blocks: 0            Seconds: 0
   > contract address:    0xe1d1953487e11cac56b3c59AF1B704d74388C4Cf
   > block number:        14
   > block timestamp:     1622726073
   > account:             0x02c675be7624FC682A95eB29346722E231B34863
   > balance:             99.92271296
   > gas used:            341650 (0x53692)
   > gas price:           20 gwei
   > value sent:          0 ETH
   > total cost:          0.006833 ETH


   Linking
   -------
   * Contract: ChannelManagerContract <--> Library: ChannelLibrary (at address: 0xbE360B4c1D3C001176a361a5f5c1294Faa039d39)

   Replacing 'ChannelApiStub'
   --------------------------
   > transaction hash:    0x87bf5b05d65ae7c84b6cfdadc5f1fa50c51e9a5fc695e461d93afca114750001
   > Blocks: 0            Seconds: 0
   > contract address:    0xb9C315457C92Ab8799C7B044a93EFF4500Afef43
   > block number:        15
   > block timestamp:     1622726073
   > account:             0x02c675be7624FC682A95eB29346722E231B34863
   > balance:             99.92016606
   > gas used:            127345 (0x1f171)
   > gas price:           20 gwei
   > value sent:          0 ETH
   > total cost:          0.0025469 ETH


   Replacing 'SCHToken'
   --------------------

   Replacing 'ERC20Standard'
   -------------------------
   > transaction hash:    0x59848f63ca5dfa76231d745d4f7ac8d7a402ea79f5bf6ac5aa955243060c64be
   ⠋ Blocks: 0            Seconds: 0   > transaction hash:    0xfd97196f95ac02c161e73f849b0e2c54c156943d2622be4757a5c10de44338f6
   > Blocks: 0            Seconds: 0
   > contract address:    0x2e8746c2ACBC3C20430e8ef1bAA6DbecB8Dc9BD5
   > block number:        16
   > block timestamp:     1622726073
   > account:             0x02c675be7624FC682A95eB29346722E231B34863
   > balance:             99.89873974
   > gas used:            630105 (0x99d59)
   > gas price:           20 gwei
   > value sent:          0 ETH
   > total cost:          0.0126021 ETH

   > Blocks: 0            Seconds: 0
   > contract address:    0xb83b7697576030597A3Ab39B5183B4d4B4aC644D
   > block number:        17
   > block timestamp:     1622726073
   > account:             0x02c675be7624FC682A95eB29346722E231B34863
   > balance:             99.89873974
   > gas used:            441211 (0x6bb7b)
   > gas price:           20 gwei
   > value sent:          0 ETH
   > total cost:          0.00882422 ETH

   ⠹ Blocks: 0            Seconds: 0
   Deploying 'ChannelManagerContract'
   ----------------------------------
   ⠸ Blocks: 0            Seconds: 0   > transaction hash:    0xe020bf446e64c43991716d350dd96f977ddd2d1850cbc61d2d34073d8a781d25
   > Blocks: 0            Seconds: 0
   > contract address:    0x9E470D1B435Bef4FB0bF1BfaEe5Aff65e59c9405
   > block number:        18
   > block timestamp:     1622726073
   > account:             0x02c675be7624FC682A95eB29346722E231B34863
   > balance:             99.87623766
   > gas used:            1125104 (0x112af0)
   > gas price:           20 gwei
   > value sent:          0 ETH
   > total cost:          0.02250208 ETH


   > Saving migration to chain.
   > Saving artifacts
   -------------------------------------
   > Total cost:           0.0684995 ETH


Summary
=======
> Total deployments:   8
> Final cost:          0.07201576 ETH



Token
=======
curl -X POST --data '{"jsonrpc":"2.0", "method": "eth_getStorageAt", "params": ["0x9E470D1B435Bef4FB0bF1BfaEe5Aff65e59c9405", "0x0", "latest"], "id": 1}' localhost:8545

{"id":1,"jsonrpc":"2.0","result":"0x0cca1cd45460850f8b369728ff7bbef0f9c40d76"}



