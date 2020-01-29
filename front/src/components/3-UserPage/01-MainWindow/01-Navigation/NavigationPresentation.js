import React from "react";
import { Link } from "react-router-dom";
import logo from "./../../../../resources/logo.png";

import NewUser from "./../../../4-Admin/1-NewUser/NewUser";

var NavigationPresentation = props => {
  return (
    <nav className="navbar navbar-dark bg-dark navbar-expand-md navbar-fixed-top">
      <NewUser />
      <Link to="/home" className="navbar-brand invert">
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
            <Link className="nav-link link-text-format" to="/home">
              Create Document <span className="sr-only">(current)</span>
            </Link>
          </li>

          <li className="nav-item active">
            <Link className="nav-link link-text-format" to="/merch">
              Sign Document <span className="sr-only">(current)</span>
            </Link>
          </li>

          <li className="nav-item active">
            <Link className="nav-link link-text-format" to="/merch">
              My Documents <span className="sr-only">(current)</span>
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
                  data-toggle="modal"
                  data-target="#newUser"
                >
                  New User
                </button>
                <Link
                  to="/admin/add"
                  className="dropdown-item link-text-format"
                >
                  New Group
                </Link>

                <Link
                  to="/admin/list"
                  className="dropdown-item link-text-format"
                >
                  Groups
                </Link>
                <Link
                  to="/admin/list"
                  className="dropdown-item link-text-format"
                >
                  Users
                </Link>
              </div>
            </li>
          ) : (
            <h4> </h4>
          )}
        </ul>

        <Link className="nav-link link-text-format" to="/">
          Logout <span className="sr-only">(current)</span>
        </Link>
      </div>
    </nav>
  );
};

export default NavigationPresentation;
