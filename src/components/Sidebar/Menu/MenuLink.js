import React from 'react'
import styled from 'styled-components'

const ContainerMLink = styled.div`
    border-left: 3px solid ${props => props.active ? props.theme.activeMenu : "transparent"};
    width: 100%;
    padding: 0.1rem;
    padding-right: 2rem;
    cursor: pointer;
    display: flex;  
    flex-direction: column;
    align-items: center;
    margin-bottom: 1rem;
    transition: 0.2s all ease-in-out;

    &:hover {
        background-color: rgba(0,0,0,0.1);
    }
`


const SpanMLink = styled.span`
    color: ${props => props.active ? props.theme.activeMenu : "#AAA5A5"};
    /* color: ${props => !props.active && props.theme.textColor}; */
    font-size: 1rem;
    /* margin-right: 1rem; */
    margin: 0 16px 0 16px;
    align-items: center;
`

const TitleMLink = styled.h1`
    font-size: 0.9rem;
    font-weight: 300;
    color: ${props => props.active ? props.theme.activeMenu : "#AAA5A5"};
`

const MenuLink = ({ title, active, icon }) => {

    return (
        <ContainerMLink active={active}>
            <SpanMLink active={active} className="iconify" data-inline="false" data-icon={`mdi-light:${icon}`}></SpanMLink>
            <TitleMLink active={active}>{title}</TitleMLink>
        </ContainerMLink>
    )
}

export default MenuLink
