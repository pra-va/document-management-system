import React, { useState } from "react";
import { Link } from "react-router-dom";
import logo from "./../../../../resources/logo.png";
import NewUser from "../../../4-Admin/1-NewUser/NewUser";
import NewGroup from "./../../../4-Admin/2-NewGroup/NewGroup";
import NewDocType from "./../../../4-Admin/6-NewDocType/NewDocType";
import UsersProfile from "./../../02-Profile/UsersProfile";

var NavigationPresentation = props => {
  const [showCreateUser, setShowCreateUser] = useState(false);
  const handleCloseCreateUser = () => setShowCreateUser(false);
  const handleShowCreateUser = () => setShowCreateUser(true);

  const [showCreateGroup, setShowCreateGroup] = useState(false);
  const handleHideCreateGroup = () => setShowCreateGroup(false);
  const handleShowCreateGroup = () => setShowCreateGroup(true);

  const [showCreateDocType, setShowCreateDocType] = useState(false);
  const handleHideCreateDocType = () => setShowCreateDocType(false);
  const handleShowCreateDocType = () => setShowCreateDocType(true);

  const [showProfile, setShowProfile] = useState(false);
  const handleHideProfile = () => setShowProfile(false);
  const handleShowProfile = () => setShowProfile(true);

  return (
    <nav className="navbar navbar-dark bg-dark navbar-expand-lg navbar-fixed-top">
      <NewUser show={showCreateUser} onHide={handleCloseCreateUser} />

      <NewGroup
        showNewGroup={showCreateGroup}
        hideNewGroup={handleHideCreateGroup}
      />

      <NewDocType
        showNewDocType={showCreateDocType}
        hideNewDocType={handleHideCreateDocType}
      />

      <UsersProfile
        showProfile={showProfile}
        handleHideProfile={handleHideProfile}
      />

      <Link to="/dvs/home" className="navbar-brand invert">
        <img src={logo} alt="unable to load" className="width-30" />
      </Link>

      <button
        className="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target=".navbar-collapse"
      >
        <span className="navbar-toggler-icon"></span>
      </button>

      <div className="collapse navbar-collapse" id="navbarSupportedContent">
        <ul className="navbar-nav mr-auto link-text-format">
          <li className="nav-item active">
            <Link className="nav-link link-text-format" to="/dvs/document">
              Create Document <span className="sr-only">(current)</span>
            </Link>
          </li>

          <li className="nav-item active">
            <Link className="nav-link link-text-format" to="/dvs/sign">
              Sign Document <span className="sr-only">(current)</span>
            </Link>
          </li>

          <li className="nav-item active">
            <Link className="nav-link link-text-format" to="/dvs/documents">
              My Documents <span className="sr-only">(current)</span>
            </Link>
          </li>

          <li className="nav-item active">
            <Link className="nav-link link-text-format" to="/dvs/statistics">
              Statistics <span className="sr-only">(current)</span>
            </Link>
          </li>

          {props.role === true ? (
            <li className="nav-item dropdown">
              <Link
                to="#"
                className="nav-link dropdown-toggle link-text-format"
                id="navbarDropdown"
                role="button"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
              >
                Admin
              </Link>
              <div className="dropdown-menu" aria-labelledby="navbarDropdown">
                <button
                  className="dropdown-item link-text-format"
                  onClick={handleShowCreateUser}
                  id="showCreateUser"
                >
                  New User
                </button>
                <button
                  className="dropdown-item link-text-format"
                  onClick={handleShowCreateGroup}
                  id="showCreateGroup"
                >
                  New Group
                </button>
                <button
                  className="dropdown-item link-text-format"
                  onClick={handleShowCreateDocType}
                  id="showCreateDoc"
                >
                  New Document Type
                </button>

                <Link
                  to="/dvs/users"
                  className="dropdown-item link-text-format"
                >
                  Users
                </Link>
                <Link
                  to="/dvs/groups"
                  className="dropdown-item link-text-format"
                >
                  Groups
                </Link>
                <Link
                  to="/dvs/doctypes"
                  className="dropdown-item link-text-format"
                >
                  Document Types
                </Link>
              </div>
            </li>
          ) : (
            <h4> </h4>
          )}
        </ul>

        <Link
          className="nav-link link-text-format"
          to="#"
          onClick={handleShowProfile}
        >
          Profile<span className="sr-only">(current)</span>
        </Link>

        <Link
          className="nav-link link-text-format"
          to="/dvs/"
          onClick={props.handleLogout}
        >
          Logout <span className="sr-only">(current)</span>
        </Link>
      </div>
    </nav>
  );
};

export default NavigationPresentation;
