import React, {useState, useEffect} from 'react'
import axios from 'axios'

const getNames = () => {
  let names =[];
  console.log(names)

  useEffect(() => {
    axios
    .get(`http://localhost:8085/mediatorApi/init`)
    .then(res => {
      const getNames = {
        name: res.data.listEthAccount
      }
      names.push(getNames)
    })
  }, [])
}

export default getNames()