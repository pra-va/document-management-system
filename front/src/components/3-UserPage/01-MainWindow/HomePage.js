import React, { Component } from "react";
import axios from "axios";
import Navigation from "./01-Navigation/Navigation";
import Main from "./02-Main/Main";

class AdminHomePage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      userMessage: "",
      adminMessage: "",
      isUserAdmin: props.role
    };
  }

  componentDidMount() {
    this.message();
  }

  message = () => {
    axios
      .get("http://localhost:8080/dvs/api/user/")
      .then(response => {
        if (
          response.status === 200 &&
          this.state.userMessage !== response.data
        ) {
          this.setState({ userMessage: response.data });
        }
        console.log("user response");
        console.log(response);
      })
      .catch(error => {
        console.log(error);
      });

    axios
      .get("http://localhost:8080/dvs/api/admin/")
      .then(response => {
        if (
          response.status === 200 &&
          this.state.adminMessage !== response.data
        ) {
          this.setState({ adminMessage: response.data });
        }
        console.log("admin response");
        console.log(response);
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <div>
        <Navigation isUserAdmin={this.state.isUserAdmin} />
        <Main />
        <h1 className="m-3">{this.state.userMessage}</h1>
        <h1 className="m-3">{this.state.adminMessage}</h1>
      </div>
    );
  }
}

export default AdminHomePage;
