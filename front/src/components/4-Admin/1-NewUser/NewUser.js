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

  componentDidUpdate() {}

  setUpGroups = data => {
    if (data.length >= 0) {
      this.parseData(data);
    }
  };

  parseData = data => {
    let tempData = data.map((item, index) => {
      return {
        number: index + 1,
        name: item.name,
        addOrRemove: (
          <button
            onClick={event => {
              event.preventDefault();
            }}
            className={
              this.state.addedGroups.includes(item.name)
                ? "btn btn-danger btn-sm"
                : "btn btn-secondary btn-sm"
            }
          >
            {this.state.addedGroups.includes(item.name) ? "Remove" : "Add"}
          </button>
        ),
        added: false,
        description: item.description
      };
    });
    this.setState({ allGroups: tempData });
  };

  processTableData = addedGroupList => {
    console.log("processing data");
    let tmpGroups = [...this.state.allGroups];
    for (let i = 0; i < tmpGroups.length; i++) {
      const element = tmpGroups[i];
      tmpGroups[i].added = addedGroupList.includes(element.name);
      tmpGroups[i].addOrRemove = (
        <button
          onClick={event => {
            event.preventDefault();
          }}
          className={
            addedGroupList.includes(element.name)
              ? "btn btn-danger btn-sm"
              : "btn btn-secondary btn-sm"
          }
        >
          {addedGroupList.includes(element.name) ? "Remove" : "Add"}
        </button>
      );
    }

    this.setState({ allGroups: this.loadingTable() });
    setTimeout(() => {
      this.setState({ allGroups: tmpGroups });
    }, 1);
  };

  loadingTable = () => {
    let loadingData = [];
    for (let i = 0; i < 8; i++) {
      loadingData.push({
        number: i,
        name: this.state.allGroups[i].name,
        addOrRemove: <button className="btn btn-secondary btn-sm">Add</button>
      });
    }
    return loadingData;
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
    this.processTableData(groupList);
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

            <Groups
              tableData={this.state.allGroups}
              setUpGroups={this.setUpGroups}
              setAddedGroups={this.setAddedGroups}
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
