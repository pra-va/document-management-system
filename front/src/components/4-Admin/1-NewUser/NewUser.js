import React, { Component } from "react";
import "./NewUser.css";
import UserInformation from "./FormComponents/1-UserInformation";
import Groups from "./FormComponents/2-Groups";
import UsersGroups from "./FormComponents/3-UsersGroups";

class NewUser extends Component {
  constructor(props) {
    super(props);
    this.state = { showPassword: "" };
  }

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
                <UserInformation />

                <hr />

                <Groups />

                <hr />

                <UsersGroups />

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
