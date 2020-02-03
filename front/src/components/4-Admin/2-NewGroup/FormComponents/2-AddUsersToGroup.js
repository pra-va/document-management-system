import React from "react";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";

var AddUsersToGroup = props => {
  const usersTableDataFields = [
    "number",
    "name",
    "surname",
    "username",
    "role",
    "add"
  ];
  const usersTableNames = ["#", "Name", "Surname", "Username", "Role", ""];

  return (
    <div>
      <h3 className="d-flex justify-content-start">2. Add group users.</h3>
      <Table
        dataFields={usersTableDataFields}
        columnNames={usersTableNames}
        tableData={props.notAddedUsers}
        searchBarId={"createGroupUsersSearchBar"}
      />
    </div>
  );
};

export default AddUsersToGroup;
