import React, { Component } from "react";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";

class AddUsersToGroup extends Component {
  usersTableDataFields = [
    "number",
    "name",
    "surname",
    "username",
    "role",
    "add"
  ];
  usersTableNames = ["#", "Name", "Surname", "Username", "Role", ""];

  fetchUsersData = () => {
    axios
      .get(serverUrl + "users")
      .then(response => {
        this.props.setUpData(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.fetchUsersData();
  }

  render() {
    return (
      <div>
        <h3 className="d-flex justify-content-start">2. Add group users.</h3>
        <Table
          id={"newGroupUsers"}
          dataFields={this.usersTableDataFields}
          columnNames={this.usersTableNames}
          tableData={this.props.notAddedUsers}
          searchBarId={"createGroupUsersSearchBar"}
        />
      </div>
    );
  }
}

export default AddUsersToGroup;
