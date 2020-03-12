import React, { Component } from "react";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";

class AddUsersToGroup extends Component {
  constructor(props) {
    super(props);
    this.state = { pagingData: {}, selectedUsers: [], tableData: [] };
  }

  columns = [
    // { dataField: "number", text: "#", sort: false },
    { dataField: "name", text: "Name", sort: true },
    { dataField: "surname", text: "Surname", sort: true },
    { dataField: "username", text: "Username", sort: true },
    { dataField: "role", text: "Role", sort: true },
    { dataField: "add", text: "", sort: false }
  ];

  componentDidMount() {
    this.fetchUsersData(0, 8, null, null, "");
  }

  fetchUsersData = (page, sizePerPage, sortField, order, searchValueString) => {
    const pageData = {
      limit: sizePerPage,
      order: order,
      page: page,
      sortBy: sortField,
      searchValueString: searchValueString
    };

    axios
      .post(serverUrl + "users", pageData)
      .then(response => {
        this.processData(response.data.userList);
        this.setState({ pagingData: response.data.pagingData });
      })
      .catch(error => {
        console.log(error);
      });
  };

  processData = data => {
    const tableData = data.map((item, index) => {
      return {
        number: index,
        name: item.name,
        surname: item.surname,
        username: item.username,
        role: item.role,
        add: (
          <button
            onClick={event => {
              event.preventDefault();
            }}
            className={
              this.state.selectedUsers.includes(item.username)
                ? "btn btn-danger btn-sm"
                : "btn btn-secondary btn-sm"
            }
          >
            {this.state.selectedUsers.includes(item.username)
              ? "Remove"
              : "Add"}
          </button>
        )
      };
    });
    this.setState({ tableData: tableData });
  };

  refreshTableData = addedUsers => {
    const tableDataTmp = [...this.state.tableData];
    for (let i = 0; i < tableDataTmp.length; i++) {
      const element = tableDataTmp[i];
      tableDataTmp[i].add = (
        <button
          onClick={event => {
            event.preventDefault();
          }}
          className={
            addedUsers.includes(element.username)
              ? "btn btn-danger btn-sm"
              : "btn btn-secondary btn-sm"
          }
        >
          {addedUsers.includes(element.username) ? "Remove" : "Add"}
        </button>
      );

      this.setState({ tableData: this.loadTable() });
      setTimeout(() => {
        this.setState({ tableData: tableDataTmp });
      }, 1);
    }
  };

  loadTable = () => {
    const { tableData } = this.state;
    const tmpTableData = [];
    for (let i = 0; i < tableData.length; i++) {
      const element = tableData[i];
      tmpTableData.push({
        number: i,
        name: element.name,
        surname: element.surname,
        username: element.username,
        role: element.role,
        add: ""
      });
    }
    return tmpTableData;
  };

  handleRowSelect = (row, isSelect) => {
    const selectedUsers = this.state.selectedUsers;
    if (isSelect) {
      if (!selectedUsers.includes(row.username)) {
        selectedUsers.push(row.username);
      }
    } else {
      if (selectedUsers.includes(row.username)) {
        selectedUsers.splice(selectedUsers.indexOf(row.username), 1);
      }
    }
    this.setState({ selectedUsers: selectedUsers });
    this.props.setAddedUsers(selectedUsers);
    this.refreshTableData(selectedUsers);
  };

  setSelectedItems = () => {
    const { tableData, selectedUsers } = this.state;
    let selectedItemNumbersForTable = [];
    for (let index = 0; index < tableData.length; index++) {
      const element = tableData[index].username;
      if (selectedUsers.includes(element)) {
        selectedItemNumbersForTable.push(index);
      }
    }

    return selectedItemNumbersForTable;
  };

  render() {
    return (
      <div>
        <h3 className="d-flex justify-content-start">2. Add group users.</h3>

        <Table
          id={"newUserGroups"}
          tableData={this.state.tableData}
          searchBarId={"createGroupUsersSearchBar"}
          requestNewData={this.fetchUsersData}
          pagingData={this.state.pagingData}
          columns={this.columns}
          selectType={"checkbox"}
          select={"true"}
          handleRowSelect={this.handleRowSelect}
          setSelectedItems={this.setSelectedItems}
        />
      </div>
    );
  }
}

export default AddUsersToGroup;
