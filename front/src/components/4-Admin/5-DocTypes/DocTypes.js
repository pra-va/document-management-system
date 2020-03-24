import React, { Component } from "react";
import Navigation from "./../../3-UserPage/01-MainWindow/01-Navigation/Navigation";
import Table from "./../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import GroupLogo from "./../../../resources/team.svg";
import "./DocTypes.css";
import PopOver from "./../../6-CommonElements/8-PopOver/PopOver";
import Edit from "./EditButton";
import axios from "axios";
import serverUrl from "./../../7-properties/1-URL";
import { Link } from "react-router-dom";

class DocTypes extends Component {
  constructor(props) {
    super(props);
    this.state = {
      docTypesData: [],
      serverData: [],
      tableData: [],
      pagingData: [],
      serverRequestPagingData: []
    };
  }

  componentDidMount() {
    this.fetchServerData(0, 8, null, null, "");
  }

  dataFields = ["number", "name", "canCreate", "canSign", "edit"];
  columnNames = ["#", "Name", "Creating Groups", "Signing Groups", ""];

  columns = [
    { dataField: "name", text: "Type", sort: true },
    { dataField: "canCreate", text: "Creating Groups", sort: false },
    { dataField: "canSign", text: "Signing Groups", sort: false },
    { dataField: "edit", text: "", sort: false }
  ];

  fetchServerData = (
    page,
    sizePerPage,
    sortField,
    order,
    searchValueString
  ) => {
    const pageData = {
      limit: sizePerPage,
      order: order,
      page: page,
      sortBy: sortField,
      searchValueString: searchValueString
    };

    axios
      .post(serverUrl + "doct/all/nogroups", pageData)
      .then(response => {
        this.parseData(response.data);
        this.setState({
          pagingData: response.data.pagingData,
          serverRequestPagingData: pageData
        });
      })
      .catch(error => {
        console.log(error);
      });
  };

  parseData = data => {
    if (data) {
      const tableData = data.documentList.content.map((item, index) => {
        return {
          number: index + 1,
          name: item,
          canCreate: (
            <PopOver
              popOverApparance={
                <img src={GroupLogo} alt="unable to load" className="invert" />
              }
              popOverTitle={"Groups with Create rights:"}
              popOverContent={this.reduceList(data.creating[item])}
            />
          ),
          canSign: (
            <PopOver
              popOverApparance={
                <img src={GroupLogo} alt="unable to load" className="invert" />
              }
              popOverTitle={"Groups with Sign rights:"}
              popOverContent={this.reduceList(data.approving[item])}
            />
          ),
          edit: <Edit owner={item} reloadTable={this.reloadTable} />
        };
      });
      this.setState({ tableData: tableData });
    }
  };

  reloadTable = () => {
    const { serverRequestPagingData } = this.state;
    setTimeout(() => {
      this.setState({ tableData: [] });
    }, 1);
    this.fetchServerData(
      serverRequestPagingData.page,
      serverRequestPagingData.limit,
      serverRequestPagingData.sortBy,
      serverRequestPagingData.order,
      serverRequestPagingData.searchValueString
    );
  };

  reduceList = data => {
    let sum = "";
    for (let i = 0; i < data.length; i++) {
      const element = data[i];
      if (i === 0) {
        sum = element;
      } else if (i < 5) {
        sum += ", " + element;
      } else {
        sum += " and " + Number(data.length - 5) + " more...";
        break;
      }
    }
    return sum;
  };

  render() {
    return (
      <div>
        <Navigation />
        <div className="container">
          <div className="row d-flex justify-content-center p-5">
            <Link
              to="/dvs/users"
              className={"btn btn-secondary btn-lg m-3"}
              id="buttonUsers"
            >
              Users
            </Link>
            <Link
              to="/dvs/groups"
              className={"btn btn-secondary btn-lg m-3"}
              id="buttonGroups"
            >
              Groups
            </Link>
            <Link
              to="/dvs/doctypes"
              className={"btn btn-secondary btn-lg m-3 darker"}
              id="buttonGroups"
            >
              Document Types
            </Link>
          </div>

          <Table
            id={"docTypes"}
            tableData={this.state.tableData}
            searchBarId={"docTypesSearchBar"}
            requestNewData={this.fetchServerData}
            pagingData={this.state.pagingData}
            columns={this.columns}
            selectType={"radio"}
            handleRowSelect={() => {}}
            setSelectedItems={() => {}}
          />
        </div>
      </div>
    );
  }
}

export default DocTypes;
