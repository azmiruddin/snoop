import React from 'react'
import styled from 'styled-components'
import UserCards from './UserCard'

const Container = styled.div`

`

const Title = styled.h1`
    font-weight: 500;
    color:  ${({ theme }) => theme.textColor};
    font-size: 1.3rem;
    display: flex;
    align-items: center;
`

const Profile = () => {

    return (
        <Container>
           <UserCards/>
        </Container>
    )
}

export default Profile
