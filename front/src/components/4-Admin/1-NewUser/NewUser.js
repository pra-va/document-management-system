import React, { Component } from "react";
import "./NewUser.css";
import UserInformation from "./FormComponents/1-UserInformation";
import Groups from "./FormComponents/2-Groups";
import UsersGroups from "./FormComponents/3-UsersGroups";
import axios from "axios";
import AddOrRemoveButton from "./FormComponents/4-ButtonAddOrRemoveUserGroup";

class NewUser extends Component {
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
      addedGroups: []
    };
  }

  changeAddedStatus = (name, itemIsAdded) => {
    let tmpGroups = this.state.allGroups;
    for (let i = 0; i < tmpGroups.length; i++) {
      const element = tmpGroups[i];
      if (element.name === name) {
        tmpGroups[i].added = !tmpGroups[i].added;
        tmpGroups[i].addOrRemove = (
          <AddOrRemoveButton
            groupName={element.name}
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

  // IMPORTANT
  // TODO. Padaryti, kad uzkrautu tik pirma karta.
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
                groupName={item.name}
                changeAddedStatus={this.changeAddedStatus}
                added={false}
              />
            ),
            added: false
          };
        });
        this.setState({ allGroups: tempData });
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

  handleNewUserSubmit = () => {
    let url = "http://localhost:8080/dvs/api/";
    if (this.state.role === "ADMIN") {
      url += "createadmin/";
    } else {
      url += "createuser/";
    }

    axios
      .post(url, {
        name: this.state.firstName,
        password: this.state.password,
        surname: this.state.lastName,
        username: this.state.username
      })
      .then(response => {
        console.log("User " + this.state.username + " created.");
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <div
        className="modal fade"
        id="newUser"
        tabIndex="-1"
        role="dialog"
        aria-labelledby="Create New User"
        aria-hidden="true"
      >
        <div className="modal-dialog modal-lg" role="document">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="newUserModal">
                New User
              </h5>
              <button
                type="button"
                className="close"
                data-dismiss="modal"
                aria-label="Close"
                id="closeNewUserModal"
              >
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div className="modal-body">
              <form>
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

                <hr />

                <Groups tableData={this.state.notAddedGroups} />

                <hr />

                <UsersGroups usersGroups={this.state.addedGroups} />

                <div className="form-group row d-flex justify-content-center">
                  <div className="modal-footer ">
                    <button
                      type="button"
                      className="btn btn-secondary"
                      data-dismiss="modal"
                    >
                      Cancel
                    </button>
                    <button
                      type="submit"
                      className="btn btn-primary"
                      onClick={this.handleNewUserSubmit}
                    >
                      Create
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default NewUser;
