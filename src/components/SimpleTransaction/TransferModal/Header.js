import React, { Fragment } from "react";

function truncate(str, num) {
  if(str.length <= num){
    return str;  
  }
  return str.slice(0, num) + '...'
}

const Header = props => (
  <Fragment>
      <h2 className="description">Send ETH to another address.</h2>
      <div className="address">Hello, {truncate(props.coinbase, 20)}</div> 
      <h1 className="balance">
        Balance : <span className="balance__number">Ξ {props.balance} </span>
      </h1>
  </Fragment>
);

export default Header;

