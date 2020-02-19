import React from "react";
import Table from "./../../../../6-CommonElements/2-AdvancedTable/AdvancedTable";

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
      <h3 className="d-flex justify-content-start">
        3. Users added to a group.
      </h3>
      <Table
        id={"newUserAddedGroups"}
        dataFields={usersTableDataFields}
        columnNames={usersTableNames}
        tableData={props.addedUsers}
        searchBarId={"createGroupAddedUsersSearchBar"}
      />
    </div>
  );
};

export default AddUsersToGroup;
