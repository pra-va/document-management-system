import React, { Component } from "react";
import Table from "../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import Doc from "./../../../../resources/doc.svg";
import PopOver from "./../../../6-CommonElements/8-PopOver/PopOver";
import "./MyDocsTable.css";
import EditButton from "./EditDocButton";

class MyDocsTable extends Component {
  dataFields = ["number", "name", "type", "status", "date", "files", "edit"];
  columnNames = ["#", "Name", "Type", "Status", "Created", "Files", ""];
  tableData = [
    {
      number: "1",
      name: "Dummy Dock",
      type: "Vacation Request",
      status: "CREATED",
      date: "2020-02-20",
      files: (
        <PopOver
          popOverApparance={
            <img className="myDocsImg invert" src={Doc} alt={"..."} />
          }
          popOverTitle={"Attached:"}
          popOverContent={"doc1, doc2, doc3"}
        />
      ),
      edit: <EditButton />
    }
  ];

  render() {
    return (
      <Table
        id={"myDocsTable"}
        dataFields={this.dataFields}
        columnNames={this.columnNames}
        tableData={this.tableData}
        searchBarId={"createGroupUsersSearchBar"}
      />
    );
  }
}

export default MyDocsTable;
