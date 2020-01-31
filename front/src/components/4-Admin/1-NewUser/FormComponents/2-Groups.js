import React, { Component } from "react";
import "./2-Groups.css";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import axios from "axios";
import AddOrRemoveButton from "./4-ButtonAddOrRemoveUserGroup";

class Groups extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tableData: []
    };
  }

  dataFields = ["number", "name", "addOrRemove"];
  columnNames = ["#", "Name", "Add/Remove"];

  renderTable = () => {
    axios
      .get("http://localhost:8080/dvs/api/groups")
      .then(response => {
        let tempData = response.data.map((item, index) => {
          return {
            number: index + 1,
            name: item.name,
            addOrRemove: <AddOrRemoveButton />
          };
        });
        this.setState({ tableData: tempData });
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.renderTable();
  }

  render() {
    return (
      <div className="mx-3">
        <div className="row d-flex justify-content-start">
          <h3 className="d-flex justify-content-start">
            2. Add user to a group.
          </h3>
        </div>

        <Table
          dataFields={this.dataFields}
          columnNames={this.columnNames}
          tableData={this.state.tableData}
        />
      </div>
    );
  }
}

export default Groups;
