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
    this.state = { username: "", tableData: [] };
  }
  dataFields = [
    "number",
    "name",
    "type",
    "status",
    "date",
    "files",
    "edit",
    "submit"
  ];
  columnNames = ["#", "Name", "Type", "Status", "Created", "Files", "", ""];
  tableData = [];

  componentDidMount() {
    this.getUsername();
  }

  getUsername = () => {
    axios
      .get(serverUrl + "loggedin")
      .then(response => {
        this.setState({ username: response.data });
        this.fetchData(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  fetchData = () => {
    axios
      .get(serverUrl + this.state.username + "/alldocuments")
      .then(response => {
        this.processData(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  processData = data => {
    const tableData = data.map((item, index) => {
      console.log(item);
      return {
        number: index + 1,
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
            <EditButton item={item} />
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
    return itemsList.reduce((sum, item, index) => {
      if (index === 0) {
        return (sum = item.fileName);
      } else {
        return (sum += ", " + item.fileName);
      }
    }, "");
  };

  render() {
    return (
      <div id="myDocsTable">
        <Table
          id={"myDocsTableSearch"}
          dataFields={this.dataFields}
          columnNames={this.columnNames}
          tableData={this.state.tableData}
          searchBarId={"createGroupUsersSearchBar"}
        />
      </div>
    );
  }
}

export default MyDocsTable;
