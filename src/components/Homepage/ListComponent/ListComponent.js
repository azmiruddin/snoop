import React, {useState, useEffect} from 'react'
import ChannelList from './ChannelList'
import FriendList from './FriendList'
import styled from 'styled-components'
import {satu, dua, ban} from "../../../assets/channel"

const ContainerList = styled.div`
    width: auto;
    position: relative;
    padding: 0 2rem;
    column-count: 2;
    column-gap: 5%;
`
export const Top = styled.div`
  border-radius: 8px;
  height: 10vh;
  margin-bottom: 4px;
  text-transform: uppercase;
  width: 100%;
  font-size: 1.3rem;
  font-weight: 650;
  text-align: center;
  background-color: ${({ theme }) => theme.blueCool};
  color: #f1f2f6;
  border-radius: 5rem;
  padding: 1rem;
  display: flex;
  justify-content: center;
  cursor: pointer;
`

const Border = styled.div`
  border: solid 1px;
  border-radius: 20px;
  color: grey;
`

function truncatedong(str, num) {
  if(str.length <= num){
    return str;  
  }
  return str.slice(0, num) + '...'
}

export default function ListComponent() {
  const [friendList, setFriendList] = useState([])
  const [friendListBalance, setFriendListBalance] = useState([])
  const url = "http://localhost:8085/mediatorApi/init"

  useEffect(() => {
    loadFriend()
    loadBalanceFriend()
  }, [])

  const loadFriend = async () => {
    await fetch(url)
    .then(response => response.json())
    .then(datalist => setFriendList(datalist.listEthAccount))
    //window.localStorage.setItem("address", JSON.stringify(receivedData.listEthAccount))
  } 
  
  const loadBalanceFriend = async () => {
    await fetch(url)
    .then(response => response.json())
    .then(datalist => setFriendListBalance(datalist.balanceListEthAccount))
  }

  const addressFriend = friendList.map(data => truncatedong(data, 16))
  const balanceFriend = friendListBalance.map(data => data / 1000000000000000000) 
  console.log(addressFriend)
  console.log(balanceFriend)


  const DataFriend = [
    {
      id:1,
      name: addressFriend[0],
      image: 'https://i.pinimg.com/736x/34/42/d7/3442d7bda02f7ca7bf0566304c0c939a.jpg',
      phone: balanceFriend[0]+ "Eth",
      email: 'geraltfromrivia@morhen.kaed',
      adress: "Kaer Morhen, Kaedwen"
    },
    {
      id:2,
      name: addressFriend[1],
      image: 'http://i.playground.ru/i/98/19/20/00/wiki/content/y1rqpmxj.250xauto.png',
      phone: balanceFriend[1]+ "Eth",
      email: 'thegreatestpoet@chameleon.red',
      adress: "Cabaret 'Chameleon', Novigrad, Redania"
    },
    {
      id:3,
      name: addressFriend[2],
      image: 'https://vignette2.wikia.nocookie.net/vedmak/images/c/cd/%D0%99%D0%B5%D0%BD%D0%BD%D0%B8%D1%84%D1%8D%D1%80%D0%923.png/revision/latest/scale-to-width-down/350?cb=20160414164624',
      phone: balanceFriend[2]+ "Eth",
      email: 'yen.ven@aretuza.taned',
      adress: "Vengerberg, Aedirn"
    },
    {
      id:4,
      name: addressFriend[3],
      image: 'https://vignette.wikia.nocookie.net/vedmak/images/b/bc/%D0%A2%D1%80%D0%B8%D1%81%D1%81%D0%923.png/revision/latest/scale-to-width-down/350?cb=20160422141718',
      phone: balanceFriend[3]+ "Eth",
      email: 'triss.merigold@aretuza.taned',
      adress: "Maribor, Temeria"
    }
  ]

  const DataChannel = [
    {
      id:1,
      name: 'We Channel',
      // image: 'https://i.pinimg.com/736x/34/42/d7/3442d7bda02f7ca7bf0566304c0c939a.jpg',
      image: satu,
      contract: '+41242341287',
      root: 'geraltfromrivia@morhen.kaed',
      auditor: "Kaer Morhen, Kaedwen"
    },
    {
      id:2,
      name: 'North Channel',
      //image: 'http://i.playground.ru/i/98/19/20/00/wiki/content/y1rqpmxj.250xauto.png',
      image: dua,
      contract: '+46785412354',
      root: 'thegreatestpoet@chameleon.red',
      auditor: "Cabaret 'Chameleon', Novigrad, Redania"
    },
    {
      id:3,
      name: '',
      image: ban,
      contract: '',
      root: '',
      auditor: ""
    },
    {
      id:4,
      name: '',
      image: ban,
      contract: '',
      root: '',
      auditor: ""
    }
    // {
    //   id:3,
    //   name: 'Yennefer',
    //   image: 'https://vignette2.wikia.nocookie.net/vedmak/images/c/cd/%D0%99%D0%B5%D0%BD%D0%BD%D0%B8%D1%84%D1%8D%D1%80%D0%923.png/revision/latest/scale-to-width-down/350?cb=20160414164624',
    //   phone: '+28675674329',
    //   email: 'yen.ven@aretuza.taned',
    //   adress: "Vengerberg, Aedirn"
    // },
    // {
    //   id:4,
    //   name: 'Triss',
    //   image: 'https://vignette.wikia.nocookie.net/vedmak/images/b/bc/%D0%A2%D1%80%D0%B8%D1%81%D1%81%D0%923.png/revision/latest/scale-to-width-down/350?cb=20160422141718',
    //   phone: '+16578564738',
    //   email: 'triss.merigold@aretuza.taned',
    //   adress: "Maribor, Temeria"
    // }
  ]

  const [selectedChannel, setSelectedChannel] = useState();
  const [selectedFriend, setSelectedFriend] = useState();


  const onClickListChannel = (id) => () => {
    setSelectedChannel(id);
  };

  const onClickListFriend = (id) => () => {
    setSelectedFriend(id);
  };

  return ( 
  <ContainerList>
    <Top>Channel List</Top>
    <Border>
      {DataChannel.map((channel) => (
        <ChannelList key={channel.id}
          id={channel.id}
          name={channel.name}
          image={channel.image}
          contract={channel.contract}
          root={channel.root}
          auditor={channel.auditor}
          onClickListChannel={onClickListChannel}
          selectedChannel={selectedChannel}
        />
      ))}
    </Border>
      
    <Top>Friend List</Top>
    <Border>
      {DataFriend.map((friend) => (
        <FriendList key={friend.id}
          id={friend.id}
          name={friend.name}
          image={friend.image}
          phone={friend.phone}
          email={friend.email}
          address={friend.address}
          onClickListFriend={onClickListFriend}
          selectedFriend={selectedFriend}
        />
      ))}
    </Border>
      
      
    
  </ContainerList>
  )
}