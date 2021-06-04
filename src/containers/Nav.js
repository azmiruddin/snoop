import React from 'react'
import styled from 'styled-components'
// import ProfileImage from '../../assets/images/profilelg.png'

const Container = styled.div`
    display: flex;
    padding: 1rem;
    justify-content: flex-end;
    align-items: center;
    margin-bottom: 3rem;
    /* background-image: ${({ theme }) => theme.blueCool}; */
    background-color: #1565c0;
    border-radius: 1rem;
`

const ProfileImg = styled.img`
    height: 2rem;
    margin: 0 1rem;
    cursor: pointer;
`

const MessageIcon = styled.span`
    color: ${({ theme }) => theme.colorGray}; 
    font-size: 27px;
    cursor: pointer;
`

const Welcome = styled.h1`
    color:  ${({ theme }) => theme.switchColorPrimary};
    font-size: 27px;
    align-items: flex-end;
`

const Nav = () => {
    return (
        <Container>
            {/* <MessageIcon className="iconify" data-inline="false" data-icon="mdi-light:email"></MessageIcon> */}
            <Welcome>Hello, Welcome</Welcome>
            <ProfileImg src={require('../assets/images/profilelg.png')} />
        </Container>
    )
}

export default Nav