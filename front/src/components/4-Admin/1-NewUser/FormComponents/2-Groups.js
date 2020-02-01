import React, { Component } from "react";
import "./2-Groups.css";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";

class Groups extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tableData: props.tableData
    };
  }

  dataFields = ["number", "name", "addOrRemove"];
  columnNames = ["#", "Name", "Add/Remove"];

  componentDidUpdate() {
    if (this.props.tableData.length !== this.state.tableData.length) {
      this.setState({ tableData: this.props.tableData });
    }
    console.log("Table data updated");
  }

  render() {
    return (
      <div className="mx-3">
        <div className="row d-flex justify-content-start">
          <h3 className="d-flex justify-content-start">
            2. Add user to a group.
          </h3>
        </div>

        <Table
          dataFields={this.dataFields}
          columnNames={this.columnNames}
          tableData={this.state.tableData}
        />
      </div>
    );
  }
}

export default Groups;
