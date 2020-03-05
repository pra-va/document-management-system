import React from "react";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import "./Table.css";
import Validation from "./../../../6-CommonElements/5-FormInputValidationLine/Validation";

const AttachedFiles = props => {
  const dataFields = ["fileName", "size", "remove"];
  const columnNames = ["File Name", "Size", ""];

  return (
    <div>
      <Table
        id={"uploadedFilesTable"}
        dataFields={dataFields}
        columnNames={columnNames}
        tableData={props.values}
        searchBarId={"createGroupUsersSearchBar"}
      />
      <Validation
        satisfied={props.size < 20000000 ? true : false}
        output="File size can not be greater than 20 MB"
      />
    </div>
  );
};

export default AttachedFiles;
