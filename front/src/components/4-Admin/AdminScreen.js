import React, { Component } from "react";
import "../3-UserPage/01-MainWindow/02-Main/Main.css";
import axios from "axios";
import serverUrl from "./../7-properties/1-URL";
import Navigation from "./../3-UserPage/01-MainWindow/01-Navigation/Navigation";
import NewDocSvg from "./../../resources/Admin/NewDoc.svg";
import NewUserSvg from "./../../resources/Admin/NewUser.svg";
import NewGroupSvg from "./../../resources/Admin/NewGroup.svg";
import DocTypesSvg from "./../../resources/Admin/DocTypes.svg";
import UsersSvg from "./../../resources/Admin/Users.svg";
import GroupsSvg from "./../../resources/Admin/Groups.svg";
import NewUser from "./1-NewUser/NewUser";
import NewGroup from "./2-NewGroup/NewGroup";
import NewDocType from "./6-NewDocType/NewDocType";
import { Link } from "react-router-dom";
import "./AdminScreen.css";

class AdminScreen extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isUserAdmin: true,
      showNewUser: false,
      showNewGroup: false,
      showCreateDocType: false
    };
  }

  componentDidMount() {
    this.isUserAdminChecker();
  }

  isUserAdminChecker = () => {
    axios
      .get(serverUrl + "administrator")
      .then(response => {
        this.setState({ isUserAdmin: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  showNewUser = () => {
    this.setState({ showNewUser: true });
  };

  hideNewUser = () => {
    this.setState({ showNewUser: false });
  };

  showNewGroup = () => {
    this.setState({ showNewGroup: true });
  };

  hideNewGroup = () => {
    this.setState({ showNewGroup: false });
  };

  showCreateDocType = () => {
    this.setState({ showCreateDocType: true });
  };

  handleHideCreateDocType = () => {
    this.setState({ showCreateDocType: false });
  };

  render() {
    return (
      <div>
        <Navigation />

        {<NewUser show={this.state.showNewUser} onHide={this.hideNewUser} />}

        {
          <NewGroup
            showNewGroup={this.state.showNewGroup}
            hideNewGroup={this.hideNewGroup}
          />
        }

        {
          <NewDocType
            showNewDocType={this.state.showCreateDocType}
            hideNewDocType={this.handleHideCreateDocType}
          />
        }

        <div className="container">
          <div className="row justify-content-center mt-4">
            <div className="col-6 col-md-3 my-4 card-width">
              <button
                className="card text-white bg-dark card-heigth card-width"
                onClick={this.showNewUser}
              >
                <img
                  src={NewUserSvg}
                  className="card-img-top pl-4 p-3 invert"
                  alt="..."
                />
                <div className="card-body">
                  <h5 className="card-title card-label-text">New User</h5>
                </div>
              </button>
            </div>
            <div className="col-6 col-md-3 my-4 card-width">
              <button
                className="card text-white bg-dark card-heigth card-width"
                onClick={this.showNewGroup}
              >
                <img
                  src={NewGroupSvg}
                  className="card-img-top p-3 invert"
                  alt="..."
                />
                <div className="card-body">
                  <h5 className="card-title card-label-text">New Group</h5>
                </div>
              </button>
            </div>
            <div className="col-6 col-md-3 my-4 card-width">
              <button
                className="card text-white bg-dark card-heigth card-width"
                onClick={this.showCreateDocType}
              >
                <img
                  src={NewDocSvg}
                  className="card-img-top p-3 third-img-heigth invert"
                  alt="..."
                />
                <div className="card-body">
                  <h5 className="card-title card-label-text">New Doc Type</h5>
                </div>
              </button>
            </div>
          </div>

          <div className="row justify-content-center mt-4">
            <div className="col-6 col-md-3 my-4 card-width">
              <Link
                className="card text-white bg-dark card-heigth card-width"
                to="/dvs/users"
              >
                <img
                  src={UsersSvg}
                  className="card-img-top pl-4 p-3 invert"
                  alt="..."
                />
                <div className="card-body">
                  <h5 className="card-title card-label-text">Users</h5>
                </div>
              </Link>
            </div>
            <div className="col-6 col-md-3 my-4 card-width">
              <Link
                className="card text-white bg-dark card-heigth card-width"
                to="/dvs/groups"
              >
                <img
                  src={GroupsSvg}
                  className="card-img-top p-3 invert"
                  alt="..."
                />
                <div className="card-body">
                  <h5 className="card-title card-label-text">Groups</h5>
                </div>
              </Link>
            </div>
            <div className="col-6 col-md-3 my-4 card-width">
              <Link
                className="card text-white bg-dark card-heigth card-width"
                to="/dvs/doctypes"
              >
                <img
                  src={DocTypesSvg}
                  className="card-img-top p-3 third-img-heigth invert"
                  alt="..."
                />
                <div className="card-body">
                  <h5 className="card-title card-label-text">Document Types</h5>
                </div>
              </Link>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default AdminScreen;
