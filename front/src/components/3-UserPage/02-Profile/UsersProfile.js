import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import UserLogo from "./../../../resources/user.svg";
import UsernameReciever from "./Components/1-UsernameReciever";
import UserDetails from "./Components/2-UserDetails";

class UsersProfile extends Component {
  constructor(props) {
    super(props);
    this.state = { serverUserDetails: [], username: "" };
  }

  setUpUsername = username => {
    if (username && this.state.username !== username) {
      this.setState({ username: username });
    }
  };

  render() {
    return (
      <div>
        <Modal
          show={this.props.showProfile}
          onHide={this.props.handleHideProfile}
        >
          <Modal.Header closeButton>
            <Modal.Title>Logged in Profile</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div className="m-3">
              <div className="row d-flex justify-content-center">
                <img src={UserLogo} alt={"Unable to load..."} />
              </div>
              <div className="row d-flex justify-content-center">
                <UsernameReciever setUpUsername={this.setUpUsername} />
              </div>
              <hr className="m-1" />
              <div className="row d-flex justify-content-center">
                <UserDetails username={this.state.username} />
              </div>
              <hr className="m-1" />
              <div className="row d-flex justify-content-center">
                <button
                  className="btn btn-dark mt-3"
                  onClick={this.props.handleHideProfile}
                >
                  Close
                </button>
              </div>
            </div>
          </Modal.Body>
        </Modal>
      </div>
    );
  }
}

export default UsersProfile;
