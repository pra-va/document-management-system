import React, { Component } from "react";
import "./NewUser.css";

class NewUser extends Component {
  constructor(props) {
    super(props);
    this.state = { showPassword: "" };
  }

  handleClick = event => {
    console.log(event.target.checked);
    this.setState({ showPassword: event.target.checked });
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
              <h3 className="d-flex justify-content-start">
                1. Enter new user information.
              </h3>
              <form>
                <div className="form-group row">
                  <label
                    htmlFor="inputFirstName"
                    className="col-sm-2 col-form-label"
                  >
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
                    />
                  </div>
                </div>
                <div className="form-group row">
                  <label
                    htmlFor="inputLastName"
                    className="col-sm-2 col-form-label"
                  >
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
                    />
                  </div>
                </div>
                <div className="form-group row">
                  <label
                    htmlFor="inputUsername"
                    className="col-sm-2 col-form-label"
                  >
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
                    />
                  </div>
                </div>
                <div className="form-group row">
                  <label
                    htmlFor="inputPassword"
                    className="col-sm-2 col-form-label"
                  >
                    Password:
                  </label>
                  <div className="col-sm-10">
                    <input
                      autoComplete="on"
                      required
                      type={
                        this.state.showPassword === true ? "text" : "password"
                      }
                      className="form-control"
                      id="inputPassword"
                      placeholder="1234"
                      pattern="[A-Za-z0-9]{8,20}"
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
                        name="inlineRadioOptions"
                        id="inputRadio"
                        value="ADMIN"
                      />
                      <label
                        className="form-check-label"
                        htmlFor="inputRadioAdmin"
                      >
                        Yes
                      </label>
                    </div>
                    <div className="form-check form-check-inline">
                      <input
                        autoComplete="on"
                        className="form-check-input"
                        type="radio"
                        name="inlineRadioOptions"
                        id="inputRadioUser"
                        value="USER"
                        checked
                      />
                      <label
                        className="form-check-label"
                        htmlFor="inputRadioUser"
                      >
                        No
                      </label>
                    </div>
                  </div>
                </div>

                <hr />
                <div className="row d-flex justify-content-start">
                  <h3>2. Add user to a group.</h3>
                </div>

                <div class="form-group row">
                  <div class="col-4">
                    <div class="list-group" id="list-tab" role="tablist">
                      <a
                        class="list-group-item list-group-item-action active"
                        id="list-home-list"
                        data-toggle="list"
                        href="#list-home"
                        role="tab"
                        aria-controls="home"
                      >
                        HR
                      </a>
                      <a
                        class="list-group-item list-group-item-action"
                        id="list-profile-list"
                        data-toggle="list"
                        href="#list-profile"
                        role="tab"
                        aria-controls="profile"
                      >
                        Workers
                      </a>
                      <a
                        class="list-group-item list-group-item-action"
                        id="list-messages-list"
                        data-toggle="list"
                        href="#list-messages"
                        role="tab"
                        aria-controls="messages"
                      >
                        Signs HR Documents
                      </a>
                      <a
                        class="list-group-item list-group-item-action"
                        id="list-settings-list"
                        data-toggle="list"
                        href="#list-settings"
                        role="tab"
                        aria-controls="settings"
                      >
                        Administrations
                      </a>
                      <a
                        class="list-group-item list-group-item-action"
                        id="list-settings-list"
                        data-toggle="list"
                        href="#list-settings2"
                        role="tab"
                        aria-controls="settings"
                      >
                        Can Sign Everything
                      </a>
                    </div>
                  </div>
                  <div class="col-8">
                    <div class="tab-content" id="nav-tabContent">
                      <div
                        class="tab-pane fade show active"
                        id="list-home"
                        role="tabpanel"
                        aria-labelledby="list-home-list"
                      >
                        Group for Human resources' documents.
                      </div>
                      <div
                        class="tab-pane fade"
                        id="list-profile"
                        role="tabpanel"
                        aria-labelledby="list-profile-list"
                      >
                        This group is for workers of the company. Workers will
                        be able to create documents using authorities granted by
                        this group.
                      </div>
                      <div
                        class="tab-pane fade"
                        id="list-messages"
                        role="tabpanel"
                        aria-labelledby="list-messages-list"
                      >
                        ...
                      </div>
                      <div
                        class="tab-pane fade"
                        id="list-settings"
                        role="tabpanel"
                        aria-labelledby="list-settings-list"
                      >
                        ...
                      </div>
                      <div
                        class="tab-pane fade"
                        id="list-settings2"
                        role="tabpanel"
                        aria-labelledby="list-settings-list"
                      >
                        ...
                      </div>
                    </div>
                  </div>
                </div>

                <hr />

                <div className="form-group row">
                  <div className="col-sm-10">
                    <button type="submit" className="btn btn-primary">
                      Sign in
                    </button>
                  </div>
                </div>
              </form>
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-dismiss="modal"
              >
                Close
              </button>
              <button type="button" className="btn btn-primary">
                Save changes
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default NewUser;
