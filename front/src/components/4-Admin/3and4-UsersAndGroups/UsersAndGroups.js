import React, { Component } from "react";
import Table from "../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import axios from "axios";
import Navigation from "../../3-UserPage/01-MainWindow/01-Navigation/Navigation";
import "./UsersAndGroups.css";
import { Link } from "react-router-dom";
import EditButton from "./EditTableItemButton";
import serverUrl from "./../../7-properties/1-URL";
import PopOver from "./../../6-CommonElements/8-PopOver/PopOver";
import GroupLogo from "./../../../resources/team.svg";

// forWhat={"users", "groups"}
class ListOfUsers extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tableData: [],
      pagingData: [],
      serverRequestPagingData: []
    };
  }

  groupsTableDataFields = ["number", "name", "members", "edit"];

  groupsTableNames = ["#", "Group Name", "Members", ""];

  columnsUsers = [
    { dataField: "name", text: "First Name", sort: true },
    { dataField: "surname", text: "Last Name", sort: true },
    { dataField: "username", text: "Username", sort: true },
    { dataField: "role", text: "Role", sort: true },
    { dataField: "edit", text: "", sort: false }
  ];

  columnsGroups = [
    { dataField: "name", text: "Group Name", sort: true },
    { dataField: "members", text: "Members", sort: false },
    { dataField: "edit", text: "", sort: false }
  ];

  componentDidMount() {
    this.getData();
  }

  getData = () => {
    if (this.props.forWhat === "users") {
      this.connectForUsersData(0, 8, null, null, "");
    } else {
      this.connectForGroupsData(0, 8, null, null, "");
    }
  };

  connectForUsersData = (
    page,
    sizePerPage,
    sortField,
    order,
    searchValueString
  ) => {
    const pageData = {
      limit: sizePerPage,
      order: order,
      page: page,
      sortBy: sortField,
      searchValueString: searchValueString
    };

    axios
      .post(serverUrl + "user/nogroups", pageData)
      .then(response => {
        let tmpUsersData = response.data.userList.content.map((item, index) => {
          return {
            number: index + 1,
            name: item.name,
            surname: item.surname,
            username: item.username,
            role: item.role,
            edit: (
              <EditButton
                ownerName={item.username}
                ownerType={"user"}
                reloadTable={this.reloadTable}
              />
            )
          };
        });
        this.setState({
          tableData: tmpUsersData,
          serverRequestPagingData: pageData,
          pagingData: response.data.pagingData
        });
      })
      .catch(error => {
        console.log(error);
      });
  };

  connectForGroupsData = (
    page,
    sizePerPage,
    sortField,
    order,
    searchValueString
  ) => {
    const pageData = {
      limit: sizePerPage,
      order: order,
      page: page,
      sortBy: sortField,
      searchValueString: searchValueString
    };

    axios
      .post(serverUrl + "groups", pageData)
      .then(response => {
        let tmpGroupsData = response.data.groupList.map((item, index) => {
          return {
            number: index + 1,
            name: item.name,
            members: (
              <PopOver
                popOverApparance={
                  <img
                    src={GroupLogo}
                    alt="unable to load"
                    className="invert"
                  />
                }
                popOverTitle={"Members list:"}
                popOverContent={
                  item.userList.length > 0
                    ? this.reduceList(item.userList)
                    : "None"
                }
              />
            ),
            edit: (
              <EditButton
                ownerName={item.name}
                ownerType={"group"}
                reloadTable={this.reloadTable}
              />
            )
          };
        });

        this.setState({
          tableData: tmpGroupsData,
          serverRequestPagingData: pageData,
          pagingData: response.data.pagingData
        });
      })
      .catch(error => {
        console.log(error);
      });
  };

  reduceList = data => {
    let sum = "";
    for (let i = 0; i < data.length; i++) {
      const element = data[i];
      if (i === 0) {
        sum = element;
      } else if (i < 5) {
        sum += ", " + element;
      } else {
        sum += " and " + Number(data.length - 5) + " more...";
        break;
      }
    }
    return sum;
  };

  reloadTable = () => {
    const { serverRequestPagingData } = this.state;
    setTimeout(() => {
      this.setState({ tableData: [] });
    }, 1);

    if (this.props.forWhat === "users") {
      this.connectForUsersData(
        serverRequestPagingData.page,
        serverRequestPagingData.limit,
        serverRequestPagingData.sortBy,
        serverRequestPagingData.order,
        serverRequestPagingData.searchValueString
      );
    } else if (this.props.forWhat === "groups") {
      this.connectForGroupsData(
        serverRequestPagingData.page,
        serverRequestPagingData.limit,
        serverRequestPagingData.sortBy,
        serverRequestPagingData.order,
        serverRequestPagingData.searchValueString
      );
    }
  };

  render() {
    return (
      <div>
        <Navigation />
        <div className="container ">
          <div className="row d-flex justify-content-center p-5">
            <Link
              to="/dvs/users"
              className={
                this.props.forWhat === "users"
                  ? "btn btn-secondary btn-lg m-3 darker"
                  : "btn btn-secondary btn-lg m-3"
              }
              id="buttonUsers"
            >
              Users
            </Link>
            <Link
              to="/dvs/groups"
              className={
                this.props.forWhat === "groups"
                  ? "btn btn-secondary btn-lg m-3 darker"
                  : "btn btn-secondary btn-lg m-3"
              }
              id="buttonGroups"
            >
              Groups
            </Link>
            <Link
              to="/dvs/doctypes"
              className={"btn btn-secondary btn-lg m-3"}
              id="buttonGroups"
            >
              Document Types
            </Link>
          </div>
          <div className="p-1" id="tableuserGroups">
            {this.props.forWhat === "users" ? (
              <Table
                id={"listOfUsers"}
                tableData={this.state.tableData}
                searchBarId={"usersSearchBar"}
                requestNewData={this.connectForUsersData}
                pagingData={this.state.pagingData}
                columns={this.columnsUsers}
                selectType={"radio"}
                handleRowSelect={() => {}}
                setSelectedItems={() => {}}
              />
            ) : (
              <Table
                id={"listOfGroups"}
                tableData={this.state.tableData}
                searchBarId={"groupsSearchBar"}
                requestNewData={this.connectForGroupsData}
                pagingData={this.state.pagingData}
                columns={this.columnsGroups}
                selectType={"radio"}
                handleRowSelect={() => {}}
                setSelectedItems={() => {}}
              />
            )}
          </div>
        </div>
      </div>
    );
  }
}

export default ListOfUsers;
