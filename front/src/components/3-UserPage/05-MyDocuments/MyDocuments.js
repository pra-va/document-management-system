import React, { Component } from "react";
import Navigation from "./../01-MainWindow/01-Navigation/Navigation";
import DataTable from "./Components/MyDocsTable";
import "./MyDocuments.css";
import { Link } from "react-router-dom";
import axios from "axios";
import serverUrl from "./../../7-properties/1-URL";
import ContentWrapper from "./../../6-CommonElements/10-TopContentWrapper/ContentWrapper";

class MyDocs extends Component {
  constructor(props) {
    super(props);
    this.state = { data: [], username: "" };
  }

  componentDidMount() {
    this.getUsername();
  }

  getUsername = () => {
    axios
      .get(serverUrl + "loggedin")
      .then(response => {
        this.setState({ username: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  handleGetZip = () => {
    axios
      .request({
        url: serverUrl + "files/zip/" + this.state.username,
        method: "GET",
        responseType: "blob"
      })
      .then(({ data }) => {
        const downloadUrl = window.URL.createObjectURL(new Blob([data]));
        const link = document.createElement("a");
        link.href = downloadUrl;
        link.setAttribute("download", "file.zip");
        document.body.appendChild(link);
        link.click();
        link.remove();
      });
  };

  handleGetCsv = () => {
    axios
      .request({
        url: serverUrl + "files/csv/" + this.state.username,
        method: "GET",
        responseType: "blob"
      })
      .then(({ data }) => {
        const downloadUrl = window.URL.createObjectURL(new Blob([data]));
        const link = document.createElement("a");
        link.href = downloadUrl;
        link.setAttribute("download", "documents.csv");
        document.body.appendChild(link);
        link.click();
        link.remove();
      });
  };

  render() {
    return (
      <div>
        <Navigation />
        <div className="container">
          <ContentWrapper content={<h3>My Documents</h3>} />
          <DataTable username={this.state.username} />
          <div className="row d-flex justify-content-center p-5">
            <button
              className="btn btn-secondary btn-lg btn-myDocs m-3"
              id="downloadArchive"
              onClick={this.handleGetZip}
            >
              Download Archive
            </button>
            <button
              className="btn btn-secondary btn-lg btn-myDocs m-3"
              id="downloadCsv"
              onClick={this.handleGetCsv}
            >
              Download .csv
            </button>
            <Link
              to={"/dvs/document"}
              className="btn btn-secondary btn-lg btn-myDocs m-3"
              id="createDocument"
            >
              New Document
            </Link>
          </div>
        </div>
      </div>
    );
  }
}

export default MyDocs;
