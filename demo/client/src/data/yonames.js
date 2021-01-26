export const names = [

]

 const lists = fetch(`http://localhost:8085/mediatorApi/init`)
    .then((resp) => resp.json())
    .then(data => 
      data.listEthAccount
    )
    .catch(error => (error))
    console.log(lists)
    names.push(lists)

console.log(names)