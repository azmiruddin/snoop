import React from 'react'
import './List.css'

export default function FriendList(friend, selectedFriend, onClickListFriend) {
  const {image, phone, email, address, name, id } = friend;
  const selected = selectedFriend === friend.id;
  return (
    <div>
       {
       selected ? 
          <li className="contact">
          <img className="contact-image" src={image} width="60px" height="60px" />
          <div className="contact-info">
              <div className="contact-name"> {name} </div>
              <div className="info"> {phone} </div>
              <div className="info  fadeIn animated"> {email} </div>
              <div className="info  fadeIn animated"> {address} </div>
          </div>
          </li> 
            :  
          <li className="contact">
              <img className="contact-image" src={image} width="60px" height="60px" />
              <div className="contact-info">
              <div className="contact-name"> {name} </div>
              <div className="info"> {phone} </div>
              </div>
          </li>
      }
    </div>
  )
}
