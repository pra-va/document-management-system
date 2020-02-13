import React, { Component } from "react";
import Navigation from "./../../3-UserPage/01-MainWindow/01-Navigation/Navigation";
import Table from "./../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import GroupLogo from "./../../../resources/team.svg";
import "./DocTypes.css";
import PopOver from "./../../6-CommonElements/8-PopOver/PopOver";
import Edit from "./EditButton";
import axios from "axios";
import serverUrl from "./../../7-properties/1-URL";

class DocTypes extends Component {
  constructor(props) {
    super(props);
    this.state = {
      docTypesData: [],
      serverData: [],
      tableData: []
    };
  }

  componentDidMount() {
    this.fetchServerData();
  }

  dataFields = ["number", "name", "canCreate", "canSign", "edit"];
  columnNames = ["#", "Name", "Creating Groups", "Signing Groups", ""];

  fetchServerData = () => {
    axios
      .get(serverUrl + "doct/all")
      .then(response => {
        this.parseData(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  parseData = data => {
    if (data) {
      const tableData = data.map((item, index) => {
        return {
          number: index + 1,
          name: item.name,
          canCreate: (
            <PopOver
              popOverApparance={
                <img src={GroupLogo} alt="unable to load" className="invert" />
              }
              popOverTitle={"Groups with Create rights:"}
              popOverContent={
                item.creating.length > 0
                  ? this.reduceList(item.creating)
                  : "None"
              }
            />
          ),
          canSign: (
            <PopOver
              popOverApparance={
                <img src={GroupLogo} alt="unable to load" className="invert" />
              }
              popOverTitle={"Groups with Sign rights:"}
              popOverContent={
                item.approving.length > 0
                  ? this.reduceList(item.approving)
                  : "None"
              }
            />
          ),
          edit: <Edit owner={item.name} />
        };
      });
      this.setState({ tableData: tableData });
    }
  };

  reduceList = data => {
    if (data) {
      let reducedList = data.reduce((sum, item, index) => {
        if (index === 0) {
          return sum + item;
        } else {
          return sum + ", " + item;
        }
      });
      reducedList += ".";
      return reducedList;
    }
  };

  render() {
    return (
      <div>
        <Navigation />
        <div className="container">
          <h1>DOCTYPES</h1>
          <Table
            id={"docTypes"}
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
