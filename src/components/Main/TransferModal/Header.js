import React, { Fragment } from "react";
//tab
import DisabledTabs from "./Tabs";

const Header = props => (
  <Fragment>
    
      {/* <div className="mainTitle">
        <DisabledTabs />  
        <br/>
        Simple Transaction
      </div> */}
      <h2 className="description">Send coin to another address.</h2>
      <div className="address">Hello, {props.coinbase}</div> 
      <h1 className="balance">
        Balance : <span className="balance__number">Ξ {props.balance} </span>
      </h1>
  </Fragment>
);

export default Header;
