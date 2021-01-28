export const pastTransactions = {
  coinbase : "Rudiii",
  balance : 99,
  transactions: [
    {
      isModeSend: true,
      executed: true,
      selectedName: "Mark",
      amount: 10,
      newBalance: 292,
      date: "Feb 7",
      note: "lunch"
    },
    {
      isModeSend: false,
      executed: true,
      selectedName: "Lucy",
      amount: 12,
      newBalance: 302,
      date: "Feb 2",
      note: ""
    },
    {
      isModeSend: true,
      executed: true,
      selectedName: "Luke",
      amount: 25,
      newBalance: 302,
      date: "Jan 20",
      note: "cab share"
    },
    {
      isModeSend: false,
      executed: true,
      selectedName: "Josh",
      amount: 10,
      newBalance: 327,
      date: "Jan 18",
      note: "cinema"
    }
  ]
};

// console.log(pastTransactions)

// const get_url = "http://localhost:8085/mediatorApi/init"

// async function getdata(url){
  //storing response
  //const res = await fetch(url)

  //storing data in form of JSON
  //const rawData = await res.json()
  //let coinbaseData = rawData.ethCoinbaseAddress
  //let balanceData = rawData.balanceEthCoinbaseAddress / 1000000000000000000
  // let address1 = rawData.listEthAccount[0]
  // console.log(data.listEthAccount)
  // addDataCoinbase(coinbaseData)
  // addBalanceCoinbase(balanceData)
  //addData(rawData)
  // const names = new Array(address)
  //console.log(`${coinbaseData}, +  ${typeof balanceData}`)
  //pastTransactions.coinbase = coinbaseData
  //pastTransactions.balance = balanceData
//}

//getdata(get_url)

// function addDataCoinbase(coinbaseData){
  
//   console.log(pastTransactions.coinbase)
// }

// function addBalanceCoinbase(balanceData){
  
//   console.log(pastTransactions.balance)
// }


