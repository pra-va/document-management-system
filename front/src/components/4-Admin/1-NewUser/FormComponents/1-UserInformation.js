import React, { Component } from "react";

class UserInformation extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showPassword: "",
      firstName: "",
      lastName: "",
      username: "",
      password: "",
      role: "USER"
    };
  }

  handleFirstNameChange = event => {
    this.setState({ firstName: event.target.value });
    console.log(this.state.firstName);
  };

  handleLastNameChange = event => {
    this.setState({ lastName: event.target.value });
    console.log(this.state.lastName);
  };

  handleUsernameChange = event => {
    this.setState({ username: event.target.value });
    console.log(this.state.username);
  };

  handlePasswordChange = event => {
    this.setState({ password: event.target.value });
    console.log(this.state.password);
  };

  handleClick = event => {
    this.setState({ showPassword: event.target.checked });
  };

  adminRadioChange = event => {
    this.setState({ adminRole: event.target.value });
  };

  userRadioChange = event => {
    this.setState({ userRole: event.target.value });
  };

  render() {
    return (
      <div>
        <h3 className="d-flex justify-content-start">
          1. Enter new user information.
        </h3>

        <div className="form-group row">
          <label htmlFor="inputFirstName" className="col-sm-2 col-form-label">
            First Name:
          </label>
          <div className="col-sm-10">
            <input
              autoComplete="on"
              required
              type="text"
              className="form-control"
              id="inputFirstName"
              placeholder="John"
              pattern="[A-Za-z0-9]{1,20}"
              onChange={this.handleFirstNameChange}
              value={this.state.firstName}
            />
          </div>
        </div>
        <div className="form-group row">
          <label htmlFor="inputLastName" className="col-sm-2 col-form-label">
            Last Name:
          </label>
          <div className="col-sm-10">
            <input
              autoComplete="on"
              required
              type="text"
              className="form-control"
              id="inputLastName"
              placeholder="Smith"
              pattern="[A-Za-z0-9]{1,20}"
              onChange={this.handleLastNameChange}
              value={this.state.lastName}
            />
          </div>
        </div>
        <div className="form-group row">
          <label htmlFor="inputUsername" className="col-sm-2 col-form-label">
            Username:
          </label>
          <div className="col-sm-10">
            <input
              autoComplete="on"
              required
              type="text"
              className="form-control"
              id="inputUsername"
              placeholder="JohnSmith"
              pattern="[A-Za-z0-9]{1,20}"
              onChange={this.handleUsernameChange}
              value={this.state.username}
            />
          </div>
        </div>
        <div className="form-group row">
          <label htmlFor="inputPassword" className="col-sm-2 col-form-label">
            Password:
          </label>
          <div className="col-sm-10">
            <input
              autoComplete="on"
              required
              type={this.state.showPassword === true ? "text" : "password"}
              className="form-control"
              id="inputPassword"
              placeholder="1234"
              pattern="[A-Za-z0-9]{8,20}"
              onChange={this.handlePasswordChange}
              value={this.state.password}
            />
          </div>
        </div>
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
                onClick={this.handleClick}
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
                onChange={this.userRadioChange}
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
