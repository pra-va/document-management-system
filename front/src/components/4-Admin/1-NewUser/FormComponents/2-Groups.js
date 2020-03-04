import React, { Component } from "react";
import "./2-Groups.css";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";

class Groups extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tableData: props.tableData,
      groupsData: [],
      selectedGroupNames: [],
      selectedItemsAsNumbers: []
    };
  }

  columns = [
    { dataField: "name", text: "Name", sort: true },
    { dataField: "addOrRemove", text: "", sort: false }
  ];

  dataFields = ["name", "addOrRemove"];
  columnNames = ["Name", ""];

  componentDidMount() {
    this.fetchGroupsData(0, 8, null, null, "");
  }

  componentDidUpdate() {
    if (this.props.tableData !== this.state.tableData) {
      this.setState({ tableData: this.props.tableData });
    }
  }

  fetchGroupsData = (
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
        this.props.setUpGroups(response.data.groupList);
        this.setState({ pagingData: response.data.pagingData });
      })
      .catch(error => {
        console.log(error);
      });
  };

  handleRowSelect = (row, isSelect) => {
    const { selectedGroupNames } = this.state;
    if (isSelect) {
      if (!selectedGroupNames.includes(row.name)) {
        selectedGroupNames.push(row.name);
      }
    } else {
      if (selectedGroupNames.includes(row.name)) {
        selectedGroupNames.splice(selectedGroupNames.indexOf(row.name), 1);
      }
    }
    console.log(this.state.selectedGroupNames);
  };

  setSelectedItems = () => {
    console.log("setting selected items");
    const { tableData, selectedGroupNames } = this.state;
    let selectedItemNumbersForTable = [];
    for (let index = 0; index < tableData.length; index++) {
      const element = tableData[index].name;
      console.log(element);
      console.log(selectedGroupNames);
      console.log(selectedGroupNames.includes(element));
      if (selectedGroupNames.includes(element)) {
        selectedItemNumbersForTable.push(index + 1);
      }
    }
    console.log(selectedItemNumbersForTable);
    return selectedItemNumbersForTable;
  };

  render() {
    return (
      <div className="mx-3">
        <div className="row d-flex justify-content-start">
          <h3 className="d-flex justify-content-start">
            2. Add user to a group.
          </h3>
        </div>

        <Table
          id={"newUserGroups"}
          dataFields={this.dataFields}
          columnNames={this.columnNames}
          tableData={this.state.tableData}
          searchBarId={"currentGroupsSearchBar"}
          dataLength={this.state.tableLengthData}
          requestNewData={this.fetchGroupsData}
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

export default Groups;
