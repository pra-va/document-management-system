import React, { Component } from "react";
import Table from "./../../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import axios from "axios";
import serverUrl from "./../../../../7-properties/1-URL";

class SelectType extends Component {
  constructor(props) {
    super(props);
    this.state = { tableData: [] };
  }

  componentDidUpdate() {
    if (this.props.username !== "" && this.state.tableData.length === 0) {
      this.fetchUserDocTypes(this.props.username);
    }
  }

  dataFields = ["number", "type", "select"];
  columnNames = ["#", "Type", ""];

  fetchUserDocTypes = username => {
    if (this.props.username !== "") {
      axios
        .get(serverUrl + username + "/dtypescreate")
        .then(response => {
          if (response.data.length !== 0 && response.data !== undefined) {
            this.processData(response.data);
          }
        })
        .catch(error => {
          console.log(error);
        });
    }
  };

  processData = data => {
    var tableData = data.map((item, index) => {
      return {
        number: index + 1,
        type: item,
        select: (
          <button className="btn btn-secondary btn-sm" onClick={this.doNothing}>
            Select
          </button>
        )
      };
    });
    this.setState({ tableData: tableData });
  };

  doNothing = event => {
    event.preventDefault();
  };

  selectedRow = row => {
    this.props.handleDocTypeSelect(row.type);
  };

  tmpValues = [
    {
      number: 1,
      type: "Vocation",
      select: (
        <button className="btn btn-secondary btn-sm" onClick={this.doNothing}>
          Select
        </button>
      )
    },
    {
      number: 2,
      type: "Rise",
      select: (
        <button className="btn btn-secondary btn-sm" onClick={this.doNothing}>
          Select
        </button>
      )
    }
  ];

  render() {
    return (
      <div>
        <h3 className="d-flex justify-content-start">
          2. Select document type.
        </h3>
        <Table
          select={true}
          id={"usersDocTypes"}
          dataFields={this.dataFields}
          columnNames={this.columnNames}
          tableData={this.state.tableData}
          searchBarId={"createGroupUsersSearchBar"}
          selectedRow={this.selectedRow}
        />
      </div>
    );
  }
}

export default SelectType;
