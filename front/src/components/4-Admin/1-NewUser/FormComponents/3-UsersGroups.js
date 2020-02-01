import React, { Component } from "react";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";

class UsersGroup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      usersGroups: []
    };
  }

  dataFields = ["number", "name", "addOrRemove"];
  columnNames = ["#", "Name", "Add/Remove"];

  componentDidUpdate() {
    if (this.props.usersGroups.length !== this.state.usersGroups.length) {
      this.setState({ usersGroups: this.props.usersGroups });
    }
  }

  render() {
    return (
      <div className="m-3">
        <div className="row d-flex justify-content-start">
          <h3 className="d-flex justify-content-start">3. Users' groups.</h3>
        </div>

        <Table
          dataFields={this.dataFields}
          columnNames={this.columnNames}
          tableData={this.state.usersGroups}
          searchBarId={"addedGroupsSearchBar"}
        />
      </div>
    );
  }
}

export default UsersGroup;
