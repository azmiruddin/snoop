import React from 'react'
import styled from 'styled-components'
import Image from '../../assets/images/profilelg.png'
import axios from 'axios';

const ContainerProf = styled.div`
    margin-top: 5rem;
`

const ProfileImg = styled.img`
    height: 5rem;
`
const ProfileName = styled.h1`
    font-size: 1rem;
    font-weight: 300;
    color: ${({ theme }) => theme.textColor};
`

class Profile extends React.Component {
    state = {
        coinbase: '',
        // coinbaseBalance: '',
        balance: 0,
      };

    componentDidMount() {
        // this.setState(() => pastTransactions, coinInit);
        axios
          .get("http://35.246.148.192:8085/mediatorApi/init")
          // .get("http://localhost:8085/mediatorApi/init")
          .then(response => {
            const wei = response.data.balanceEthCoinbaseAddress
            const ether0 = wei / 1000000000000000000
            const addr0 = response.data.ethCoinbaseAddress
            console.log(ether0, addr0)
            this.setState({
            //   pastTransactions,
              coinbase : addr0,
              balance : ether0,
            })
            console.log(this.state)
          })
          .catch(error => {
            console.log(error)
          })
      }
      render(){
        const{
            balance,
            coinbase
        } = this.state
        const bal = Math.floor(balance * 100) / 100;
        const user = coinbase.slice(0, 8)
        return (
            <ContainerProf>
                <ProfileImg src={Image} />
                <ProfileName>{user}...</ProfileName>
                Balance : {bal} ETH 
            </ContainerProf>
        )
      }
}

// const Profile = () => {
    
//     return (
//         <Container>
//             <ProfileImg src={Image} />
//             <ProfileName>Scott Grant</ProfileName>
//         </Container>
//     )
// }

export default Profile
