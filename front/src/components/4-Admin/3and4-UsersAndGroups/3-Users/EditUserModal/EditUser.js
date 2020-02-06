import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import "./../../../1-NewUser/NewUser.css";
import UserInformation from "./1-EditUserInformation";
import Groups from "./2-Groups";
import UsersGroups from "./../../../1-NewUser/FormComponents/3-UsersGroups";
import axios from "axios";

class NewModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      owner: "",
      firstName: "",
      lastName: "",
      username: "",
      password: "",
      role: "",
      userGroups: [],
      allGroups: [],
      notAddedGroups: [],
      addedGroups: [],
      usernameExists: false
    };
  }

  componentDidMount() {}

  componentDidUpdate() {}

  setUserGroups = userGroups => {
    this.setState({ userGroups: userGroups });
  };

  setAddedUserGroups = addedGroups => {
    this.setState({ addedGroups: addedGroups });
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

  handleUsernameExists = value => {
    this.setState({ usernameExists: value });
  };

  initalDataTransfer = data => {
    this.setState({ ...data });
  };

  handleSubmit = event => {
    event.preventDefault();
    let url =
      "http://localhost:8080/dvs/api/user/update/" + this.props.ownerName;

    let userGroups = this.state.addedGroups.map(item => {
      return item.name;
    });

    console.log({
      groupList: userGroups,
      name: this.state.firstName,
      password: this.state.password,
      surname: this.state.lastName,
      username: this.state.username
    });

    axios
      .post(url, {
        groupList: userGroups,
        name: this.state.firstName,
        password: this.state.password,
        role: this.state.role,
        surname: this.state.lastName,
        username: this.state.username
      })
      .then(response => {
        this.props.onHide();
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
        id="editUserModal"
      >
        <Modal.Header closeButton>
          <Modal.Title>New User</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={this.handleSubmit}>
            <UserInformation
              initalDataTransfer={this.initalDataTransfer}
              handleFirstNameChange={this.handleFirstNameChange}
              handleLastNameChange={this.handleLastNameChange}
              handleUsernameChange={this.handleUsernameChange}
              handlePasswordChange={this.handlePasswordChange}
              handleRoleChange={this.handleRoleChange}
              handleUsernameExists={this.handleUsernameExists}
              setUserGroups={this.setUserGroups}
              firstName={this.state.firstName}
              lastName={this.state.lastName}
              username={this.state.username}
              password={this.state.password}
              role={this.state.role}
              userData={this.props.userData}
              groupData={this.state.allGroups}
              ownerName={this.props.ownerName}
            />

            <hr className="m-1" />

            <Groups
              userGroups={this.state.userGroups}
              setAddedUserGroups={this.setAddedUserGroups}
            />

            <hr className="m-1" />

            <UsersGroups userGroups={this.state.addedGroups} />

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
                  Submit
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
