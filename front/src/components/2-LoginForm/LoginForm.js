import React, { Component } from "react";
import axios from "axios";
import logo from "./../../resources/logo.png";
import "./Form.css";

axios.defaults.withCredentials = true;

class LoginForm extends Component {
  constructor(props) {
    super(props);
    this.state = { username: "", password: "", loginFailed: false };
  }

  handleUsernameChange = event => {
    this.setState({ username: event.target.value });
  };

  handlePasswordChange = event => {
    this.setState({ password: event.target.value });
  };

  loginFailed = () => {
    this.setState({ loginFailed: true });
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
        if (response.status === 200) {
          this.props.history.push("/home");
        }
      })
      .catch(error => {
        console.log(error);
        if (error.response.status === 401) {
          console.log("failing");
          this.setState({ loginFailed: true });
        }
      });
    event.preventDefault();
  };

  render() {
    return (
      <div className="container">
        <div
          className="row d-flex
            justify-content-center
           align-items-lg heigth-100"
        >
          <form onSubmit={this.handleSubmit}>
            <img className="my-3" src={logo} alt="unable to load" />
            <div className="form-group">
              <label
                className="d-flex justify-content-start col-form-label-lg mb-0"
                htmlFor="username"
              >
                Username
              </label>
              <input
                type="text"
                className="form-control form-control-lg"
                id="username"
                aria-describedby="username"
                onChange={this.handleUsernameChange}
              />
            </div>
            <div className="form-group">
              <label
                className="d-flex justify-content-start col-form-label-lg mb-0"
                htmlFor="password"
              >
                Password
              </label>
              <input
                type="password"
                className="form-control form-control-lg"
                id="password"
                onChange={this.handlePasswordChange}
              />
            </div>
            <button type="submit" className="btn btn-black btn-lg btn-block">
              Submit
            </button>
            {this.state.loginFailed ? (
              <div className="alert alert-danger my-3" role="alert">
                <h5>Incorrect Username or Password!</h5>
              </div>
            ) : (
              <div>
                <h2>&nbsp;</h2>
                <h2>&nbsp;</h2>
              </div>
            )}
          </form>
        </div>
      </div>
    );
  }
}

export default LoginForm;
