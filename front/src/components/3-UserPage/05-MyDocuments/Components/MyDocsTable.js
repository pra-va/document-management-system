import React, { Component } from "react";
import Table from "../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import Doc from "./../../../../resources/doc.svg";
import PopOver from "./../../../6-CommonElements/8-PopOver/PopOver";
import "./MyDocsTable.css";
import EditButton from "./EditDocButton";
import ViewButton from "./ViewButton";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";

class MyDocsTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      tableData: [],
      pagingData: {},
      requestPagingData: {},
      dataUrl: "",
      initialDataTransferHappend: false
    };
  }

  columns = [
    { dataField: "id", text: "ID", sort: true },
    { dataField: "name", text: "Name", sort: true },
    { dataField: "type", text: "Type", sort: true },
    { dataField: "status", text: "Status", sort: true },
    { dataField: "date", text: "Created", sort: true },
    { dataField: "files", text: "Files", sort: false },
    { dataField: "edit", text: "", sort: false },
    { dataField: "submit", text: "", sort: false }
  ];

  sortValues = {
    id: "d.id",
    name: "d.name",
    type: "dt.name",
    status: "d.status",
    date: "d.dateCreate"
  };

  componentDidUpdate() {
    if (this.props.username !== this.state.username) {
      this.setState({
        username: this.props.username,
        dataUrl: this.props.username + "/alldocuments"
      });
    }
    if (
      !this.state.initialDataTransferHappend &&
      this.state.dataUrl.length > 0
    ) {
      this.fetchData(0, 8, null, null, "");
      this.setState({ initialDataTransferHappend: true });
    }
  }

  fetchData = (page, sizePerPage, sortField, order, searchValueString) => {
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
      .post(serverUrl + this.state.dataUrl, pageData)
      .then(response => {
        this.setState({
          pagingData: response.data.pagingData,
          requestPagingData: pageData
        });
        this.processData(response.data.documents);
      })
      .catch(error => {
        console.log(error);
      });
  };

  processData = data => {
    const tableData = data.map((item, index) => {
      return {
        number: index,
        id: item.uid,
        name: item.name,
        type: item.type,
        status: item.status,
        date: item.dateCreate.substring(0, 10),
        files: (
          <PopOver
            popOverApparance={
              <img className="myDocsImg invert" src={Doc} alt={"..."} />
            }
            popOverTitle={"Attached:"}
            popOverContent={
              item.filesAttached.length > 0
                ? this.reduceItemsList(item.filesAttached)
                : "None"
            }
          />
        ),
        edit:
          item.status === "CREATED" ? (
            <EditButton item={item} reloadTable={this.reloadTable} />
          ) : (
            <ViewButton item={item} />
          ),
        uid: item.uid,
        submit: (
          <button
            className="btn btn-secondary btn-sm"
            onClick={event => {
              this.submitDocument(item.uid, event);
            }}
            disabled={this.checkDisabled(item)}
            id={item.uid}
          >
            Submit
          </button>
        )
      };
    });

    this.setState({ tableData: tableData });
  };

  reloadTable = () => {
    const { requestPagingData } = this.state;
    setTimeout(() => {
      this.setState({ tableData: [] });
    }, 1);
    this.fetchData(
      requestPagingData.page,
      requestPagingData.limit,
      requestPagingData.sortBy,
      requestPagingData.order,
      requestPagingData.searchValueString
    );
  };

  submitDocument = (uid, event) => {
    event.preventDefault();
    axios
      .post(serverUrl + "doc/submit/" + uid, {})
      .then(respones => {
        window.location.reload();
      })
      .catch(error => {
        console.log(error);
      });
  };

  checkDisabled = item => {
    if (
      (item.name.length > 0) &
      (item.type.length > 0) &
      (item.description.length > 0) &
      (item.status === "CREATED") &
      (item.filesAttached.length > 0)
    ) {
      return false;
    } else {
      return true;
    }
  };

  reduceItemsList = itemsList => {
    let sum = "";
    for (let i = 0; i < itemsList.length; i++) {
      const element = itemsList[i].fileName;
      if (i === 0) {
        sum = element;
      } else if (i < 5) {
        sum += ", " + element;
      } else {
        sum += " and " + Number(itemsList.length - 5) + " more...";
        break;
      }
    }
    return sum;
  };

  fetchCustomData = url => {
    this.setState({ dataUrl: url, initialDataTransferHappend: false });
  };

  render() {
    return (
      <div id="myDocsTable">
        <div className="row d-flex justify-content-center ">
          <div className="btn-group btn-group-toggle" data-toggle="buttons">
            <label className="btn btn-secondary active">
              <input
                type="radio"
                name="options"
                id="all"
                checked
                onChange={() => {}}
                onClick={() => {
                  this.fetchCustomData(this.props.username + "/alldocuments");
                }}
              />{" "}
              All
            </label>
            <label className="btn btn-secondary">
              <input
                type="radio"
                name="options"
                id="created"
                onClick={() => {
                  this.fetchCustomData("doc/allcreated/" + this.props.username);
                }}
              />{" "}
              Created
            </label>
            <label className="btn btn-secondary">
              <input
                type="radio"
                name="options"
                id="submitted"
                onClick={() => {
                  this.fetchCustomData(
                    "doc/allsubmitted/" + this.state.username
                  );
                }}
              />{" "}
              Submitted
            </label>
            <label className="btn btn-secondary">
              <input
                type="radio"
                name="options"
                id="rejected"
                onClick={() => {
                  this.fetchCustomData(
                    "doc/allrejected/" + this.state.username
                  );
                }}
              />{" "}
              Declined
            </label>
            <label className="btn btn-secondary">
              <input
                type="radio"
                name="options"
                id="accepted"
                onClick={() => {
                  this.fetchCustomData(
                    "doc/allaccepted/" + this.state.username
                  );
                }}
              />{" "}
              Accepted
            </label>
          </div>
        </div>

        <Table
          id={"myDocsTableSearch"}
          tableData={this.state.tableData}
          searchBarId={"myDocsSearch"}
          requestNewData={this.fetchData}
          pagingData={this.state.pagingData}
          columns={this.columns}
          selectType={"radio"}
          handleRowSelect={() => {}}
          setSelectedItems={() => {}}
        />
      </div>
    );
  }
}

export default MyDocsTable;
