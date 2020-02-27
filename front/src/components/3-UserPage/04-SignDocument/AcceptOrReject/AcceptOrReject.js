import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";

class AcceptOrReject extends Component {
  constructor(props) {
    super(props);
    this.state = { data: [] };
  }

  render() {
    return (
      <div>
        {" "}
        <Modal
          size="lg"
          show={this.props.show}
          onHide={this.props.hide}
          aria-labelledby="example-modal-sizes-title-lg"
        >
          <Modal.Header closeButton>
            <Modal.Title id="example-modal-sizes-title-lg">
              Large Modal
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>...</Modal.Body>
        </Modal>
      </div>
    );
  }
}

export default AcceptOrReject;
