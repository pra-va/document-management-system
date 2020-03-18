import React, { Component } from "react";
import axios from "axios";
import Navigation from "./../3-UserPage/01-MainWindow/01-Navigation/Navigation";
import { Link } from "react-router-dom";
import serverUrl from "./../7-properties/1-URL";
import ContentWrapper from "./../6-CommonElements/10-TopContentWrapper/ContentWrapper";
import Table from "./../6-CommonElements/2-AdvancedTable/AdvancedTable";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

class Statistics extends Component {
  constructor(props) {
    super(props);
    this.state = {
      startDate: new Date("2020-01-01"),
      endDate: new Date(),
      tableData: []
    };
  }
  dateFormat = "yyyy-MM-dd";
  dataFields = [
    "docType",
    "numberOfAccepted",
    "numberOfRejected",
    "numberOfSubmitted"
  ];
  columnNames = ["Document type", "Accepted", "Rejected", "Submited"];
  columnsByDoc = [
    { dataField: "docType", text: "Document type", sort: true },
    { dataField: "numberOfAccepted", text: "Accepted", sort: true },
    { dataField: "numberOfRejected", text: "Rejected", sort: true },
    { dataField: "numberOfSubmitted", text: "Submited", sort: true }
  ];

  componentDidMount() {
    //this.fetchServerData();
    this.parseData([
      {
        docType: "Prasymas",
        numberOfAccepted: 5,
        numberOfRejected: 2,
        numberOfSubmitted: 7
      }
    ]);
    var tmp = this.changeDateFormat(this.state.endDate);
    console.log(tmp);
  }

  fetchServerData = () => {
    axios
      .get(serverUrl + "/statisticsdtype", {
        params: {
          username: "admin"
        }
      })
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
          docType: item.docType,
          numberOfAccepted: item.numberOfAccepted,
          numberOfRejected: item.numberOfRejected,
          numberOfSubmitted: item.numberOfSubmitted
        };
      });
      this.setState({ tableData: tableData });
    }
  };

  handleStartDateChange = date => {
    this.setState({
      startDate: date
    });
  };
  handleEndDateChange = date => {
    this.setState({
      endDate: date
    });
  };

  changeDateFormat = Date => {
    let temp = Date.toISOString().slice(0, 10);
    temp = temp.split("-").join("");
    return temp;
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
              className={"btn btn-secondary btn-lg m-3 darker"}
              id="buttonUsers"
            >
              By Document Types
            </Link>
            <Link
              to="/dvs/statisticsbyuser"
              className={"btn btn-secondary btn-lg m-3"}
              id="buttonGroups"
            >
              By Users
            </Link>
          </div>
          <div className="row d-flex justify-content-center px-5">
            {/* <div className="col-6"> */}
            <DatePicker
              title="From"
              dateFormat={this.dateFormat}
              selected={this.state.startDate}
              onChange={this.handleStartDateChange}
            />
            {/* </div> */}
            {/* <div className="col-6"> */}
            <DatePicker
              title="To"
              dateFormat={this.dateFormat}
              selected={this.state.endDate}
              onChange={this.handleEndDateChange}
            />
            {/* </div> */}
          </div>
          <div className="row p-1" id="tableStatisticsDType">
            <Table
              id={"docTypes"}
              dataFields={this.dataFields}
              columnNames={this.columnNames}
              tableData={this.state.tableData}
              searchBarId={"docTypeSearchBar"}
              columns={this.columnsByDoc}
                // requestNewData={this.connectForUsersData}
                // pagingData={this.state.pagingData}
              
                // selectType={"radio"}
                // handleRowSelect={() => {}}
                // setSelectedItems={() => {}}
            />
          </div>
        </div>
      </div>
    );
  }
}
export default Statistics;
