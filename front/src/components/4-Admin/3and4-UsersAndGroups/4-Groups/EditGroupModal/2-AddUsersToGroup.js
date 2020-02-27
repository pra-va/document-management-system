import React, { Component } from "react";
import Table from "./../../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import axios from "axios";
import serverUrl from "./../../../../7-properties/1-URL";
import AddOrRemoveButton from "./../../../../6-CommonElements/4-Buttons/1-AddRemove/ButtonAddOrRemove";

class AddUsersToGroup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tableData: [],
      groupUsers: [],
      allUsers: [],
      notAddedUsers: [],
      addedUsers: []
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
    this.fetchUsersData();
  }

  componentDidUpdate() {
    if (this.state.groupUsers.length !== this.props.groupUsers.length) {
      this.setState({ groupUsers: this.props.groupUsers });
    }
  }

  changeAddedStatus = name => {
    let tmpGroups = this.state.allUsers;
    for (let i = 0; i < tmpGroups.length; i++) {
      const element = tmpGroups[i];
      if (element.username === name) {
        tmpGroups[i].added = !tmpGroups[i].added;
        tmpGroups[i].addOrRemove = (
          <AddOrRemoveButton
            itemName={element.username}
            changeAddedStatus={this.changeAddedStatus}
            added={element.added}
          />
        );
        this.setState({ tableData: tmpGroups });
        this.filterAddedUsers();
      }
    }
  };

  filterAddedUsers = () => {
    let filterUsers = this.state.allUsers;
    let notAdded = [];
    let added = [];
    for (let i = 0; i < filterUsers.length; i++) {
      const element = filterUsers[i];
      if (element.added) {
        added.push(element);
      } else {
        notAdded.push(element);
      }
    }
    this.setState({ notAddedUsers: notAdded });
    this.setState({ addedUsers: added });
    this.props.setAddedUsers(added);
  };

  fetchUsersData = () => {
    axios
      .get(serverUrl + "users")
      .then(response => {
        //this.props.setUpData(response.data);
        let tempData = response.data.map((item, index) => {
          let isItemAdded = this.isUserAddedToGroup(item.username);
          return {
            number: index + 1,
            name: item.name,
            surname: item.surname,
            username: item.username,
            role: item.role,
            addOrRemove: (
              <AddOrRemoveButton
                itemName={item.username}
                changeAddedStatus={this.changeAddedStatus}
                added={isItemAdded}
              />
            ),
            added: isItemAdded
          };
        });
        this.setState({
          allUsers: tempData
        });
        this.filterAddedUsers();
      })
      .catch(error => {
        console.log(error);
      });
  };

  isUserAddedToGroup = userName => {
    for (let i = 0; i < this.state.groupUsers.length; i++) {
      const element = this.state.groupUsers[i];
      if (element === userName) {
        return true;
      }
    }
    return false;
  }

  render() {
    return (
      <div>
        <h3 className="d-flex justify-content-start">
          2. Add users to this group.
        </h3>
        <Table
          id={"newGroupUsers"}
          dataFields={this.usersTableDataFields}
          columnNames={this.usersTableNames}
          tableData={this.state.notAddedUsers}
          searchBarId={"createGroupUsersSearchBar"}
        />
      </div>
    );
  }
}

export default AddUsersToGroup;
