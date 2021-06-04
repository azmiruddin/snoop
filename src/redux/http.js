  
import axios from "axios";

export default axios.create({
  baseURL: "http://localhost:8085/mediatorApi",
  //baseURL: 'http://35.246.148.192:8085/mediatorApi',
  headers: {
    "Content-type": "application/json"
  }
});