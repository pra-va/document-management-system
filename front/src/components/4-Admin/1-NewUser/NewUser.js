import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import "./NewUser.css";
import UserInformation from "./FormComponents/1-UserInformation";
import Groups from "./FormComponents/2-Groups";
import axios from "axios";
import serverUrl from "./../../7-properties/1-URL";

class NewModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      firstName: "",
      lastName: "",
      username: "",
      password: "",
      role: "USER",
      allGroups: [],
      addedGroups: [],
      usernameExists: false
    };
  }

  componentDidMount() {
    if (this.props.mode === "edit") {
      this.setState({ username: this.props.ownerName });
      this.fetchEditUserData();
    }
  }

  componentDidUpdate() {
    if (!this.props.show) {
      if (this.state.addedGroups.length > 0) {
        this.setState({
          firstName: "",
          lastName: "",
          username: "",
          password: "",
          role: "USER",
          allGroups: [],
          addedGroups: [],
          usernameExists: false
        });
      }
    }
  }

  setUpGroups = data => {
    if (data.length >= 0) {
      this.parseData(data);
    }
  };

  handleFirstNameChange = value => {
    this.setState({ firstName: value });
  };

  handleLastNameChange = value => {
    this.setState({ lastName: value });
  };

  handleUsernameChange = value => {
    this.setState({ username: value });
  };

  handlePasswordChange = value => {
    this.setState({ password: value });
  };

  handleRoleChange = value => {
    this.setState({ role: value });
  };

  setAddedGroups = groupList => {
    this.setState({ addedGroups: groupList });
  };

  handleNewUserSubmit = event => {
    event.preventDefault();
    const { addedGroups, firstName, lastName, password, username } = this.state;
    let url = serverUrl;
    if (this.state.role === "ADMIN") {
      url += "createadmin/";
    } else {
      url += "createuser/";
    }

    axios
      .post(url, {
        groupList: addedGroups,
        name: firstName,
        password: password,
        surname: lastName,
        username: username
      })
      .then(response => {
        this.props.onHide();
        window.location.reload();
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <Modal
        show={this.props.show}
        onHide={this.props.onHide}
        size={"lg"}
        id="newUserModal"
      >
        <Modal.Header closeButton>
          <Modal.Title>New User</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={this.handleNewUserSubmit}>
            <UserInformation
              handleFirstNameChange={this.handleFirstNameChange}
              handleLastNameChange={this.handleLastNameChange}
              handleUsernameChange={this.handleUsernameChange}
              handlePasswordChange={this.handlePasswordChange}
              handleRoleChange={this.handleRoleChange}
              firstName={this.state.firstName}
              lastName={this.state.lastName}
              username={this.state.username}
              password={this.state.password}
              role={this.state.role}
            />
            <hr className="m-1" />

            <Groups setAddedGroups={this.setAddedGroups} />

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
                  type={this.state.usernameExists ? "" : "submit"}
                  className="btn btn-dark"
                  data-dismiss="modal"
                  disabled={this.state.usernameExists ? true : false}
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

export default NewModal;
