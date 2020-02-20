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
    "add"
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

  // getGroupData = () => {
  //   axios
  //     .get(serverUrl + "groups/" + this.props.ownerName)
  //     .then(response => {
  //       // this.setState({
  //       //   groupName: response.data.name,
  //       //   groupDescription: response.data.description,
  //       //   role: response.data.role,
  //       //   usersList: response.data.userList,
  //       //   canCreate: response.data.docTypesToCreateNames,
  //       //   canSign: response.data.docTypesToApproveNames
  //       // });
  //       this.props.initalDataTransfer({
  //         groupName: response.data.name,
  //         groupDescription: response.data.description,
  //         role: response.data.role,
  //         usersList: response.data.userList,
  //         canCreate: response.data.docTypesToCreateNames,
  //         canSign: response.data.docTypesToApproveNames
  //       });
  //       this.props.setGroupUsers(response.data.groupList);
  //       console.log("LODING DATA----");
  //     })
  //     .catch(error => console.log(error));
  // };

  changeAddedStatus = name => {
    let tmpGroups = this.state.allUsers;
    for (let i = 0; i < tmpGroups.length; i++) {
      const element = tmpGroups[i];
      if (element.name === name) {
        tmpGroups[i].added = !tmpGroups[i].added;
        tmpGroups[i].addOrRemove = (
          <AddOrRemoveButton
            itemName={element.name}
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
    let filterGroups = this.state.allUsers;
    let notAdded = [];
    let added = [];
    for (let i = 0; i < filterGroups.length; i++) {
      const element = filterGroups[i];
      if (element.added) {
        added.push(element);
      } else {
        notAdded.push(element);
      }
    }
    this.setState({ notAddedUsers: notAdded });
    this.setState({ addedUsers: added });
    this.props.setAddeduserGroups(added);
  };
//tobe changed acording user edit
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
          tableData={this.props.notAddedUsers}
          searchBarId={"createGroupUsersSearchBar"}
        />
      </div>
    );
  }
}

export default AddUsersToGroup;
