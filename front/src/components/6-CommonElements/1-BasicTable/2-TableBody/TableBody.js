import React, { Component } from "react";
import TableRow from "./TableRow/TableRow";

class Table extends Component {
  constructor(props) {
    super(props);
    this.state = { tableValues: props.tableValues };
  }
  render() {
    var tableRows = this.state.tableValues.map((item, index) => {
      return <TableRow rowValues={item} />;
    });
    return <tbody>{tableRows}</tbody>;
  }
}

export default Table;
