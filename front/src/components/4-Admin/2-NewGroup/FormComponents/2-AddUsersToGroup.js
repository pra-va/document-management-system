import React, { Component } from "react";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";

class AddUsersToGroup extends Component {
  constructor(props) {
    super(props);
    this.state = { pagingData: {} };
  }

  columns = [
    { dataField: "number", text: "#", sort: false },
    { dataField: "name", text: "Name", sort: true },
    { dataField: "surname", text: "Surname", sort: true },
    { dataField: "username", text: "Username", sort: true },
    { dataField: "role", text: "Role", sort: true },
    { dataField: "add", text: "", sort: false }
  ];

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
        this.props.setUpData(response.data.usersList);
        this.setState({ pagingData: response.data.pagingData });
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

        <Table
          id={"newUserGroups"}
          tableData={this.props.notAddedUsers}
          searchBarId={"createGroupUsersSearchBar"}
          requestNewData={this.fetchGroupsData}
          pagingData={this.state.fetchUsersData}
          columns={this.columns}
          //
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
