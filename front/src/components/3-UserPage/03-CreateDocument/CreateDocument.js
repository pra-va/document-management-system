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
      attachedFilesTableValues: [],
      uploadProgress: 0,
      filesSize: 0,
      submitDisabled: true,
      submitInProgres: false
    };
  }

  componentDidUpdate() {
    const { name, description, selectedDocType, filesSize } = this.state;

    if (
      (name.length > 0) &
      (description.length > 0) &
      (selectedDocType.length > 0) &
      (filesSize < 20000000)
    ) {
      if (this.state.submitDisabled) {
        this.setState({ submitDisabled: false });
      }
    } else {
      if (!this.state.submitDisabled) {
        this.setState({ submitDisabled: true });
      }
    }
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
    this.checkAttachedFilesSize(tmpValues);
  };

  checkAttachedFilesSize = files => {
    let sum = 0;
    for (let i = 0; i < files.length; i++) {
      const element = files[i].file.size;
      sum += element;
    }
    this.setState({ filesSize: sum });
  };

  handleFileAdd = files => {
    let tmpFilesForTable = [...this.state.attachedFilesTableValues];
    let stateLength = this.state.attachedFilesTableValues.length;

    for (let i = 0; i < files.length; i++) {
      const element = files[i];
      var size = "";
      if (element.size < 1000) {
        size = element.size + " B";
      } else if (element.size >= 1000 && element.size < 1000000) {
        size = Math.floor((element.size / 1000) * 100) / 100 + " kB";
      } else {
        size = Math.floor((element.size / 1000000) * 100) / 100 + " MB";
      }
      tmpFilesForTable.push({
        number: i + stateLength,
        fileName: element.name,
        size: size,
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
    this.setState({
      attachedFilesTableValues: tmpFilesForTable
    });
    this.checkAttachedFilesSize(tmpFilesForTable);
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

  config = {
    onUploadProgress: progressEvent => {
      var percentCompleted = Math.round(
        (progressEvent.loaded * 100) / progressEvent.total
      );
      this.setState({ percentCompleted: percentCompleted + "%" });
    },
    headers: { "Content-Type": "multipart/form-data" }
  };

  handleUpload = event => {
    event.preventDefault();
    this.setState({ submitInProgres: true });
    const data = new FormData();
    let uid = "";
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

    axios
      .post(serverUrl + "doc/create", postData)
      .then(response => {
        uid = response.data;
        axios
          .post(serverUrl + "doc/upload/" + uid, data)
          .then(response => {
            this.props.history.push("/dvs/documents");
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
            <AttachedFiles
              values={this.state.attachedFilesTableValues}
              size={this.state.filesSize}
            />
            <div className="progress my-3">
              <div
                className="progress-bar progress-bar-striped progress-bar-animated bg-dark"
                role="progressbar"
                aria-valuenow="100"
                aria-valuemin="0"
                aria-valuemax="100"
                style={
                  this.state.submitInProgres
                    ? { width: this.state.percentCompleted }
                    : { width: (this.state.filesSize * 100) / 20000000 + "%" }
                }
              ></div>
            </div>
            <div className="form-group row d-flex justify-content-center m-0">
              <button
                type="button"
                className="btn btn-outline-dark mr-2"
                onClick={this.props.hideNewGroup}
              >
                Cancel
              </button>
              <button
                type="submit"
                className="btn btn-dark ml-2"
                data-dismiss="modal"
                disabled={this.state.submitDisabled}
              >
                Create
              </button>
            </div>
          </form>
        </div>
      </div>
    );
  }
}

export default CreateDocument;
