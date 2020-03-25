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
    { dataField: "role", text: "Role", sort: true }
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
      .post(serverUrl + "user/nogroups", pageData)
      .then(response => {
        this.processData(response.data.userList);
        this.setState({ pagingData: response.data.pagingData });
      })
      .catch(error => {
        console.log(error);
      });
  };

  processData = data => {
    const tableData = data.content.map((item, index) => {
      return {
        number: index,
        name: item.name,
        surname: item.surname,
        username: item.username,
        role: item.role
      };
    });
    this.setState({ tableData: tableData });
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

  handleSelectAll = (isSelect, rows) => {
    rows.forEach(row => {
      setTimeout(() => {
        this.handleRowSelect(row, isSelect);
      }, 1);
    });
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
          handleSelectAll={this.handleSelectAll}
          setSelectedItems={this.setSelectedItems}
        />
      </div>
    );
  }
}

export default AddUsersToGroup;
