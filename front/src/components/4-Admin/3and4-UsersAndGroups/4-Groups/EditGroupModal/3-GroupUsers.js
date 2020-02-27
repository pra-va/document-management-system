import React, { Component } from "react";
import Table from "./../../../../6-CommonElements/2-AdvancedTable/AdvancedTable";

class groupUsers extends Component {
  constructor(props) {
    super(props);
    this.state = {
      groupUsers: []
    };
  }

  usersTableDataFields = [
    "number",
    "name",
    "surname",
    "username",
    "role",
    "addOrRemove"
  ];
  usersTableNames = ["#", "Name", "Surname", "Username", "Role", ""];

  componentDidMount() {
    if (this.props.groupUsers) {
      if (this.props.groupUsers.length !== this.state.groupUsers.length) {
        this.setState({ groupUsers: this.props.groupUsers });
      }
    }
  }

  componentDidUpdate() {
    if (this.props.groupUsers) {
      if (this.props.groupUsers.length !== this.state.groupUsers.length) {
        this.setState({ groupUsers: this.props.groupUsers });
      }
    }
  }

  render() {
    return (
      <div>
        <h3 className="d-flex justify-content-start">
          3. Users added to a group.
        </h3>
        <Table
          id={"editGroupUsers"}
          dataFields={this.usersTableDataFields}
          columnNames={this.usersTableNames}
          tableData={this.state.groupUsers}
          searchBarId={"editGroupAddedUsersSearchBar"}
        />
      </div>
    );
  }
}

export default groupUsers;
