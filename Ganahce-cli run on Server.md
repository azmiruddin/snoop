# RUNNING GANACHE-CLI ON SERVER
1. RUN GANAHCE-CLI SERVICE
docker run --detach --publish 8545:8545 trufflesuite/ganache-cli:latest --host 0.0.0.0 --debug --mnemonic "priority nominee cloud they adapt fortune mean change boat march extra success" --verbose --chainId --hardfork "muirGlacier" --noVMErrorsOnRPCResponse -g 20000000000 -l 6721975

2. DEPLOY CONTRACT
truffle migrate --network development
truffle migrate --network development --reset

3. SEE THE CHANNELMANAGERCONTRACT TOKEN and CHANNEL API ADDR
- token
curl -X POST --data '{"jsonrpc":"2.0", "method": "eth_getStorageAt", "params": ["CHANNELMANAGERCONTRACTADDRESS", "0x0", "latest"], "id": 1}' localhost:8545

- channelApi
curl -X POST --data '{"jsonrpc":"2.0", "method": "eth_getStorageAt", "params": ["CHANNELMANAGERCONTRACTADDRESS", "0x1", "latest"], "id": 1}' localhost:8545

4. CHECK THE ACCOUNT BALANCE
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_getBalance","params":["0x02c675be7624FC682A95eB29346722E231B34863", "latest"],"id":1}' localhost:8545

