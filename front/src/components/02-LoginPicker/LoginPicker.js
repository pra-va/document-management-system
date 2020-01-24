import React, { Component } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

class LoginPicker extends Component {
  constructor() {
    super();
    this.state = { message: "" };
  }

  getHomepageGreeting = () => {
    axios
      .get("http://localhost:8080/dvs/")
      .then(response => {
        if (this.state.message.length !== response.data.length) {
          this.setState({ message: response.data });
        }
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.getHomepageGreeting();
  }

  render() {
    return (
      <div className="App">
        <h1 className="m-3">{this.state.message}</h1>
        <Link to="/login" className="btn btn-info m-3">
          Login as Admin
        </Link>
      </div>
    );
  }
}

export default LoginPicker;
