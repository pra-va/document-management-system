import React, { Component } from "react";
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
