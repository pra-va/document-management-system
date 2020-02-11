import React, { Component } from "react";
import Navigation from "./../../3-UserPage/01-MainWindow/01-Navigation/Navigation";

class DocTypes extends Component {
  constructor(props) {
    super(props);
    this.state = { docTypesData: [] };
  }

  render() {
    return (
      <div>
        <Navigation />
        <h1>DOCTYPES</h1>
      </div>
    );
  }
}

export default DocTypes;
