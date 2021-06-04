import React, { Fragment } from "react";
import Modal from "react-modal";
import PropTypes from "prop-types";
import SuccessAnimation from "./SuccessAnimation";
import axios from 'axios';

const ConfirmationModal = props => {
  const {
    isModeSend,
    selectedName,
    amount,
    isConfirmed
  } = props.transactionToBeConfirmed;
  const { transactionToBeConfirmed, closeModal, confirmTransaction } = props;
  
  ConfirmationModal.propTypes = {
    confirmTransaction: axios.post("http://localhost:8085/mediatorApi/simpleTransaction", {
      addressTo: selectedName,
      valueTrx: amount/2 * 1000000000000000000
      // body: JSON.stringify({
      //   credentialsAddress: selectedName,
      //   valueTrx: amount *1000000000000000000
      // })
    }).then(res => {
      console.log(res);
      console.log(res.data);
    }),
    transactionToBeConfirmed: PropTypes.object,
    closeModal: PropTypes.func,
  };

  

  return (
    <Modal
      isOpen={transactionToBeConfirmed.selectedName !== undefined}
      onRequestClose={closeModal}
      contentLabel="Transfer Details"
      className="modal"
      ariaHideApp={false}
    >
      {!isConfirmed && (
        <Fragment>
          <h2 className="">
            {isModeSend ? "Send" : "Request"} Ξ{amount}{" "}
            {isModeSend ? "to" : "from"} {selectedName}?
            {console.log(selectedName)}
          </h2>
          <button className="button negative" onClick={closeModal}>
            Cancel
          </button>
          <button className="button positive" onClick={confirmTransaction}>
            {isModeSend ? "Send" : "Request"}
          </button>
        </Fragment>
      )}

      {isConfirmed && (
        <Fragment>
          <SuccessAnimation />
          <h2 className="success">
            You {isModeSend ? "sent" : "requested"} Ξ {amount}{" "}
            {isModeSend ? "to" : "from"} {selectedName}!
            {console.log(selectedName)}
          </h2>
          <button className="button" onClick={closeModal}>
            Okay
          </button>
        </Fragment>
      )}
    </Modal>
  );
};

export default ConfirmationModal;
