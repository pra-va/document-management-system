import React from "react";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";

const SignTable = props => {
  const dataFields = [
    "number",
    "name",
    "type",
    "submited",
    "createdBy",
    "files",
    "process"
  ];

  const columnNames = [
    "ID",
    "Name",
    "Type",
    "Submitted",
    "Created By",
    "Files",
    ""
  ];

  return (
    <div id="signDocumentsTable">
      <Table
        id={"uploadedFilesTableSearch"}
        dataFields={dataFields}
        columnNames={columnNames}
        tableData={props.values}
        searchBarId={"createGroupUsersSearchBar"}
      />
    </div>
  );
};

export default SignTable;
