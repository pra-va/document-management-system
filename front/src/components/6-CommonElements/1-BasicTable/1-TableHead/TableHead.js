import React, { Component } from "react";

class TableHead extends Component {
  constructor(props) {
    super(props);
    this.state = { columnNames: props.columnNames };
  }

  render() {
    var tableHeadColumns = this.state.columnNames.map((item, index) => {
      return <th scope="col">{item}</th>;
    });
    return (
      <thead>
        <tr>{tableHeadColumns}</tr>
      </thead>
    );
  }
}

export default TableHead;
