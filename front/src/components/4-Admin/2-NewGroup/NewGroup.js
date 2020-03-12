import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import GroupInformation from "./FormComponents/1-GroupInformation";
import AddUsersToGroup from "./FormComponents/2-AddUsersToGroup";
import AddDocTypes from "./FormComponents/3-AddDocTypes";
import axios from "axios";
import serverUrl from "./../../7-properties/1-URL";

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

  componentDidUpdate() {
    if (!this.props.showNewGroup) {
      if (
        this.state.groupName.length > 0 ||
        this.state.groupDescription.length > 0 ||
        this.state.usersList.length > 0 ||
        this.state.canCreate > 0 ||
        this.state.canSign.length > 0
      ) {
        this.setState({
          groupName: "",
          groupDescription: "",
          usersList: [],
          readyToSubmit: true,
          canCreate: [],
          canSign: []
        });
      }
    }
  }

  handleNewGroupSubmit = event => {
    event.preventDefault();
    const newGroup = {
      description: this.state.groupDescription,
      docTypesToCreate: this.state.canCreate,
      docTypesToSign: this.state.canSign,
      groupName: this.state.groupName,
      userList: this.state.usersList
    };

    axios
      .post(serverUrl + "creategroup", newGroup)
      .then(response => {
        window.location.reload();
        this.props.hideNewGroup();
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

  render() {
    return (
      <Modal
        show={this.props.showNewGroup}
        onHide={this.props.hideNewGroup}
        size="lg"
      >
        <Modal.Header closeButton>
          <Modal.Title>New Group</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={this.handleNewGroupSubmit}>
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
            />
            <hr className="m-1" />

            <AddDocTypes
              readyToSubmit={this.readyToSubmit}
              setCanCreate={this.setCanCreate}
              setCanSign={this.setCanSign}
              setAddedDocTypes={this.setAddedDocTypes}
            />

            <div className="form-group row d-flex justify-content-center">
              <div className="modal-footer ">
                <button
                  type="button"
                  className="btn btn-outline-dark"
                  onClick={this.props.hideNewGroup}
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="btn btn-dark"
                  data-dismiss="modal"
                  disabled={this.state.readyToSubmit ? false : true}
                >
                  Create
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
