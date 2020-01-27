import React, { Component } from "react";
import { Link } from "react-router-dom";
import logo from "./../../../../resources/logo.png";
import axios from "axios";
import "./Navigation.css";

class Navigation extends Component {
  constructor(props) {
    super(props);
    this.state = { isUserAdmin: this.props.isUserAdmin };
  }
  handleLogout = event => {
    axios
      .get("http://localhost:8080/dvs/api/logout")
      .then(response => {
        console.log("Logged out.");
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentWillUnmount = () => {
    this.handleLogout();
  };

  render() {
    return (
      <nav className="navbar navbar-dark bg-dark navbar-expand-md navbar-fixed-top">
        <Link to="/home" className="navbar-brand invert">
          <img src={logo} alt="unable to load" className="width-30" />
        </Link>

        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav mr-auto">
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

            {this.state.isUserAdmin === "true" ? (
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
                  <Link
                    to="/admin/add"
                    className="dropdown-item link-text-format"
                  >
                    Add New Product
                  </Link>
                  <Link
                    to="/admin/list"
                    className="dropdown-item link-text-format"
                  >
                    Products List
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
  }
}

export default Navigation;
