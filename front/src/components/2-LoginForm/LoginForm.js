import React, { Component } from "react";
import axios from "axios";
import "./Form.css";
import serverUrl from "./../7-properties/1-URL";
import FormPresentation from "./Components/LoginPresentation/LoginFormPresentation";

axios.defaults.withCredentials = true;

class LoginForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      loginFailed: false,
      initialCreate: false
    };
  }

  componentDidMount() {
    axios.get(serverUrl + "user/first").then(response => {
      if (response.status === 200) {
        this.setState({ initialCreate: true });
      } else {
        this.setState({ initialCreate: false });
      }
    });
  }

  setUsername = value => {
    this.setState({ username: value });
  };

  setPassword = value => {
    this.setState({ password: value });
  };

  setLoginFailed = value => {
    this.setState({ loginFailed: value });
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
        if (response.data.su === "true" || response.data.su === "false") {
          this.props.history.push({
            pathname: "/dvs/home",
            state: { isUserAdmin: isUserAdmin }
          });
        } else {
          this.setState({ loginFailed: true });
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
        <FormPresentation
          username={this.state.username}
          password={this.state.password}
          loginFailed={this.state.loginFailed}
          setUsername={this.setUsername}
          setPassword={this.setPassword}
          setLoginFailed={this.setLoginFailed}
          handleSubmit={this.handleSubmit}
          initialCreate={this.state.initialCreate}
        />
      </div>
    );
  }
}

export default LoginForm;
