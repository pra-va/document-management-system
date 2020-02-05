import React, { Component } from "react";
import EditUser from "./3-Users/EditUserModal/EditUser";
import EditGroup from "./4-Groups/EditGroupModal/EditGroup";

// ownerType={user/group} ownerName={"..."}
class EditTableItemButton extends Component {
  constructor(props) {
    super(props);
    this.state = { showModal: false };
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
          Edit
        </button>

        {this.props.ownerType === "user" ? (
          <EditUser
            ownerName={this.props.ownerName}
            show={this.state.showModal}
            onHide={this.handleClose}
          />
        ) : (
          <EditGroup
            ownerName={this.props.ownerName}
            show={this.state.showModal}
            onHide={this.handleClose}
          />
        )}
      </div>
    );
  }
}

export default EditTableItemButton;
