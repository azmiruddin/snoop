Running Process

POSTMAN
1. Run IPFS, Redis and Ganache Service
2. Run rest-service using eclipse
3. Open Postman
-- init data
GET
url endpoint : http://localhost:8085/mediatorApi/init
request

-- store data ipfs redis , simple transaction
POST
url endpoint : http://localhost:8085/mediatorApi/simpleTransaction
request

{
  "addressTo" : "xxxxyy",
  "valueTrx" : 2500000000000
}


CLIENT APP
**To Be Updated**