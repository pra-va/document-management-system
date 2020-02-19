import React from "react";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";

const SelectType = props => {
  const doNothing = event => {
    event.preventDefault();
  };

  const selectedRow = row => {
    props.handleDocTypeSelect(row.type);
  };

  const dataFields = ["number", "type", "select"];
  const columnNames = ["#", "Type", ""];
  const tmpValues = [
    {
      number: 1,
      type: "Vocation",
      select: (
        <button className="btn btn-secondary btn-sm" onClick={doNothing}>
          Select
        </button>
      )
    },
    {
      number: 2,
      type: "Rise",
      select: (
        <button className="btn btn-secondary btn-sm" onClick={doNothing}>
          Select
        </button>
      )
    }
  ];

  return (
    <div>
      <h3 className="d-flex justify-content-start">2. Select document type.</h3>
      <Table
        select={true}
        id={"usersDocTypes"}
        dataFields={dataFields}
        columnNames={columnNames}
        tableData={tmpValues}
        searchBarId={"createGroupUsersSearchBar"}
        selectedRow={selectedRow}
      />
    </div>
  );
};

export default SelectType;
