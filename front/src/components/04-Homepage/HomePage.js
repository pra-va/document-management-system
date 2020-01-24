import React, { Component } from "react";
import axios from "axios";

class AdminHomePage extends Component {
  constructor(props) {
    super(props);
    this.state = { userMessage: "", adminMessage: "" };
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

  handleLogout = event => {
    event.preventDefault();
    axios
      .get("http://localhost:8080/dvs/api/logout")
      .then(response => {
        console.log("Logged out.");
        this.props.history.push("/login");
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <div>
        <h1 className="m-3">{this.state.userMessage}</h1>
        <h1 className="m-3">{this.state.adminMessage}</h1>
        <button className="btn btn-info m-3" onClick={this.handleLogout}>
          Logout
        </button>
      </div>
    );
  }
}

export default AdminHomePage;
