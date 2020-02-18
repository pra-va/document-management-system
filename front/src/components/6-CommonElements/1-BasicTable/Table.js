import React, { Component } from "react";
import TableHead from "./1-TableHead/TableHead";
import TableBody from "./2-TableBody/TableBody";

class Table extends Component {
  render() {
    return (
      <table class="table table-striped table-dark">
        <TableHead columnNames={this.props.columnNames} />
        <TableBody tableValues={this.props.tableValues} />
      </table>
    );
  }
}

export default Table;
