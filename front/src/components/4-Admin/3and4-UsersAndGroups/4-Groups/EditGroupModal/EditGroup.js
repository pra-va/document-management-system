import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";

class EditGroup extends Component {
  render() {
    return (
      <Modal
        show={this.props.show}
        onHide={this.props.onHide}
        size={"lg"}
        id="editUserModal"
      >
        <Modal.Header closeButton>
          <Modal.Title>Modal heading</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <h1>Edit group modal.</h1>
        </Modal.Body>
      </Modal>
    );
  }
}

export default EditGroup;
