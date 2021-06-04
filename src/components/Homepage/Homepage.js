import React from 'react'
import styled from 'styled-components'
import ListComponent from './ListComponent/ListComponent'
import Profile from './Profile'

const ContainerDash = styled.div`
    width: auto;
    margin-left: 16rem;
    position: relative;
    padding: 0 4rem;
`

const Homepage = () => {
    return (
        <ContainerDash>
            <Profile title="Profile Page"  />
            <ListComponent />
        </ContainerDash>
    )
}

export default Homepage
