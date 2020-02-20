import React from "react";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import "./Table.css";

const AttachedFiles = props => {
  const dataFields = ["fileName", "remove"];
  const columnNames = ["File Name", ""];

  return (
    <Table
      id={"uploadedFilesTable"}
      dataFields={dataFields}
      columnNames={columnNames}
      tableData={props.values}
      searchBarId={"createGroupUsersSearchBar"}
    />
  );
};

export default AttachedFiles;
