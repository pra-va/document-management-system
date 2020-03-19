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
      tableData: [],
      username: "",
      initialFetch: false
    };
  }

  columns = [
    { dataField: "name", text: "First Name", sort: true },
    { dataField: "surname", text: "Last Name", sort: true },
    { dataField: "docNumber", text: "Number Of Documents", sort: true }
  ];

  componentDidMount() {
    this.fetchUsername();
  }

  componentDidUpdate() {
    const { username, initialFetch } = this.state;

    if (username !== "" && !initialFetch) {
      this.fetchServerData(0, 8, null, null, "");
    }
  }

  fetchUsername() {
    axios
      .get(serverUrl + "loggedin")
      .then(response => {
        this.setState({ username: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  }

  fetchServerData = (
    page,
    sizePerPage,
    sortField,
    order,
    searchValueString
  ) => {
    const { username } = this.state;
    const pageData = {
      limit: sizePerPage,
      order: order,
      page: page,
      sortBy: sortField,
      searchValueString: searchValueString
    };
    axios
      .post(serverUrl + "statisticsuser", pageData, {
        params: {
          username: username
        }
      })
      .then(response => {
        this.parseData(response.data.statistics);
        this.setState({
          pagingData: response.data.pagingData,
          initialFetch: true,
          serverData: response.data.statistics
        });
      })
      .catch(error => {
        console.log(error);
      });
  };

  parseData = data => {
    if (data) {
      const tableData = data.map((item, index) => {
        return {
          number: index,
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
          <div id="tableUserStatistics">
            <Table
              id={"statsByUser"}
              tableData={[...this.state.tableData]}
              searchBarId={"statsByUserSearch"}
              requestNewData={this.fetchServerData}
              pagingData={this.state.pagingData}
              columns={this.columns}
              selectType={"radio"}
              handleRowSelect={() => {}}
              handleSelectAll={() => {}}
              setSelectedItems={() => {}}
            />
          </div>
        </div>
      </div>
    );
  }
}
export default StatisticsByUser;
