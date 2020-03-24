import React, { Component } from "react";
import InputLine from "./../../../../../6-CommonElements/3-FormSingleInput/FormSingleInput";
import axios from "axios";
import serverUrl from "./../../../../../7-properties/1-URL";

class UserInformation extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showPassword: "",
      firstName: "",
      lastName: "",
      password: "",
      role: "USER",
      usernameExists: false,
      updatePassword: false
    };
  }

  componentDidMount() {
    this.fetchUserInfo();
  }

  handleFirstNameChange = event => {
    this.setState({ firstName: event.target.value });
    this.props.handleFirstNameChange(event.target.value);
  };

  handleLastNameChange = event => {
    this.setState({ lastName: event.target.value });
    this.props.handleLastNameChange(event.target.value);
  };

  fetchUserInfo = () => {
    axios
      .get(serverUrl + "user/" + this.props.ownerName)
      .then(response => {
        const user = response.data;
        const state = {
          firstName: user.name,
          lastName: user.surname,
          role: user.role,
          addedGroups: user.groupList
        };
        this.setState({ ...state });
        this.props.initialStateUpdate(state);
      })
      .catch(error => {
        console.log(error);
      });
  };

  handlePasswordChange = event => {
    this.setState({ password: event.target.value });
    this.props.handlePasswordChange(event.target.value);
  };

  handleShowPassword = event => {
    this.setState({ showPassword: event.target.checked });
  };

  adminRadioChange = event => {
    this.setState({ userRole: event.target.value });
    this.props.handleRoleChange(event.target.value);
  };

  userRadioChange = event => {
    this.setState({ userRole: event.target.value });
    this.props.handleRoleChange(event.target.value);
  };

  updatePassword = event => {
    if (event.target.checked) {
      this.setState({ updatePassword: event.target.checked });
    } else {
      this.setState({ updatePassword: event.target.checked, password: "" });
    }
  };

  render() {
    return (
      <div>
        <h3 className="d-flex justify-content-start">
          1. Update user information.
        </h3>

        <InputLine
          id={"inputFirstName"}
          labelName={"First Name:"}
          required={true}
          type={"text"}
          placeholder={"John"}
          onChange={this.handleFirstNameChange}
          value={this.state.firstName}
          pattern={2}
        />

        <InputLine
          id={"inputLastName"}
          labelName={"Last Name:"}
          required={true}
          type={"text"}
          placeholder={"Smith"}
          onChange={this.handleLastNameChange}
          value={this.state.lastName}
          pattern={2}
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
              pattern={0}
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
                htmlFor="checBoxUpdatePassword"
              >
                Update password
              </label>
              <input
                autoComplete="on"
                className="form-check-input"
                type="checkbox"
                id="checBoxUpdatePassword"
                onClick={this.updatePassword}
              />
            </div>
          </div>
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
                onChange={this.adminRadioChange}
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
                value="USER"
                className="form-check-input"
                type="radio"
                name="adminOrUser"
                id="radioUser"
                onChange={this.userRadioChange}
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
