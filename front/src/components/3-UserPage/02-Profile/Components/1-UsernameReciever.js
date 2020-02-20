import React, { useState } from "react";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";

const UsernameReciever = props => {
  var [username, setUsername] = useState("");

  axios
    .get(serverUrl + "loggedin")
    .then(response => {
      setUsername(response.data);
      props.setUpUsername(response.data);
    })
    .catch(error => {
      console.log(error);
    });

  return <h4 className="my-2">{username}</h4>;
};

export default UsernameReciever;
