import React, { Component } from "react";

class TableRow extends Component {
  constructor(props) {
    super(props);
    this.state = { rowValues: props.rowValues };
  }

  render() {
    var rowValues = this.state.rowValues.map((item, index) => {
      return <td>{item}</td>;
    });

    return <tr>{rowValues}</tr>;
  }
}

export default TableRow;
