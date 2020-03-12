import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import DocInfo from "./FormComponents/1-DocInfo";
import AssignRights from "./FormComponents/2-AssignRights";
import axios from "axios";
import serverUrl from "../../7-properties/1-URL";

class NewGroup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      canCreate: [],
      canSign: []
    };
  }

  componentDidUpdate() {
    if (!this.props.showNewDocType) {
      if (
        this.state.name.length > 0 ||
        this.state.canCreate.length > 0 ||
        this.state.canSign > 0
      ) {
        this.setState({ name: "", canCreate: [], canSign: [] });
      }
    }
  }

  handleCreateNewDocType = event => {
    event.preventDefault();
    const newDocType = {
      name: this.state.name,
      creating: this.state.canCreate,
      approving: this.state.canSign
    };

    axios
      .post(serverUrl + "doct/create", newDocType)
      .then(response => {
        event.preventDefault();
        window.location.reload();
        this.props.hideNewDocType();
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

  render() {
    return (
      <Modal
        show={this.props.showNewDocType}
        onHide={this.props.hideNewDocType}
        size="lg"
      >
        <Modal.Header closeButton>
          <Modal.Title>New Document Type</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={this.handleCreateNewDocType}>
            <DocInfo
              handleNameChange={this.handleNameChange}
              name={this.state.name}
            />

            <hr className="m-1" />

            <AssignRights
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
                  onClick={this.props.hideNewDocType}
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="btn btn-dark"
                  data-dismiss="modal"
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
