import React, { Component } from "react";
import Navigation from "./../01-MainWindow/01-Navigation/Navigation";
import EditInfo from "./Components/1-EditInfo";
import SelectDocType from "./Components/2-SelectDocType";
import AttachFiles from "./Components/3-AttachFiles";
import AttachedFiles from "./Components/4-AttachedFiles";
import serverUrl from "./../../7-properties/1-URL";
import "./CreateDocument.css";
import axios from "axios";

class CreateDocument extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      name: "",
      description: "",
      selectedDocType: "",
      files: [],
      loaded: 0,
      attachedFilesTableValues: []
    };
  }

  componentDidMount() {
    this.fetchUsername();
  }

  handleNameChange = nameData => {
    this.setState({ name: nameData });
  };

  handleDescriptionChange = descriptionData => {
    this.setState({ description: descriptionData });
  };

  handleDocTypeSelect = selectedDocTypeName => {
    this.setState({ selectedDocType: selectedDocTypeName });
  };

  handleRemove = event => {
    event.preventDefault();
    const tmpValues = [...this.state.attachedFilesTableValues];
    for (let i = 0; i < tmpValues.length; i++) {
      const element = tmpValues[i];
      if (Number(event.target.id) === Number(element.number)) {
        tmpValues.splice(i, 1);
        break;
      }
    }
    this.setState({ attachedFilesTableValues: tmpValues });
  };

  handleFileAdd = files => {
    let tmpFilesForTable = [...this.state.attachedFilesTableValues];
    let stateLength = this.state.attachedFilesTableValues.length;

    for (let i = 0; i < files.length; i++) {
      const element = files[i];
      tmpFilesForTable.push({
        number: i + stateLength,
        fileName: element.name,
        remove: (
          <button
            className="btn btn-secondary btn-sm"
            onClick={this.handleRemove}
            id={i + stateLength}
          >
            Remove
          </button>
        ),
        file: files[i]
      });
    }
    this.setState({ attachedFilesTableValues: tmpFilesForTable });
  };

  fetchUsername = () => {
    axios
      .get(serverUrl + "loggedin")
      .then(response => {
        this.setState({ username: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  handleUpload = event => {
    event.preventDefault();
    const data = new FormData();
    var attachedFiles = this.state.attachedFilesTableValues;
    if (attachedFiles.length !== 0) {
      for (let i = 0; i < attachedFiles.length; i++) {
        const element = attachedFiles[i].file;
        data.append("files", element);
      }
    } else {
      attachedFiles = null;
    }

    const postData = {
      authorUsername: this.state.username,
      description: this.state.description,
      docType: this.state.selectedDocType,
      name: this.state.name
    };

    console.log(serverUrl + "doc/create");
    axios
      .post(serverUrl + "doc/create", postData)
      .then(response => {
        console.log(serverUrl + "doc/upload/" + this.state.name);
        axios
          .post(serverUrl + "doc/upload/" + this.state.name, data, {
            headers: { "Content-Type": "multipart/form-data" }
          })
          .then(response => {
            console.log(response);
          })
          .catch(function(error) {
            console.log(error);
          });
      })
      .catch(function(error) {
        console.log(error);
      });
  };

  render() {
    return (
      <div>
        <Navigation />{" "}
        <div className="container" id="newDocument">
          <form onSubmit={this.handleUpload} id="createDocumentForm">
            <h2 className="mb-3">New Document</h2>
            <EditInfo
              handleNameChange={this.handleNameChange}
              handleDescriptionChange={this.handleDescriptionChange}
              name={this.state.name}
              description={this.state.description}
            />
            <hr />
            <SelectDocType
              handleDocTypeSelect={this.handleDocTypeSelect}
              username={this.state.username}
            />
            <hr />
            <AttachFiles handleFileAdd={this.handleFileAdd} />
            <AttachedFiles values={this.state.attachedFilesTableValues} />
            <div className="form-group row d-flex justify-content-center m-0">
              <div className="modal-footer ">
                <button
                  type="button"
                  className="btn btn-outline-dark"
                  onClick={this.props.hideNewGroup}
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="btn btn-dark"
                  data-dismiss="modal"
                  disabled={false}
                >
                  Create
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    );
  }
}

export default CreateDocument;
