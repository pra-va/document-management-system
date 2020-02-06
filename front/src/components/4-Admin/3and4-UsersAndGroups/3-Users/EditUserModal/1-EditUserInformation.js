import React, { Component } from "react";
import InputLine from "../../../../6-CommonElements/3-FormSingleInput/FormSingleInput";
import axios from "axios";

class UserInformation extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showPassword: "",
      firstName: "",
      lastName: "",
      username: "",
      password: "",
      role: "",
      usernameExists: false,
      updatePassword: false
    };
  }

  handleFirstNameChange = event => {
    this.setState({ firstName: event.target.value });
    this.props.handleFirstNameChange(event.target.value);
  };

  handleLastNameChange = event => {
    this.setState({ lastName: event.target.value });
    this.props.handleLastNameChange(event.target.value);
  };

  handleUsernameChange = event => {
    this.setState({ username: event.target.value });
    this.props.handleUsernameChange(event.target.value);
    this.checkIfUsernameExists();
  };

  handlePasswordChange = event => {
    this.setState({ password: event.target.value });
    this.props.handlePasswordChange(event.target.value);
  };

  handleShowPassword = event => {
    this.setState({ showPassword: event.target.checked });
  };

  handleRoleChange = event => {
    this.props.handleRoleChange(event.target.value);
  };

  updatePassword = event => {
    this.setState({ updatePassword: event.target.checked });
    if (!event.target.checked) {
      this.setState({ password: "" });
    }
  };

  componentDidMount() {
    this.getUserData();
  }

  getUserData = () => {
    console.log(this.props.ownerName);
    axios
      .get("http://localhost:8080/dvs/api/user/" + this.props.ownerName)
      .then(response => {
        this.setState({
          firstName: response.data.name,
          lastName: response.data.surname,
          username: response.data.username,
          role: response.data.role
        });
        this.props.initalDataTransfer({});
        this.props.setUserGroups(response.data.groupList);
      })
      .catch(error => console.log(error));
  };

  checkIfUsernameExists = () => {
    setTimeout(() => {
      if (this.state.username.length > 0) {
        axios
          .get(
            "http://localhost:8080/dvs/api/" + this.state.username + "/exists"
          )
          .then(response => {
            this.setState({ usernameExists: response.data });
            if (response.data && this.state.username !== this.props.ownerName) {
              this.setState({ usernameExists: true });
              this.props.handleUsernameExists(true);
            } else {
              this.setState({ usernameExists: false });
              this.props.handleUsernameExists(false);
            }
          })
          .catch(error => {
            console.log(error);
          });
      }
    }, 1000);
  };

  render() {
    return (
      <div>
        <h3 className="d-flex justify-content-start">
          1. Enter new user information.
        </h3>

        <InputLine
          id={"inputFirstName"}
          labelName={"First Name:"}
          required={true}
          type={"text"}
          placeholder={"John"}
          onChange={this.handleFirstNameChange}
          value={this.state.firstName}
        />

        <InputLine
          id={"inputLastName"}
          labelName={"Last Name:"}
          required={true}
          type={"text"}
          placeholder={"Smith"}
          onChange={this.handleLastNameChange}
          value={this.state.lastName}
        />

        <InputLine
          id={"inputUsername"}
          labelName={"Username:"}
          required={true}
          type={"text"}
          placeholder={"holyDiver"}
          onChange={this.handleUsernameChange}
          value={this.state.username}
        />

        {this.state.updatePassword ? (
          <div>
            <InputLine
              id={"inputPassword"}
              labelName={"Password:"}
              required={true}
              type={this.state.showPassword === true ? "text" : "password"}
              placeholder={"1234"}
              onChange={this.handlePasswordChange}
              value={this.state.password}
            />
            <div className="form-group row">
              <div className="col-sm-2"></div>
              <div className="col-sm-10">
                <div className="form-check d-flex justify-content-start">
                  <label
                    className="form-check-label"
                    htmlFor="checkBoxShowPassword"
                  >
                    Show Password
                  </label>
                  <input
                    autoComplete="on"
                    className="form-check-input"
                    type="checkbox"
                    id="checkBoxShowPassword"
                    onClick={this.handleShowPassword}
                  />
                </div>
              </div>
            </div>
          </div>
        ) : (
          <span></span>
        )}

        <div className="form-group row">
          <div className="col-sm-2"></div>
          <div className="col-sm-10">
            <div className="form-check d-flex justify-content-start">
              <label
                className="form-check-label"
                htmlFor="checkBoxShowPassword"
              >
                Update password
              </label>
              <input
                autoComplete="on"
                className="form-check-input"
                type="checkbox"
                id="checkBoxShowPassword"
                onClick={this.updatePassword}
              />
            </div>
          </div>
        </div>

        <div className="form-group row d-flex justify-content-center">
          {this.state.username.length > 0 ? (
            this.state.usernameExists ? (
              <h5>Username {this.state.username} is taken.</h5>
            ) : (
              <div></div>
            )
          ) : (
            <div></div>
          )}
        </div>

        <div className="form-group row">
          <div className="col-sm-2">Admin:</div>
          <div className="col-sm-10 d-flex align-content-start">
            <div className="form-check form-check-inline">
              <input
                autoComplete="on"
                className="form-check-input"
                type="radio"
                name="adminOrUser"
                id="radioAdmin"
                value="ADMIN"
                onChange={this.handleRoleChange}
                onClick={() => {
                  this.setState({ role: "ADMIN" });
                }}
                checked={this.state.role === "ADMIN" ? true : false}
              />
              <label className="form-check-label" htmlFor="inputRadioAdmin">
                Yes
              </label>
            </div>
            <div className="form-check form-check-inline">
              <input
                autoComplete="on"
                className="form-check-input"
                type="radio"
                name="adminOrUser"
                id="radioUser"
                value="USER"
                onChange={this.handleRoleChange}
                onClick={() => {
                  this.setState({ role: "USER" });
                }}
                checked={this.state.role === "ADMIN" ? false : true}
              />
              <label className="form-check-label" htmlFor="inputRadioUser">
                No
              </label>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default UserInformation;
