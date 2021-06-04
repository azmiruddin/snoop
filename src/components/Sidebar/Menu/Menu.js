import React from 'react'
import MenuLink from './MenuLink'
import styled from 'styled-components'
import {Link} from 'react-router-dom'

const ContainerMenu = styled.div`
    margin-top: 2rem;
    width: 100%;
`

const Menu = () => {
    return (
        <ContainerMenu>
            <Link to="/">
                <MenuLink title="State Channels" icon={'gift'}/>
            </Link>
            <Link to="/onchain">
                <MenuLink title="On-Chain Transactions" icon={'file-multiple'}/>
            </Link>
            <Link to="/systemconfig">
                <MenuLink title="System Configurations" icon={'file-multiple'}/>
            </Link>
            
            {/* <MenuLink title="Channels configuration" icon={'cog'}/> */}
        </ContainerMenu>
    )
}

export default Menu
