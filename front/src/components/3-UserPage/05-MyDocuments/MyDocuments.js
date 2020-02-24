import React, { Component } from "react";
import Navigation from "./../01-MainWindow/01-Navigation/Navigation";
import DataTable from "./Components/MyDocsTable";
import "./MyDocuments.css";
import { Link } from "react-router-dom";

class MyDocs extends Component {
  constructor(props) {
    super(props);
    this.state = { data: [] };
  }

  render() {
    return (
      <div>
        <Navigation />
        <div className="container">
          <h3>My Documents</h3>
          <DataTable />
          <div className="row d-flex justify-content-center p-5">
            <button
              className="btn btn-secondary btn-lg btn-myDocs m-3"
              id="downloadArchive"
            >
              Download Archive
            </button>
            <button
              className="btn btn-secondary btn-lg btn-myDocs m-3"
              id="downloadCsv"
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
