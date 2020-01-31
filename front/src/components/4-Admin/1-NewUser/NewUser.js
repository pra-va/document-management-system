import React, { Component } from "react";
import "./NewUser.css";
import UserInformation from "./FormComponents/1-UserInformation";
import Groups from "./FormComponents/2-Groups";
import UsersGroups from "./FormComponents/3-UsersGroups";
import axios from "axios";

class NewUser extends Component {
  constructor(props) {
    super(props);
    this.state = {
      firstName: "",
      lastName: "",
      username: "",
      password: "",
      role: "",
      allGroups: "",
      usersGroupos: ""
    };
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
        password: this.state.lastName,
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

                <Groups />

                <hr />

                <UsersGroups />

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
