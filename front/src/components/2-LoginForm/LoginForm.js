import React, { Component } from "react";
import axios from "axios";
import logo from "./../../resources/logo.png";
import "./Form.css";
import serverUrl from "./../7-properties/1-URL";

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

  handleIncorectPasswordStateReset = () => {
    this.setState({ loginFailed: false });
  };

  handleSubmit = event => {
    let userData = new URLSearchParams();
    let isUserAdmin = false;
    userData.append("username", this.state.username);
    userData.append("password", this.state.password);
    axios
      .post(serverUrl + "login", userData, {
        headers: { "Content-type": "application/x-www-form-urlencoded" }
      })
      .then(response => {
        isUserAdmin = response.data.su;
        if (response.status === 200) {
          this.props.history.push({
            pathname: "/home",
            state: { isUserAdmin: isUserAdmin }
          });
        }
      })
      .catch(error => {
        console.log(error);
        this.setState({ loginFailed: true });
      });
    event.preventDefault();
  };

  render() {
    return (
      <div className="container">
        <div className="row d-flex justify-content-center">
          <form
            onSubmit={this.handleSubmit}
            id="loginForm"
            className="align-items-lg m-4"
          >
            <div className="login-form p-4 border border-dark rounded-lg">
              <img className="mb-3 width" src={logo} alt="unable to load" />
              <div className="form-group">
                <label
                  className="d-flex justify-content-start col-form-label-lg mb-0 pb-0"
                  htmlFor="username"
                >
                  Username
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="username"
                  aria-describedby="username"
                  onChange={this.handleUsernameChange}
                  autoComplete="on"
                />
              </div>
              <div className="form-group">
                <label
                  className="d-flex justify-content-start col-form-label-lg mb-0 pb-0 pt-0"
                  htmlFor="password"
                >
                  Password
                </label>
                <input
                  type="password"
                  className="form-control"
                  id="password"
                  onChange={this.handlePasswordChange}
                  autoComplete="on"
                />
              </div>
              <button
                type="submit"
                className="btn btn-black btn-lg btn-block mt-4"
              >
                Log In
              </button>
            </div>

            <div
              className={
                this.state.loginFailed
                  ? "alert alert-danger alert-dismissible fade show my-3"
                  : "alert alert-danger alert-dismissible fade show my-3 invisible"
              }
              role="alert"
            >
              <h5>Incorrect Username or Password!</h5>
              <button
                id="loginFormButton"
                type="button"
                className="close"
                aria-label="Close"
                onClick={this.handleIncorectPasswordStateReset}
              >
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </form>
        </div>
      </div>
    );
  }
}

export default LoginForm;
