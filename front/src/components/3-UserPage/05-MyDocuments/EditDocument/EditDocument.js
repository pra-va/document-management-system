import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";
import EditInfo from "./Components/1-EditInfo";
import SelectDocType from "./Components/2-SelectDocType";
import AttachFiles from "./Components/3-AttachFiles";
import AttachedFiles from "./Components/4-AttachedFiles";
import "./EditDocument.css";

class EditDocument extends Component {
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
      submitInProgres: false,
      filesAttachedInServer: []
    };
  }

  componentDidUpdate() {
    const { name, description, selectedDocType, filesSize } = this.state;
    console.log(this.state.attachedFilesTableValues);

    if (
      (name.length > 0) &
      (description.length > 0) &
      (selectedDocType.length > 0) &
      (filesSize < 20000000) &
      (filesSize !== 0)
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
    console.log(this.props.item);
    const data = this.props.item;
    this.fetchUsername();
    this.setState({
      filesAttachedInServer: this.props.item.filesAttached,
      name: data.name,
      description: data.description,
      selectedDocType: data.type,
      files: [...this.props.item.filesAttached],
      attachedFilesTableValues: data.filesAttached.map((item, index) => {
        return {
          number: index + 1,
          fileName: item.fileName,
          size: this.processFileSizeString(item.fileSize),
          fileSize: item.fileSize,
          remove: (
            <button
              className="btn btn-secondary btn-sm"
              onClick={this.handleRemove}
              id={index + 1}
            >
              Remove
            </button>
          )
        };
      })
    });
    this.checkAttachedFilesSize(
      this.props.item.filesAttached.map(item => item.fileSize)
    );
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
      console.log(
        "iterator: " + event.target.id + "; element: " + element.number
      );
      if (Number(event.target.id) === Number(element.number)) {
        tmpValues.splice(i, 1);
        break;
      }
    }
    this.setState({ attachedFilesTableValues: tmpValues });

    console.log(tmpValues);

    this.checkAttachedFilesSize(tmpValues.map(item => item.fileSize));
  };

  checkAttachedFilesSize = (...files) => {
    let sum = 0;

    files.forEach(element => {
      for (let i = 0; i < element.length; i++) {
        const item = element[i];
        sum += item;
      }
    });
    this.setState({ filesSize: sum });
    console.log(sum);
    return sum;
  };

  handleFileAdd = files => {
    let tmpFilesForTable = [...this.state.attachedFilesTableValues];
    let stateLength = tmpFilesForTable.length;

    for (let i = 0; i < files.length; i++) {
      const element = files[i];
      var size = this.processFileSizeString(element.size);

      console.log(stateLength);

      tmpFilesForTable.push({
        number: i + stateLength + 1,
        fileName: element.name,
        size: size,
        fileSize: element.size,
        remove: (
          <button
            className="btn btn-secondary btn-sm"
            onClick={this.handleRemove}
            id={i + stateLength + 1}
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
    this.checkAttachedFilesSize(tmpFilesForTable.map(item => item.fileSize));
  };

  processFileSizeString = sizeInB => {
    if (sizeInB < 1000) {
      return sizeInB + " B";
    } else if (sizeInB >= 1000 && sizeInB < 1000000) {
      return Math.floor((sizeInB / 1000) * 100) / 100 + " kB";
    } else {
      return Math.floor((sizeInB / 1000000) * 100) / 100 + " MB";
    }
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
      <Modal show={this.props.show} onHide={this.props.hide} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>Edit Document</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div className="container">
            <form onSubmit={this.handleUpload} id="editDocumentForm">
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
                  onClick={this.props.hide}
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
        </Modal.Body>
      </Modal>
    );
  }
}

export default EditDocument;
