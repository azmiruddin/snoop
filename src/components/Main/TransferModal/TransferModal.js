import React, { Fragment } from "react";
import { FaAngleDoubleUp } from "react-icons/fa";
// import { pastTransactions } from "../data/pastTransactions";
// import {pastTransactions} from "../data/datas"
import TransferForm from "./TransferForm";
import ConfirmationModal from "./ConfirmationModal";
import ResponseModal from "./ResponseModal";
import TransactionList from "./TransactionList";
import Header from "./Header";
import PositionedSnackbar from "./PositionedSnackbar";
import axios from 'axios';

class TransferModal extends React.Component {
  state = {
    coinbase: '',
    // coinbaseBalance: '',
    listAddress: [],
    listAddBalances: [],
    balance: 0,
    transactions: [],
    isResponseModalOpen: false,
    isModeSend: true,
    transactionToBeConfirmed: {
      isModeSend: undefined,
      selectedName: undefined,
      amount: undefined,
      note: undefined,
      isConfirmed: false
    }
  };


  firstPage = React.createRef();
  secondPage = React.createRef();

  componentDidMount() {
    // this.setState(() => pastTransactions, coinInit);
    axios
      .get("http://localhost:8085/mediatorApi/init")
      .then(response => {
        const wei = response.data.balanceEthCoinbaseAddress
        const ether0 = wei / 1000000000000000000
        const addr0 = response.data.ethCoinbaseAddress
        console.log(ether0, addr0)
        this.setState({
          // pastTransactions,
          coinbase : addr0,
          balance : ether0,
        })
        console.log(this.state)
      })
      .catch(error => {
        console.log(error)
      })
  }

  confirmTransaction = () => {
    console.log("transaction confirmed");
    this.setState(
      prevState => ({
        transactionToBeConfirmed: {
          ...prevState.transactionToBeConfirmed,
          isConfirmed: true
        }
      }),
      this.processTransaction(this.state.transactionToBeConfirmed)
    );
  };

  closeModal = () => {
    this.setState(prevState => ({
      ...prevState,
      transactionToBeConfirmed: {
        isModeSend: undefined,
        selectedName: undefined,
        amount: undefined,
        note: undefined,
        isConfirmed: false
      }
    }));
  };

  handleSubmitForm = (isModeSend, selectedName, amount, note) => {
    this.setState(() => ({
      transactionToBeConfirmed: {
        isModeSend,
        selectedName,
        amount: parseFloat(amount),
        note,
        isConfirmed: false
      }
    }));
  };

  processTransaction = transaction => {
    const { isModeSend, selectedName, amount, note } = transaction;
    const { balance } = this.state;
    const newBalance = isModeSend ? balance - amount : balance;
    const date = new Date().toLocaleDateString("en-US", {
      month: "short",
      day: "numeric"
    });

    this.setState(
      prevState => ({
        balance: newBalance,
        transactions: [
          {
            isModeSend,
            executed: false,
            selectedName,
            amount,
            note,
            newBalance,
            date
          },
          ...prevState.transactions
        ]
      }),
      this.executeTransaction
    );
  };

  executeTransaction = () => {
    const transactions = [...this.state.transactions];
    const newBalance = transactions[0].newBalance + transactions[0].amount;
    transactions[0] = { ...transactions[0], executed: true, newBalance };
    if (!transactions[0].isModeSend) {
      setTimeout(() => {
        this.setState(() => ({
          transactions,
          balance: newBalance,
          isResponseModalOpen: true
        }));
      }, 7000);
    }
  };

  closeResponseModal = () => {
    this.setState(() => ({ isResponseModalOpen: false }));
  };

  switchToSendMode = () => {
    this.setState(() => ({ isModeSend: true }));
    this.scroll(this.secondPage);
  };

  switchToRequestMode = () => {
    this.setState(() => ({ isModeSend: false }));
    this.scroll(this.secondPage);
  };

  scrollToFirstPage = () => {
    this.scroll(this.firstPage);
  };

  scroll = ref => {
    ref.current.scrollIntoView({ block: "start", behavior: "smooth" });
  };

  render() {
    const {
      transactionToBeConfirmed,
      transactions,
      balance,
      coinbase,
      isResponseModalOpen,
      isModeSend
    } = this.state;
    return (
      <Fragment>
        <div className="TransferPage__FirstScreen" ref={this.firstPage}>
          <Header balance={balance} coinbase={coinbase}/>
          <div className="TransferPage__FirstScreen__ButtonsBlock">
            <div className="TransferPage__FirstScreen__ButtonsBlock__Buttons">
              <button className="button" onClick={this.switchToSendMode}>
                Send coin
              </button>
              <button className="button" onClick={this.switchToRequestMode} disabled>
                Request coin
              </button>
            </div>
          </div>
        </div>
        <div className="TransferPage__SecondScreen" ref={this.secondPage}>
          <div className="TransferPage__SecondScreen__Header">
            <button
              className="inverted-button"
              onClick={this.scrollToFirstPage}
            >
              <FaAngleDoubleUp />
            </button>
            <h1 className="balance">
              Balance: <span className="balance__number">Ξ {balance} </span>
            </h1>
          </div>

          <ConfirmationModal
            transactionToBeConfirmed={transactionToBeConfirmed}
            closeModal={this.closeModal}
            confirmTransaction={this.confirmTransaction}
          />

          {/* ResponseModal used for fake request fullfillments */}
          <ResponseModal
            transaction={transactions[0]}
            isResponseModalOpen={isResponseModalOpen}
            closeResponseModal={this.closeResponseModal}
            transactionToBeConfirmed={transactionToBeConfirmed}
          />
          <TransferForm
            isModeSend={isModeSend}
            balance={balance}
            handleSubmitForm={this.handleSubmitForm}
          />
          <TransactionList transactions={transactions} />
        </div>
        <PositionedSnackbar />
      </Fragment>
    );
  }
}

export default TransferModal;
