import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import DocInfo from "./FormComponents/1-DocInfo";
import AssignRights from "./FormComponents/2-AssignRights";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";

class NewGroup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      canCreate: [],
      canSign: []
    };
  }

  handleSubmit = event => {
    event.preventDefault();

    const editedDocType = {
      newName: this.state.name,
      groupsCreating: this.state.canCreate,
      groupsApproving: this.state.canSign
    };

    axios
      .post(serverUrl + "doct/update/" + this.props.owner, editedDocType)
      .then(response => {
        window.location.reload();
      })
      .catch(error => {
        console.log(error);
      });
  };

  handleNameChange = name => {
    this.setState({ name: name });
  };

  setCanCreate = canCreate => {
    this.setState({ canCreate: canCreate });
  };

  setCanSign = canSign => {
    this.setState({ canSign: canSign });
  };

  setUpData = data => {
    this.setState({
      canCreate: data.groupsToCreate,
      canSign: data.groupsToApprove,
      name: data.name
    });
  };

  render() {
    return (
      <Modal
        show={this.props.modalState}
        onHide={this.props.hideModal}
        size="lg"
      >
        <Modal.Header closeButton>
          <Modal.Title>Edit {this.props.owner} Document Type</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={this.handleSubmit}>
            <DocInfo
              handleNameChange={this.handleNameChange}
              name={this.state.name}
              owner={this.props.owner}
            />

            <hr className="m-1" />

            <AssignRights
              readyToSubmit={this.readyToSubmit}
              setCanCreate={this.setCanCreate}
              setCanSign={this.setCanSign}
              setAddedDocTypes={this.setAddedDocTypes}
              owner={this.props.owner}
              setUpData={this.setUpData}
            />

            <div className="form-group row d-flex justify-content-center">
              <div className="modal-footer ">
                <button
                  type="button"
                  className="btn btn-outline-dark"
                  onClick={this.props.hideModal}
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="btn btn-dark"
                  data-dismiss="modal"
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
