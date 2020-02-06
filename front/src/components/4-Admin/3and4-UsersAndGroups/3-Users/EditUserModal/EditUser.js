import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import "./../../../1-NewUser/NewUser.css";
import UserInformation from "./1-EditUserInformation";
import Groups from "./../../../1-NewUser/FormComponents/2-Groups";
import UsersGroups from "./../../../1-NewUser/FormComponents/3-UsersGroups";
import axios from "axios";
import AddOrRemoveButton from "./../../../../6-CommonElements/4-Buttons/1-AddRemove/ButtonAddOrRemove";

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

  componentDidMount() {
    this.getGroupData();
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

  getGroupData = () => {
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
                added={this.hasUserAddedGroup(item.name)}
              />
            ),
            added: false
          };
        });
        this.setState({
          allGroups: tempData
        });
        this.filterAddedGroups();
      })
      .catch(error => {
        console.log(error);
      });
  };

  hasUserAddedGroup = groupName => {
    for (let i = 0; i < this.state.userGroups.length; i++) {
      const element = this.state.userGroups[i];
      if (element === groupName) {
        return true;
      }
    }
    return false;
  };

  setUserGroups = userGroups => {
    this.setState({ userGroups: userGroups });
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

    let userGroups = [];

    for (let i = 0; i < this.state.addedGroups.length; i++) {
      const element = this.state.addedGroups[i];
      userGroups.push(element.name);
    }

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
