import React, { Component } from "react";
import Navigation from "./../../3-UserPage/01-MainWindow/01-Navigation/Navigation";
import Table from "./../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import GroupLogo from "./../../../resources/team.svg";
import "./DocTypes.css";
import PopOver from "./../../6-CommonElements/8-PopOver/PopOver";
import Edit from "./EditButton";

class DocTypes extends Component {
  constructor(props) {
    super(props);
    this.state = {
      docTypesData: [],
      tableData: [
        {
          number: 1,
          name: "asdf",
          canCreate: (
            <PopOver
              popOverApparance={
                <img src={GroupLogo} alt="unable to load" className="invert" />
              }
              popOverTitle={"Groups able to create"}
              popOverContent={"adsf, asdf, asdf."}
            />
          ),
          canSign: (
            <PopOver
              popOverApparance={
                <img src={GroupLogo} alt="unable to load" className="invert" />
              }
              popOverTitle={"Groups able to sign"}
              popOverContent={"adsf, asdf, asdf."}
            />
          ),
          edit: <Edit />
        }
      ]
    };
  }

  dataFields = ["number", "name", "canCreate", "canSign", "edit"];
  columnNames = ["#", "Name", "Creating Groups", "Signing Groups", ""];

  render() {
    return (
      <div>
        <Navigation />
        <div className="container">
          <h1>DOCTYPES</h1>
          <Table
            dataFields={this.dataFields}
            columnNames={this.columnNames}
            tableData={this.state.tableData}
            searchBarId={"docTypeSearchBar"}
          />
        </div>
      </div>
    );
  }
}

export default DocTypes;
