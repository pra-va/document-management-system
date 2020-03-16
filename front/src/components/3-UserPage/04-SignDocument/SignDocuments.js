import React, { Component } from "react";
import Navigation from "./../01-MainWindow/01-Navigation/Navigation";
import Table from "./../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import axios from "axios";
import serverUrl from "./../../7-properties/1-URL";
import Files from "./../../../resources/doc.svg";
import PopOver from "../../6-CommonElements/8-PopOver/PopOver";
import SignOrRejectButton from "./SignOrRejectButton";
import ContentWrapper from "./../../6-CommonElements/10-TopContentWrapper/ContentWrapper";
import "./SignDocument.css";

export default class SignDocuments extends Component {
  constructor(props) {
    super(props);
    this.state = {
      serverData: [],
      username: "",
      tableData: [],
      pagingData: {}
    };
  }

  columns = [
    { dataField: "number", text: "ID", sort: true },
    { dataField: "name", text: "Name", sort: true },
    { dataField: "type", text: "Type", sort: true },
    { dataField: "submited", text: "Submitted", sort: true },
    { dataField: "createdBy", text: "Created By", sort: true },
    { dataField: "files", text: "Files", sort: false },
    { dataField: "process", text: "", sort: false }
  ];

  sortValues = {
    number: "d.id",
    name: "d.name",
    type: "dt.name",
    submited: "d.dateSubmit",
    createdBy: "u.name"
  };

  componentDidMount() {
    this.getUsername();
  }

  componentDidUpdate() {}

  getUsername = () => {
    axios
      .get(serverUrl + "loggedin")
      .then(response => {
        this.setState({ username: response.data });
        this.fetchDocumentsToBeSigned(0, 8, null, null, "");
      })
      .catch(error => {
        console.log(error);
      });
  };

  fetchDocumentsToBeSigned = (
    page,
    sizePerPage,
    sortField,
    order,
    searchValueString
  ) => {
    var modifiedSortField = null;
    if (sortField !== null) {
      modifiedSortField = this.sortValues[sortField];
    }

    const pageData = {
      limit: sizePerPage,
      order: order,
      page: page,
      sortBy: modifiedSortField,
      searchValueString: searchValueString
    };

    axios
      .post(serverUrl + this.state.username + "/doctobesigned", pageData)
      .then(response => {
        this.setState({
          serverData: response.data.documents,
          pagingData: response.data.pagingData
        });
        this.processData(response.data.documents);
      })
      .catch(error => {
        console.log(error);
      });
  };

  processData = data => {
    const tableDataTmp = data.map((item, index) => {
      return {
        uid: item.uid,
        number: item.uid,
        name: item.name,
        type: item.type,
        submited: item.dateSubmit.substring(0, 10),
        createdBy: item.author,
        files: (
          <PopOver
            popOverApparance={
              <img className="invert img-resize" src={Files} alt="..." />
            }
            popOverTitle={"Attached files:"}
            popOverContent={this.reduceFilesAttached(item.filesAttached)}
          />
        ),
        process: <SignOrRejectButton item={item} />
      };
    });

    this.setState({ tableData: tableDataTmp });
  };

  reduceFilesAttached = fileslist => {
    return fileslist.reduce((sum, item, index) => {
      if (index === 0) {
        return (sum = item.fileName);
      } else {
        return (sum += ", " + item.fileName);
      }
    }, "");
  };

  render() {
    return (
      <div>
        <Navigation />
        <div className="container">
          <ContentWrapper content={<h3>Sign Documents</h3>} />
          <Table
            id={"signDocsTable"}
            tableData={this.state.tableData}
            searchBarId={"signDocsSearch"}
            requestNewData={this.fetchDocumentsToBeSigned}
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
