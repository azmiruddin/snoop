export const namesd = []

const get_url = "http://localhost:8085/mediatorApi/init"

//defining async function
async function getdata(url){
  //storing response
  const res = await fetch(url)

  //storing data in form of JSON
  let rawData = await res.json()
  let address = await rawData.listEthAccount
  // let address1 = rawData.listEthAccount[0]
  // console.log(data.listEthAccount)
  // addData(address1)
  // addData(address)
  var keys = Object.keys(address)
  keys.forEach(function(key){
    namesd.push(address[key])
  })
  //addData(rawData)
  // const names = new Array(address)
  // console.log(address)
}

getdata(get_url)

// function addData(address){
//   var keys = Object.keys(address)
//   keys.forEach(function(key){
//     names.push(address[key])
//   })
//   return names
// }


export const names = [
  "ya",
  "0x33719b186eb764e6f47345be01e284ad807399a5"
]
names.push('Ney', "0x965a8d944f9a37ff87936c0a8bbc1b94a4815def",
"0x782214246a194e995410f1d3a80076ad67956258",
 "0x0ec43e9e0174d3cb2371c17216d5f519c14a9c67",
 "0x1d4e4a85109b522589d098cd5311305043dfc118",
 "0x8dfe397a7f465525ffc33b838c378f63718d9941")

 console.log(names)

