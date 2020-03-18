import React, { Component } from "react";
import axios from "axios";
import Navigation from "./../3-UserPage/01-MainWindow/01-Navigation/Navigation";
import { Link } from "react-router-dom";
import serverUrl from "./../7-properties/1-URL";
import ContentWrapper from "./../6-CommonElements/10-TopContentWrapper/ContentWrapper";
import Table from "./../6-CommonElements/2-AdvancedTable/AdvancedTable";

class StatisticsByUser extends Component {
  constructor(props) {
    super(props);
    this.state = {
      docTypesData: [],
      serverData: [],
      tableData: []
    };
  }
  dataFields = ["docNumber", "name", "surname", "username"];
  columnNames = ["Number Of Docs", "Name", "Surname", "Username"];

  componentDidMount() {
    this.fetchServerData();
    this.parseData([
      {
        username: "admin",
        name: "Adminas",
        surname: "Adomauskas",
        docNumber: 3
      },
      {
        username: "tomdku",
        name: "Tomas",
        surname: "Domkus",
        docNumber: 69
      }
    ]);
  }

  fetchServerData = () => {
    console.log("FETCHING");
    axios
      .get(serverUrl + "/statisticsuser?username=admin")
      .then(response => {
        this.parseData(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  parseData = data => {
    console.log(data);
    if (data) {
      const tableData = data.map((item, index) => {
        return {
          username: item.username,
          name: item.name,
          surname: item.surname,
          docNumber: item.docNumber
        };
      });
      this.setState({ tableData: tableData });
    }
  };

  render() {
    return (
      <div>
        <Navigation />
        <div className="container">
          <ContentWrapper content={<h3>Statistics</h3>} />
          <div className="row d-flex justify-content-center px-5">
            <Link
              to="/dvs/statistics"
              className={"btn btn-secondary btn-lg m-3"}
              id="buttonUsers"
            >
              By Document Types
            </Link>
            <Link
              to="/dvs/statisticsbyuser"
              className={"btn btn-secondary btn-lg m-3 darker"}
              id="buttonGroups"
            >
              By Users
            </Link>
          </div>
          <div className="row p-1" id="tableUserStatistics">
            <Table
              id={"userStatistics"}
              dataFields={this.dataFields}
              columnNames={this.columnNames}
              tableData={this.state.tableData}
              searchBarId={"userStatisticsSearchBar"}
            />
          </div>
        </div>
      </div>
    );
  }
}
export default StatisticsByUser;
