import React, { Component } from "react";
import "./usercard.css";
import Image from '../../assets/images/profilelg.png'
import axios from 'axios'

class UserCards extends Component {
  state = {
    data: [],
    coinbase:'',
    // coinbaseBalance: '',
    balance: 0,
  };

  uppercase = word => {
    return word.charAt(0).toUpperCase() + word.slice(1);
  };

  loadProfile = () => {
    axios
    .get("http://localhost:8085/mediatorApi/init")
    .then(response => {
      const wei = response.data.balanceEthCoinbaseAddress
      const ether0 = wei / 1000000000000000000
      const addr0 = response.data.ethCoinbaseAddress
      console.log(ether0, addr0)
      this.setState({
      //   pastTransactions,
        coinbase : addr0,
        balance : ether0,
      })
      console.log(this.state)
    })
    .catch(error => {
      console.log(error)
    })
  }

  loadData = () => {
    const { data } = this.state;
    const endpoint = `https://randomuser.me/api/?nat=us&results`;
    fetch(endpoint)
      .then(response => response.json())
      .then(json => {
        this.setState({
          data: [...data, ...json.results],
        });
      });
  };

  componentDidMount() {
    this.loadData();
    this.loadProfile()
  }

  render()
  {
    const{
      balance,
      coinbase
    } = this.state
    const bal = Math.floor(balance * 100) / 100;
    return (
      <div className="clearfix">
        <div className="row">
          {this.state.data.map(data => (
            <div className="col-md-4 animated fadeIn" key={data.id.value}>
              <div className="card">
                {/* <div className="card-body"> */}
                  <div className="avatar col col-1">
                    <img
                      src={Image}
                      className="card-img-top"
                      alt=""
                    />
                  </div>
                  <div className="col col-2">
                    <div id="unopacity">
                      <h5 className="card-title">
                        {this.uppercase(data.name.first) +
                          " " +
                          this.uppercase(data.name.last)}
                      </h5>
                      <p className="card-text">
                        {coinbase}
                        <br />
                        <span className="phone">{bal} Eth</span>
                      </p>
                    </div>
                  </div> 
                {/* </div> */}
              </div>
            </div>
          ))}
        </div>
      </div>
    );
  }
}

export default UserCards;