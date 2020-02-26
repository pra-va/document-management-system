import React, { Component } from "react";
import Table from "../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import Doc from "./../../../../resources/doc.svg";
import PopOver from "./../../../6-CommonElements/8-PopOver/PopOver";
import "./MyDocsTable.css";
import EditButton from "./EditDocButton";
import ViewButton from "./ViewButton";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";

class MyDocsTable extends Component {
  constructor(props) {
    super(props);
    this.state = { username: "", tableData: [] };
  }
  dataFields = [
    "number",
    "name",
    "type",
    "status",
    "date",
    "files",
    "edit",
    "submit"
  ];
  columnNames = ["#", "Name", "Type", "Status", "Created", "Files", "", ""];
  tableData = [];

  componentDidMount() {
    this.getUsername();
  }

  getUsername = () => {
    axios
      .get(serverUrl + "loggedin")
      .then(response => {
        this.setState({ username: response.data });
        this.fetchData(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  fetchData = () => {
    axios
      .get(serverUrl + this.state.username + "/alldocuments")
      .then(response => {
        this.processData(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  processData = data => {
    const tableData = data.map((item, index) => {
      return {
        number: index + 1,
        name: item.name,
        type: item.type,
        status: item.status,
        date: item.dateCreate.substring(0, 10),
        files: (
          <PopOver
            popOverApparance={
              <img className="myDocsImg invert" src={Doc} alt={"..."} />
            }
            popOverTitle={"Attached:"}
            popOverContent={
              item.filesAttached.length > 0
                ? this.reduceItemsList(item.filesAttached)
                : "None"
            }
          />
        ),
        edit:
          item.status === "CREATED" ? (
            <EditButton item={item} />
          ) : (
            <ViewButton item={item} />
          ),
        uid: item.uid
      };
    });

    this.setState({ tableData: tableData });
  };

  reduceItemsList = itemsList => {
    return itemsList.reduce((sum, item, index) => {
      if (index === 0) {
        return (sum = item.fileName);
      } else {
        return (sum += ", " + item.fileName);
      }
    }, "");
  };

  render() {
    return (
      <Table
        id={"myDocsTable"}
        dataFields={this.dataFields}
        columnNames={this.columnNames}
        tableData={this.state.tableData}
        searchBarId={"createGroupUsersSearchBar"}
      />
    );
  }
}

export default MyDocsTable;
