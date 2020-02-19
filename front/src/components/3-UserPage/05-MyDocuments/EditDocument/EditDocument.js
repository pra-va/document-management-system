import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";

class EditDocument extends Component {
  render() {
    return (
      <Modal show={this.props.show} onHide={this.props.hide} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>Modal heading</Modal.Title>
        </Modal.Header>
        <Modal.Body>Woohoo, you're reading this text in a modal!</Modal.Body>
      </Modal>
    );
  }
}

export default EditDocument;
