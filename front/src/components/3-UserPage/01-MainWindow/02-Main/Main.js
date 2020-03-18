import React, { Component } from "react";
import "./Main.css";
import AdminPhoto from "./../../../../resources/admin.svg";
import MyDocuments from "./../../../../resources/my-documents.svg";
import NewDocument from "./../../../../resources/new-document.svg";
import SignDocument from "./../../../../resources/sign-document.svg";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";
import { Link } from "react-router-dom";

class Main extends Component {
  constructor(props) {
    super(props);
    this.state = { isUserAdmin: "false" };
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

  render() {
    return (
      <div className="container">
        <div className="row justify-content-center mt-4">
          <div className="col-6 col-md-3 my-4 card-width">
            <Link
              className="card text-white bg-dark card-heigth"
              to={"/dvs/document"}
            >
              <img
                src={NewDocument}
                className="card-img-top pl-4 p-3 invert"
                alt="..."
              />
              <div className="card-body">
                <h5 className="card-title card-label-text">Create Document</h5>
              </div>
            </Link>
          </div>
          <div className="col-6 col-md-3 my-4 card-width">
            <Link
              className="card text-white bg-dark card-heigth"
              to={"/dvs/sign"}
            >
              <img
                src={SignDocument}
                className="card-img-top p-3 invert"
                alt="..."
              />
              <div className="card-body">
                <h5 className="card-title card-label-text">Sign Document</h5>
              </div>
            </Link>
          </div>
          <div className="col-6 col-md-3 my-4 card-width">
            <Link
              className="card text-white bg-dark card-heigth"
              to={"/dvs/documents"}
            >
              <img
                src={MyDocuments}
                className="card-img-top p-3 third-img-heigth invert"
                alt="..."
              />
              <div className="card-body">
                <h5 className="card-title card-label-text">My Documents</h5>
              </div>
            </Link>
          </div>
          {this.state.isUserAdmin === true ? (
            <div className="col-6 col-md-3 my-4 card-width">
              <Link
                className="card text-white bg-dark card-heigth"
                to={"/dvs/admin"}
              >
                <img
                  className="card-img-top p-3 invert"
                  src={AdminPhoto}
                  alt="..."
                />
                <div className="card-body">
                  <h5 className="card-title card-label-text">Admin</h5>
                </div>
              </Link>
            </div>
          ) : (
            <span> </span>
          )}
        </div>
      </div>
    );
  }
}

export default Main;
