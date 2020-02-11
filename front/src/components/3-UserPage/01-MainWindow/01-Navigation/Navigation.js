import React, { Component } from "react";
import axios from "axios";
import "./Navigation.css";
import NavigationPresentation from "./NavigationPresentation";
import { withRouter } from "react-router";
import serverUrl from "./../../../7-properties/1-URL";

class Navigation extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isUserAdmin: "false",
      authenticated: "false"
    };
  }

  handleLogout = event => {
    axios
      .post(serverUrl + "logout")
      .then(response => {})
      .catch(error => {
        console.log(error);
      });
  };

  isUserAdminChecker = () => {
    axios
      .get(serverUrl + "administrator")
      .then(response => {
        this.setState({ isUserAdmin: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  authenticationChecker = () => {
    axios
      .get(serverUrl + "authenticated")
      .then(response => {
        if (response.data === false) {
          this.props.history.push("/");
        } else {
          this.setState({ authenticated: "true" });
        }
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.authenticationChecker();
    this.isUserAdminChecker();
  }

  render() {
    return (
      <NavigationPresentation
        role={this.state.isUserAdmin}
        handleLogout={this.handleLogout}
      />
    );
  }
}

export default withRouter(Navigation);
