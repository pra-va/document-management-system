import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";
import "./ViewDocument.css";

class ViewDocument extends Component {
  downloadFile = (uid, fileName) => {
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

  generateAttachedFilesList = () => {
    return this.props.item.filesAttached.map((item, index) => {
      return (
        <div
          key={index}
          className="row p-1 d-flex justify-content-center info-div"
        >
          <button
            className="btn btn-outline-dark"
            onClick={() => this.downloadFile(item.uid, item.fileName)}
          >
            {item.fileName}
          </button>
        </div>
      );
    });
  };

  render() {
    return (
      <Modal show={this.props.show} onHide={this.props.hide}>
        <Modal.Header closeButton>
          <Modal.Title>View Document</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div className="row p-3">
            <div className="col-3">Name:</div>
            <div className="col-9">{this.props.item.name}</div>
          </div>
          <div className="row p-3">
            <div className="col-3">Type:</div>
            <div className="col-9">{this.props.item.type}</div>
          </div>
          <div className="row p-3">
            <div className="col-3">Description:</div>
            <div className="col-9">{this.props.item.description}</div>
          </div>
          <div className="row p-3">
            <div className="col-3">Status:</div>
            <div className="col-9">{this.props.item.status}</div>
          </div>
          <div className="row p-3">
            <div className="col-3">Created:</div>
            <div className="col-9">
              {this.props.item.dateCreate
                ? this.props.item.dateCreate.substring(0, 10)
                : "NOT CREATED"}
            </div>
          </div>
          <div className="row p-3">
            <div className="col-3">Submited:</div>
            <div className="col-9">
              {this.props.item.dateSubmit
                ? this.props.item.dateSubmit.substring(0, 10)
                : "NOT SUBMITED"}
            </div>
          </div>
          <div className="row p-3">
            <div className="col-3">Considered:</div>
            <div className="col-9">
              {this.props.item.dateProcessed
                ? this.props.item.dateProcessed.substring(0, 10)
                : "NOT PROCESSED"}
            </div>
          </div>
          <div className="row p-3">
            <div className="col-3">Decline reason:</div>
            <div className="col-9">{this.props.item.reasonToReject}</div>
          </div>
          <div className="row p-3">
            <div className="col-3">Validator:</div>
            <div className="col-9">TODO VALIDATOR NAME</div>
          </div>
          <div className="row p-3">
            <div className="col-3">Attached files:</div>
            <div className="col-9">{this.generateAttachedFilesList()}</div>
          </div>
          <button className="btn btn-secondary" onClick={this.props.hide}>
            Close
          </button>
        </Modal.Body>
      </Modal>
    );
  }
}

export default ViewDocument;
