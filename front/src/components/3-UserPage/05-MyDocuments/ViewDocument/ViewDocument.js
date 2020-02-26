import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";

class ViewDocument extends Component {
  render() {
    return (
      <Modal show={this.props.show} onHide={this.props.hide} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>View Document</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div className="row">
            <div className="col-3">Name:</div>
            <div className="col-9">{this.props.item.name}</div>
          </div>
          <div className="row">
            <div className="col-3">Type:</div>
            <div className="col-9">{this.props.item.type}</div>
          </div>
          <div className="row">
            <div className="col-3">Description:</div>
            <div className="col-9">{this.props.item.description}</div>
          </div>
          <div className="row">
            <div className="col-3">Status:</div>
            <div className="col-9">{this.props.item.status}</div>
          </div>
          <div className="row">
            <div className="col-3">Created:</div>
            <div className="col-9">
              {this.props.item.dateCreate
                ? this.props.item.dateCreate.substring(0, 10)
                : "NOT CREATED"}
            </div>
          </div>
          <div className="row">
            <div className="col-3">Submited:</div>
            <div className="col-9">
              {this.props.item.dateSubmit
                ? this.props.item.dateSubmit.substring(0, 10)
                : "NOT SIGNED"}
            </div>
          </div>
          <div className="row">
            <div className="col-3">Signed:</div>
            <div className="col-9">{this.props.item.dateProcessed}</div>
          </div>
        </Modal.Body>
      </Modal>
    );
  }
}

export default ViewDocument;
