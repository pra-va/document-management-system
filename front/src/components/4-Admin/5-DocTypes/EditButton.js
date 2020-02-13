import React, { Component } from "react";
import EditDocType from "./EditDocType/EditDocType";

// ownerName={"..."}
class EditTableItemButton extends Component {
  constructor(props) {
    super(props);
    this.state = { showModal: false };
  }

  componentDidMount() {
    console.log(this.props);
  }

  handleClose = () => {
    this.setState({ showModal: false });
  };

  handleShow = () => {
    this.setState({ showModal: true });
  };

  render() {
    return (
      <div>
        <button className="btn btn-secondary btn-sm" onClick={this.handleShow}>
          Edit / View
        </button>

        <EditDocType
          modalState={this.state.showModal}
          hideModal={this.handleClose}
        />
      </div>
    );
  }
}

export default EditTableItemButton;
