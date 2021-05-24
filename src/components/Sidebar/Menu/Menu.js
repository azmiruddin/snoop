import React from 'react'
import MenuLink from './MenuLink'
import styled from 'styled-components'

const Container = styled.div`
    margin-top: 2rem;
    width: 100%;
`

const Menu = () => {
    return (
        <Container>
            <MenuLink title="Home" icon={'home'}/>
            <MenuLink title="Simple Transactions" icon={'file-multiple'}/>
            <MenuLink title="Payment Channels" icon={'gift'}/>
            <MenuLink title="State Channels" icon={'gift'}/>
            <MenuLink title="Channels configuration" icon={'cog'}/>
        </Container>
    )
}

export default Menu
