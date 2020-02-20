import React, { Component } from "react";
import Table from "./../../2-AdvancedTable/AdvancedTable";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";

class AddDocumentTypes extends Component {
  constructor(props) {
    super(props);
    this.state = { notAddedDocTypes: [] };
  }

  dataFields = ["number", "name", "canCreate", "canSign", "edit"];
  columnNames = ["#", "Name", "Creating Groups", "Signing Groups", ""];

  fetchServerData = () => {
    axios
      .get(serverUrl + "doct/all")
      .then(response => {
        console.log(response.data);
        this.props.fetchServerData(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.props.doCleanUp();
    this.fetchServerData();
  }

  componentDidUpdate() {
    if (
      this.state.notAddedDocTypes.length !== this.props.notAddedDocTypes.length
    ) {
      this.setState({ notAddedDocTypes: this.props.notAddedDocTypes });
    }
  }

  render() {
    return (
      <div>
        <h3 className="d-flex justify-content-start">
          4. Add document types for group.
        </h3>
        <Table
          id={"docTypes"}
          dataFields={this.dataFields}
          columnNames={this.columnNames}
          tableData={this.state.notAddedDocTypes}
          searchBarId={"docTypeSearchBar"}
        />
      </div>
    );
  }
}

export default AddDocumentTypes;
