import React, { Component } from "react";

import axios from "axios";
import "./Navigation.css";
import NavigationPresentation from "./NavigationPresentation";

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
      .get("http://localhost:8080/dvs/api/logout")
      .then(response => {
        console.log("Logged out.");
      })
      .catch(error => {
        console.log(error);
      });
  };

  isUserAdminChecker = () => {
    axios
      .get("http://localhost:8080/dvs/api/administrator")
      .then(response => {
        this.setState({ isUserAdmin: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  authenticationChecker = () => {
    axios
      .get("http://localhost:8080/dvs/api/authenticated")
      .then(response => {
        if (response.data === "false") {
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

  componentWillUnmount = () => {
    this.handleLogout();
  };

  render() {
    return <NavigationPresentation role={this.state.isUserAdmin} />;
  }
}

export default Navigation;
