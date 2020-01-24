import React, { Component } from "react";
import axios from "axios";

axios.defaults.withCredentials = true;

class LoginForm extends Component {
  constructor(props) {
    super(props);
    this.state = { username: "", password: "" };
  }

  handleUsernameChange = event => {
    this.setState({ username: event.target.value });
  };

  handlePasswordChange = event => {
    this.setState({ password: event.target.value });
  };

  handleSubmit = event => {
    let userData = new URLSearchParams();
    userData.append("username", this.state.username);
    userData.append("password", this.state.password);
    axios
      .post("http://localhost:8080/dvs/api/login", userData, {
        headers: { "Content-type": "application/x-www-form-urlencoded" }
      })
      .then(response => {
        console.log(response.status);
        console.log("user " + response.data.username + " logged in.");
        console.log(response);
        if (response.status === 200) {
          this.props.history.push("/home");
        }
      })
      .catch(error => {
        console.log(error);
      });
    event.preventDefault();
  };

  render() {
    return (
      <div>
        <h1>Login to a System</h1>
        <form onSubmit={this.handleSubmit}>
          <div className="form-group">
            <input
              type="text"
              value={this.state.username}
              onChange={this.handleUsernameChange}
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              autoComplete="on"
              value={this.state.password}
              onChange={this.handlePasswordChange}
            />
          </div>
          <button className="btn btn-info" onClick={this.handleSubmit}>
            Login
          </button>
        </form>
      </div>
    );
  }
}

export default LoginForm;
