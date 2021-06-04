import React, {useState} from 'react'
import styled from 'styled-components'
import NewDepositBtn from './NewDepositBtn'
import Deposits from './SimpleContent/Deposits'

import depositData from '../../DepositData.json'

import Modal from "react-modal";
import TransferModal from './TransferModal/TransferModal'

import "normalize.css/normalize.css";
import "./TransferModal/styles/styles.scss"

const ContainerDash = styled.div`
    width: auto;
    margin-left: 16rem;
    position: relative;
    padding: 0 4rem;
`

const SimpleTransaction = () => {
    const [isOpen, setIsOpen] = useState(false)

    const toggleModal = () => {
        setIsOpen(!isOpen);
    }

   
    return (
        <ContainerDash> 
             <Modal 
                isOpen={isOpen}
                onRequestClose={toggleModal}
                contentLabel="Dialog"
                style={{
                    content:{
                        width:'30%',
                        margin: 'auto',
                    }
                }}
            >
                <TransferModal />
            </Modal>
            <button onClick={toggleModal}>
                <NewDepositBtn/>
            </button>
            <Deposits title="Transactions" count={2} data={depositData.active} />
            <Deposits title="History" count={8} data={depositData.closed} />
        </ContainerDash>
    )
}

export default SimpleTransaction
