import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import "./NewUser.css";
import UserInformation from "./FormComponents/1-UserInformation";
import Groups from "./FormComponents/2-Groups";
import UsersGroups from "./FormComponents/3-UsersGroups";
import axios from "axios";
import AddOrRemoveButton from "./../../6-CommonElements/4-Buttons/1-AddRemove/ButtonAddOrRemove";

class NewModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      firstName: "",
      lastName: "",
      username: "",
      password: "",
      role: "",
      allGroups: [],
      notAddedGroups: [],
      addedGroups: [],
      usernameExists: false
    };
  }

  changeAddedStatus = name => {
    let tmpGroups = this.state.allGroups;
    for (let i = 0; i < tmpGroups.length; i++) {
      const element = tmpGroups[i];
      if (element.name === name) {
        tmpGroups[i].added = !tmpGroups[i].added;
        tmpGroups[i].addOrRemove = (
          <AddOrRemoveButton
            itemName={element.name}
            changeAddedStatus={this.changeAddedStatus}
            added={element.added}
          />
        );
        this.setState({ allGroups: tmpGroups });
        this.filterAddedGroups();
      }
    }
  };

  filterAddedGroups = () => {
    let filterGroups = this.state.allGroups;
    let notAdded = [];
    let added = [];
    for (let i = 0; i < filterGroups.length; i++) {
      const element = filterGroups[i];
      if (element.added) {
        added.push(element);
      } else {
        notAdded.push(element);
      }
    }
    this.setState({ notAddedGroups: notAdded });
    this.setState({ addedGroups: added });
  };

  componentDidMount() {
    axios
      .get("http://localhost:8080/dvs/api/groups")
      .then(response => {
        let tempData = response.data.map((item, index) => {
          return {
            number: index + 1,
            name: item.name,
            addOrRemove: (
              <AddOrRemoveButton
                itemName={item.name}
                changeAddedStatus={this.changeAddedStatus}
                added={false}
              />
            ),
            added: false
          };
        });
        this.setState({
          allGroups: tempData,
          notAddedGroups: [],
          addedGroups: []
        });
        this.filterAddedGroups();
      })
      .catch(error => {
        console.log(error);
      });
  }

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

  handleNewUserSubmit = event => {
    event.preventDefault();
    let url = "http://localhost:8080/dvs/api/";
    if (this.state.role === "ADMIN") {
      url += "createadmin/";
    } else {
      url += "createuser/";
    }

    let userGroups = [];

    for (let i = 0; i < this.state.addedGroups.length; i++) {
      const element = this.state.addedGroups[i];
      userGroups.push(element.name);
    }

    axios
      .post(url, {
        groupList: userGroups,
        name: this.state.firstName,
        password: this.state.password,
        surname: this.state.lastName,
        username: this.state.username
      })
      .then(response => {
        if (response.status === 201) {
          console.log("201");
        }
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
              handleUsernameExists={this.handleUsernameExists}
              firstName={this.state.firstName}
              lastName={this.state.lastName}
              username={this.state.username}
              password={this.state.password}
              role={this.state.role}
            />

            <hr className="m-1" />

            <Groups tableData={this.state.notAddedGroups} />

            <hr className="m-1" />

            <UsersGroups usersGroups={this.state.addedGroups} />

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
