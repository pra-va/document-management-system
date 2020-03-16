import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";
import EditInfo from "./Components/1-EditInfo";
import SelectDocType from "./Components/2-SelectDocType";
import AttachFiles from "./Components/3-AttachFiles";
import AttachedFiles from "./Components/4-AttachedFiles";
import "./EditDocument.css";
import { withRouter } from "react-router-dom";
import Download from "./../../../../resources/download.svg";

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
          number: item.uid,
          fileName: item.fileName,
          size: this.processFileSizeString(item.fileSize),
          fileSize: item.fileSize,
          download: (
            <img
              src={Download}
              alt={"download"}
              className="invert m-0 p-0 img-download"
              onClick={event => {
                this.downloadFile(event, item.uid, item.fileName);
              }}
            />
          )
        };
      })
    });
    this.checkAttachedFilesSize(
      this.props.item.filesAttached.map(item => item.fileSize)
    );
  }

  downloadFile = (event, uid, fileName) => {
    event.preventDefault();
    axios
      .request({
        url: serverUrl + "files/" + uid,
        method: "GET",
        responseType: "blob"
      })
      .then(({ data }) => {
        const downloadUrl = window.URL.createObjectURL(new Blob([data]));
        const link = document.createElement("a");
        link.href = downloadUrl;
        link.setAttribute("download", fileName);
        document.body.appendChild(link);
        link.click();
        link.remove();
      });
  };

  handleNameChange = nameData => {
    this.setState({ name: nameData });
  };

  handleDescriptionChange = descriptionData => {
    this.setState({ description: descriptionData });
  };

  handleDocTypeSelect = selectedDocTypeName => {
    this.setState({ selectedDocType: selectedDocTypeName });
  };

  handleRemove = number => {
    const tmpValues = [...this.state.attachedFilesTableValues];
    for (let i = 0; i < tmpValues.length; i++) {
      const element = tmpValues[i];
      if (Number(number) === Number(element.number)) {
        tmpValues.splice(i, 1);
        break;
      }
    }
    this.setState({ attachedFilesTableValues: tmpValues });

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
    return sum;
  };

  handleFileAdd = files => {
    let tmpFilesForTable = [...this.state.attachedFilesTableValues];
    let stateLength = tmpFilesForTable.length;

    for (let i = 0; i < files.length; i++) {
      const element = files[i];
      var size = this.processFileSizeString(element.size);

      tmpFilesForTable.push({
        number: i + stateLength + 1,
        fileName: element.name,
        size: size,
        fileSize: element.size,
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
    var filesInDb = [];
    var filesToRemove = [];
    let uid = this.props.item.uid;
    var attachedFiles = this.state.attachedFilesTableValues;

    for (let i = 0; i < attachedFiles.length; i++) {
      const element = attachedFiles[i];
      if (element.file !== undefined) {
        data.append("files", element.file);
      } else {
        filesInDb.push(element.number);
      }
    }

    for (let j = 0; j < this.state.filesAttachedInServer.length; j++) {
      const element = this.state.filesAttachedInServer[j].uid;
      if (filesInDb.includes(element)) {
        continue;
      } else {
        filesToRemove.push(element);
      }
    }

    const postData = {
      description: this.state.description,
      docType: this.state.selectedDocType,
      newName: this.state.name,
      filesToRemoveUID: filesToRemove
    };

    axios
      .post(serverUrl + "doc/update" + uid, postData)
      .then(response => {
        axios
          .post(serverUrl + "doc/upload/" + uid, data)
          .then(response => {
            this.props.history.push("/dvs/documents");
            this.props.hide();
            window.location.reload();
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
          <Modal.Title>Edit Document ID {this.props.item.uid}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={this.handleUpload} id="editDocumentForm">
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
              selected={this.state.selectedDocType}
            />
            <hr />
            <AttachFiles handleFileAdd={this.handleFileAdd} />
            <AttachedFiles
              values={this.state.attachedFilesTableValues}
              size={this.state.filesSize}
              attachedFilesTableValues={this.state.attachedFilesTableValues}
              handleRemove={this.handleRemove}
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
            <div
              className="form-group row d-flex justify-content-center m-0"
              id="updateDocumentFooter"
            >
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
                Submit
              </button>
            </div>
          </form>
        </Modal.Body>
      </Modal>
    );
  }
}

export default withRouter(EditDocument);
