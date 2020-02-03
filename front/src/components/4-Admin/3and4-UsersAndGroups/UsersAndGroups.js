import React, { Component } from "react";
import Table from "../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import axios from "axios";
import Navigation from "../../3-UserPage/01-MainWindow/01-Navigation/Navigation";
import "./UsersAndGroups.css";
import { Link } from "react-router-dom";
import EditButton from "./EditTableItemButton";

// forWhat={"users", "groups"}
class ListOfUsers extends Component {
  constructor(props) {
    super(props);
    this.state = {
      dbData: [],
      tableData: []
    };
  }

  usersTableDataFields = [
    "number",
    "name",
    "surname",
    "username",
    "role",
    "edit"
  ];
  usersTableNames = ["#", "Name", "Surname", "Username", "Role", ""];

  groupsTableDataFields = ["number", "name", "members", "edit"];
  groupsTableNames = ["#", "Group Name", "Members", ""];

  componentDidMount() {
    this.getData();
  }

  getData = () => {
    if (this.props.forWhat === "users") {
      this.connectForUsersData();
    } else {
      this.connectForGroupsData();
    }
  };

  connectForUsersData = () => {
    axios
      .get("http://localhost:8080/dvs/api/users")
      .then(response => {
        let tmpUsersData = response.data.map((item, index) => {
          return {
            number: index + 1,
            name: item.name,
            surname: item.surname,
            username: item.username,
            role: item.role,
            edit: <EditButton ownerName={item.name} ownerType={"user"} />
          };
        });

        this.setState({ tableData: tmpUsersData });
      })
      .catch(error => {
        console.log(error);
      });
  };

  connectForGroupsData = () => {
    axios
      .get("http://localhost:8080/dvs/api/groups")
      .then(response => {
        let tmpGroupsData = response.data.map((item, index) => {
          return {
            number: index + 1,
            name: item.name,
            members: item.userList.length,
            edit: <EditButton ownerName={item.name} ownerType={"group"} />
          };
        });

        this.setState({ tableData: tmpGroupsData });
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <div>
        <Navigation />
        <div className="container ">
          <div className="row d-flex justify-content-center p-5">
            <Link
              to="/users"
              className={
                this.props.forWhat === "users"
                  ? "btn btn-dark btn-lg m-3 disabled"
                  : "btn btn-dark btn-lg m-3"
              }
              id="buttonUsers"
            >
              Users
            </Link>
            <Link
              to="/groups"
              className={
                this.props.forWhat === "groups"
                  ? "btn btn-dark btn-lg m-3 disabled"
                  : "btn btn-dark btn-lg m-3"
              }
              id="buttonGroups"
            >
              Groups
            </Link>
          </div>
          <div className="row p-1" id="tableUsersGroups">
            <Table
              dataFields={
                this.props.forWhat === "users"
                  ? this.usersTableDataFields
                  : this.groupsTableDataFields
              }
              columnNames={
                this.props.forWhat === "users"
                  ? this.usersTableNames
                  : this.groupsTableNames
              }
              tableData={this.state.tableData}
              searchBarId={"addedGroupsSearchBar"}
            />
          </div>
        </div>
      </div>
    );
  }
}

export default ListOfUsers;
