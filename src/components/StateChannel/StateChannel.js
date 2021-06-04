import React, {useState, useEffect} from 'react'
import styled from 'styled-components'
import NewDepositBtn from './NewDepositBtn'
import Deposits from './StateContent/Deposits'

import depositData from '../../DepositData.json'

import Modal from "react-modal";
// import TransferModal from './ModalStateChannel/TransferModal'

import "normalize.css/normalize.css";   
import "./ModalStateChannel/styles/styles.scss"
import Dashboard from './ContentBoard/dashboard/Default'

import {jssPreset, StylesProvider, ThemeProvider} from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import theme from '../themes';
import {useSelector} from 'react-redux';

import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import Table from './ContentBoard/dashboard/Table'
import TableChannel from '../ui-component/table'

const ContainerDash = styled.div`
    width: auto;
    margin-left: 16rem;
    position: relative;
    padding: 0 4rem;
`

const loadLocaleData = (locale) => {
    switch (locale) {
        default:
            return import('../utils/locals/en.json');
    }
};
    
const StateChannel = () => {
    const [isOpen, setIsOpen] = useState(false)

    const toggleModal = () => {
        setIsOpen(!isOpen);
    }

    const customization = useSelector((state) => state.customization);
    const [messages, setMessages] = useState();

    useEffect(() => {
        loadLocaleData(customization.locale).then((d) => {
            setMessages(d.default);
        });
    }, [customization]);

    if (customization.rtlLayout) {
        document.querySelector('body').setAttribute('dir', 'rtl');
    }
    return (
        <ContainerDash>
            
          
            {/* <Deposits title="State Channel" count={2} data={depositData.active} />
            <Deposits title="History" count={8} data={depositData.closed} /> */}
            <ThemeProvider theme={theme(customization)}>
                <CssBaseline>
                    <Dashboard />
                </CssBaseline>
            </ThemeProvider>
            
            

        </ContainerDash>
    )
}

export default StateChannel
