// const lists = fetch(`http://localhost:8085/mediatorApi/init`)
//     .then((resp) => resp.json())
//     .then(data => 
//       data.listEthAccount
//     )
//     .catch(error => (error))
//     console.log(lists)


// const data = () => {
//   lists.then(do => {

//   } 
    
//   )
// }
// data()


// export const data1 = fetch('http://localhost:8085/mediatorApi/init')
// .then(res => {
//   return res.json()
// }).then(address => address.listEthAccount)
// .then(name => {
//   const dataAddress = name
//   console.log(dataAddress[0])
//   return data1
// })

// console.log(data1)


// const data2 = fetch('http://localhost:8085/mediatorApi/init')
// .then(res => {
//   return res.json()
// }).then(balance => balance.balanceListEthAccount)
// .then(balanceArr => {
//   const dataAddress = balanceArr
//   console.log(`Azmi  ${dataAddress[0]/1000000000000000000}`)
//   return data1
// })


export const names = fetch('http://localhost:8085/mediatorApi/init')
.then(response => 
  response.json().then(data => ({
      address: data.listEthAccount,
      balances: data.balanceListEthAccount
  })
).then(res => {
  const address1 = `Azmi (${res.address[0]}) = ${res.balances[0]/1000000000000000000} Eth`
  const address2 = `Rudi (${res.address[1]}) = ${res.balances[1]/1000000000000000000} Eth`
  const address3 = `Jim (${res.address[2]}) = ${res.balances[2]/1000000000000000000} Eth`
  const address4 = `Alice (${res.address[3]}) = ${res.balances[3]/1000000000000000000} Eth`
  const address5 = `Bob (${res.address[4]}) = ${res.balances[4]/1000000000000000000} Eth`
  const address6 = `Zean (${res.address[5]}) = ${res.balances[5]/1000000000000000000} Eth`
  const address7 = `Don (${res.address[6]}) = ${res.balances[6]/1000000000000000000} Eth`
  const address8 = `Ron (${res.address[7]}) = ${res.balances[7]/1000000000000000000} Eth`

  console.log(address1)
  console.log(address2)
  console.log(address3)
  console.log(address4)
  console.log(address5)
  console.log(address6)
  console.log(address7)
  console.log(address8)
}));

console.log(names)


// export const names = [
//   // `Azmi (${res.address[0]}) = ${res.balances[0]/1000000000000000000} Eth`,
//   // `Rudi (${res.address[1]}) = ${res.balances[1]/1000000000000000000} Eth`,
//   // `Jim (${res.address[2]}) = ${res.balances[2]/1000000000000000000} Eth`,
//   // `Alice (${res.address[3]}) = ${res.balances[3]/1000000000000000000} Eth`,
//   // `Bob (${res.address[4]}) = ${res.balances[4]/1000000000000000000} Eth`,
 
// ]