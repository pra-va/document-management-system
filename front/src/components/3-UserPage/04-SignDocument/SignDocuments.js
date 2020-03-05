import React, { Component } from "react";
import Navigation from "./../01-MainWindow/01-Navigation/Navigation";
import SignTable from "./Components/SignTable";
import axios from "axios";
import serverUrl from "./../../7-properties/1-URL";
import Files from "./../../../resources/doc.svg";
import PopOver from "../../6-CommonElements/8-PopOver/PopOver";
import SignOrRejectButton from "./SignOrRejectButton";
import ContentWrapper from "./../../6-CommonElements/10-TopContentWrapper/ContentWrapper";

export default class SignDocuments extends Component {
  constructor(props) {
    super(props);
    this.state = { serverData: [], username: "", tableData: [] };
  }

  componentDidMount() {
    this.getUsername();
  }

  componentDidUpdate() {}

  getUsername = () => {
    axios
      .get(serverUrl + "loggedin")
      .then(response => {
        this.setState({ username: response.data });
        this.fetchDocumentsToBeSigned(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  fetchDocumentsToBeSigned = username => {
    axios
      .get(serverUrl + username + "/doctobesigned")
      .then(response => {
        this.setState({ serverData: response.data });
        this.processData(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  processData = data => {
    console.log(data);
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
            popOverApparance={<img className="invert" src={Files} alt="..." />}
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
          <SignTable values={this.state.tableData} />
        </div>
      </div>
    );
  }
}
