import React, { Component } from "react";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";

class UsersGroup extends Component {
  dataFields = ["number", "name", "addOrRemove"];
  columnNames = ["#", "Name", "Add/Remove"];

  render() {
    return (
      <div className="m-3">
        <div className="row d-flex justify-content-start">
          <h3 className="d-flex justify-content-start">3. Users' groups.</h3>
        </div>

        <Table
          dataFields={this.dataFields}
          columnNames={this.columnNames}
          tableData={[]}
        />
      </div>
    );
  }
}

export default UsersGroup;
