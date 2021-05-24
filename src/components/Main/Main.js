import React, {useState} from 'react'
import styled from 'styled-components'
import Nav from './Nav'
import NewDepositBtn from './NewDepositBtn'
import Deposits from './SimpleTransactions/Deposits'

import depositData from '../../DepositData.json'

import Modal from "react-modal";
import TransferModal from './TransferModal/TransferModal'

const Container = styled.div`
    width: auto;
    margin-left: 16rem;
    position: relative;
    padding: 0 4rem;
`


const Main = () => {
    const [isOpen, setIsOpen] = useState(false)

    const toggleModal = () => {
        setIsOpen(!isOpen);
    }
    return (
        <Container>
            <Nav />
            <button onClick={toggleModal}>
                <NewDepositBtn  />
            </button>
            <Modal 
                isOpen={isOpen}
                onRequestClose={toggleModal}
                contentLabel="Dialog"
            >
                <TransferModal />
            </Modal>
            <Deposits title="Transactions" count={2} data={depositData.active} />
            <Deposits title="History" count={8} data={depositData.closed} />
        </Container>
    )
}

export default Main
