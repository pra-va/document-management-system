import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import GroupInformation from "./FormComponents/1-GroupInformation";
import AddUsersToGroup from "./FormComponents/2-AddUsersToGroup";
import AddDocTypes from "./FormComponents/3-AddDocTypes";
import axios from "axios";
import serverUrl from "./../../../../7-properties/1-URL";

class NewGroup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      groupName: "",
      groupDescription: "",
      usersList: [],
      readyToSubmit: true,
      canCreate: [],
      canSign: []
    };
  }

  handleSubmit = event => {
    event.preventDefault();

    const editedGroup = {
      description: this.state.groupDescription,
      docTypesToCreate: this.state.canCreate,
      docTypesToApprove: this.state.canSign,
      newName: this.state.groupName,
      userList: this.state.usersList
    };

    axios
      .post(serverUrl + "groups/update/" + this.props.ownerName, editedGroup)
      .then(response => {
        window.location.reload();
      })
      .catch(error => console.log(error));
  };

  handleGroupNameChange = event => {
    this.setState({ groupName: event.target.value });
  };

  handleGroupDescriptionChange = event => {
    this.setState({ groupDescription: event.target.value });
  };

  setAddedUsers = users => {
    this.setState({ usersList: users });
  };

  setCanCreate = canCreate => {
    this.setState({ canCreate: canCreate });
  };

  setCanSign = canSign => {
    this.setState({ canSign: canSign });
  };

  setUpGroupData = data => {
    this.setState({ ...data });
  };

  render() {
    return (
      <Modal show={this.props.show} onHide={this.props.onHide} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>Edit Group {this.props.ownerName}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={this.handleSubmit}>
            <GroupInformation
              handleGroupNameChange={this.handleGroupNameChange}
              groupName={this.state.groupName}
              handleGroupDescriptionChange={this.handleGroupDescriptionChange}
              groupDescription={this.state.groupDescription}
            />
            <hr className="m-1" />
            <AddUsersToGroup
              setUpData={this.setUpData}
              notAddedUsers={this.state.notAddedUsers}
              setAddedUsers={this.setAddedUsers}
              ownerName={this.props.ownerName}
              setUpGroupData={this.setUpGroupData}
            />
            <hr className="m-1" />

            <AddDocTypes
              readyToSubmit={this.readyToSubmit}
              setCanCreate={this.setCanCreate}
              setCanSign={this.setCanSign}
              setAddedDocTypes={this.setAddedDocTypes}
              canCreate={this.state.canCreate}
              canSign={this.state.canSign}
            />

            <div className="form-group row d-flex justify-content-center">
              <div className="modal-footer ">
                <button
                  type="button"
                  className="btn btn-outline-dark"
                  onClick={this.props.onHide}
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="btn btn-dark"
                  data-dismiss="modal"
                  disabled={this.state.readyToSubmit ? false : true}
                >
                  Update
                </button>
              </div>
            </div>
          </form>
        </Modal.Body>
      </Modal>
    );
  }
}

export default NewGroup;
