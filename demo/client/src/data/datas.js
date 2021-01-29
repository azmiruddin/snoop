const get_url = "http://localhost:8085/mediatorApi/init"
fetch(get_url)
.then(data => data.json())
.then(rawAddress => {
  window.localStorage.setItem("address", JSON.stringify(rawAddress.listEthAccount))
  window.localStorage.setItem("addrBalances", JSON.stringify(rawAddress.balanceListEthAccount))
  window.localStorage.setItem("coinbaseAddr", JSON.stringify(rawAddress.ethCoinbaseAddress))
  window.localStorage.setItem("coinbase", JSON.stringify(rawAddress.balanceEthCoinbaseAddress))
})

const dataNames = localStorage.getItem("address")
const parseName = JSON.parse(dataNames)

function truncate(str, num) {
  if(str.length <= num){
    return str;  
  }
  return str.slice(0, num) + '...'
}

const address = [
  truncate(parseName[0], 16),
  truncate(parseName[1], 16), 
  truncate(parseName[2], 16),
  truncate(parseName[3], 16),
  truncate(parseName[4], 16),
  truncate(parseName[5], 16),
  truncate(parseName[6], 16),
  truncate(parseName[7], 16),
  truncate(parseName[8], 16)
  // truncate(parseName[9], 16),
]

//console.log(address)


//first feature
// const getCoinbaseAddr = localStorage.getItem("coinbaseAddr")
// const getCoinbaseBalance = localStorage.getItem("coinbase")

// coinbaseInit.coinbase = localStorage.getItem("coinbase")
const dataBalances = localStorage.getItem("addrBalances")
const parseBalance = JSON.parse(dataBalances)
//adding name
const alice = `Alice (${parseBalance[1]}) (${parseName[1]})`
const bob = `Bob (${parseBalance[2]}) (${parseName[2]})`
const clark = `Clark (${parseBalance[3]}) (${parseName[3]})`
const withName = [alice, bob, clark]
//console.log(withName)

export const names = parseName
console.log(names)
// const balance = JSON.parse(getCoinbaseBalance)
// const coinbase = JSON.parse(getCoinbaseAddr)
// export const coinInit = {
//   balance: balance,
//   coinbase: coinbase
// }
// console.log(coinInit, names)

export const pastTransactions = {
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
}
