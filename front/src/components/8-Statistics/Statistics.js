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
      username: "",
      startDate: "",
      endDate: "",
      oldStartDate: "",
      oldEndDate: "",
      tableData: [],
      fetchPagingData: {
        limit: 8,
        order: null,
        page: 0,
        searchValueString: "",
        sortBy: null
      },
      withFilter: false,
      serverData: [],
      sortBy: -1,
      sortOrder: -1,
      isNewFetchNeeded: true
    };
  }

  dateFormat = "yyyy-MM-dd";

  sortValues = {
    docTypeName: "da.name",
    submited: "submited",
    accepted: "accepted",
    declined: "declined"
  };

  columns = [
    { dataField: "docTypeName", text: "Doc. Type Name", sort: true },
    { dataField: "submited", text: "Submitted", sort: true },
    { dataField: "accepted", text: "Accepted", sort: true },
    { dataField: "declined", text: "Declined", sort: true }
  ];

  componentDidMount() {
    this.fetchUsername();
  }

  componentDidUpdate() {
    const { username, fetchPagingData, isNewFetchNeeded } = this.state;
    if (username.length > 0 && isNewFetchNeeded) {
      this.fetchServerData(
        fetchPagingData.page,
        fetchPagingData.limit,
        fetchPagingData.sortBy,
        fetchPagingData.order,
        fetchPagingData.searchValueString
      );
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
    const {
      username,
      startDate,
      endDate,
      sortBy,
      sortOrder,
      oldEndDate,
      oldStartDate,
      isNewFetchNeeded
    } = this.state;
    var modifiedSortField = null;
    if (sortField !== null) {
      modifiedSortField = this.sortValues[sortField];
    }

    const pageData = {
      limit: sizePerPage === undefined ? 8 : sizePerPage,
      order: order,
      page: Number.isNaN(page) ? 0 : page,
      sortBy: modifiedSortField,
      searchValueString: searchValueString
    };

    axios
      .post(serverUrl + "statisticsdtype", pageData, {
        params: {
          username: username,
          startDate: this.checkDate(startDate),
          endDate: this.checkDate(endDate)
        }
      })
      .then(response => {
        if (
          !this.isNewDataContainsSameElements(response.data.statistics) ||
          sortBy !== pageData.sortBy ||
          sortOrder !== pageData.order ||
          oldEndDate !== endDate ||
          oldStartDate !== startDate
        ) {
          if (isNewFetchNeeded) {
            this.parseData(response.data.statistics);
            this.setState({
              pagingData: response.data.pagingData,
              serverData: response.data.statistics,
              sortBy: pageData.sortBy,
              sortOrder: pageData.order,
              isNewFetchNeeded: false,
              oldEndDate: endDate,
              oldStartDate: startDate
            });
          }
        }
      })
      .catch(error => {
        console.log(error);
      });
  };

  isNewDataContainsSameElements = newServerData => {
    const { serverData } = this.state;
    let currentData = [...serverData];
    let newData = [...newServerData];

    if (currentData.length !== newData.length) {
      return false;
    } else {
      let tmpData = JSON.stringify(newData);
      for (let i = 0; i < currentData.length; i++) {
        const item = JSON.stringify(currentData[i]);
        if (tmpData.includes(item)) {
          continue;
        } else {
          return false;
        }
      }
      return true;
    }
  };

  parseData = data => {
    if (data !== undefined) {
      const tableData = data.map((item, index) => {
        return {
          number: index,
          docTypeName: item.docType,
          accepted: item.numberOfAccepted,
          declined: item.numberOfRejected,
          submited: item.numberOfSubmitted
        };
      });
      this.setState({ tableData: tableData });
    }
  };

  checkDate = date => {
    const { withFilter } = this.state;

    if (withFilter) {
      if (date !== "") {
        const changedDate = this.changeDateFormat(date);
        if (changedDate.length === 8) {
          return Number(this.changeDateFormat(date)) + 1;
        }
      }
    }
    return -1;
  };

  handleStartDateChange = date => {
    this.setState({
      startDate: date,
      isNewFetchNeeded: true
    });
  };

  handleEndDateChange = date => {
    this.setState({
      endDate: date,
      isNewFetchNeeded: true
    });
  };

  changeDateFormat = Date => {
    let temp = Date.toISOString().slice(0, 10);
    temp = temp.split("-").join("");
    return temp;
  };

  getPagingData = pagingData => {
    const { fetchPagingData } = this.state;
    const dataTmp = {
      limit:
        pagingData.limit === undefined
          ? fetchPagingData.limit
          : pagingData.limit,
      order: pagingData.order,
      page: Number.isNaN(pagingData.page)
        ? fetchPagingData.page
        : pagingData.page,
      sortBy: pagingData.sortBy,
      searchValueString: pagingData.searchValueString
    };

    if (
      JSON.stringify(pagingData) !== JSON.stringify(this.state.fetchPagingData)
    ) {
      this.setState({ fetchPagingData: dataTmp, isNewFetchNeeded: true });
    }
  };

  handleFilterToggle = event => {
    if (event.target.checked) {
      this.setState({ withFilter: true, isNewFetchNeeded: true });
    } else {
      this.setState({ withFilter: false, isNewFetchNeeded: true });
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
            <div className="form-inline">
              <DatePicker
                placeholderText="To Date"
                title="From"
                id="from"
                className="m-2 form-control"
                dateFormat={this.dateFormat}
                selected={this.state.startDate}
                onChange={this.handleStartDateChange}
              />
              <DatePicker
                placeholderText="From Date"
                id="to"
                title="To"
                className="m-2 form-control"
                dateFormat={this.dateFormat}
                selected={this.state.endDate}
                onChange={this.handleEndDateChange}
              />
              <div className="m-2 custom-control custom-checkbox">
                <input
                  type="checkbox"
                  className="custom-control-input"
                  id="customSwitch"
                  onClick={this.handleFilterToggle}
                />
                <label className="custom-control-label" htmlFor="customSwitch">
                  Use Date Filter
                </label>
              </div>
            </div>
          </div>
          <div className="p-1" id="tableStatisticsDType">
            <Table
              id={"statsByDType"}
              tableData={[...this.state.tableData]}
              searchBarId={"statsByDTypeSearchBar"}
              requestNewData={() => {}}
              getPagingData={this.getPagingData}
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
export default Statistics;
