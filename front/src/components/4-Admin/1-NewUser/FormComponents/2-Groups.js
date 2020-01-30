import React, { Component } from "react";
import "./2-Groups.css";
import SearchIcon from "./../../../../resources/search.svg";

class Groups extends Component {
  render() {
    return (
      <div className="mx-3">
        <div className="row d-flex justify-content-start">
          <h3 className="d-flex justify-content-start">
            2. Add user to a group.
          </h3>
        </div>

        <div className="row p-3">
          <div className="input-group md-form form-sm form-1 pl-0">
            <div className="input-group-prepend">
              <span
                className="input-group-text purple lighten-3"
                id="basic-text1"
              >
                <i
                  className="text-white"
                  aria-hidden="true"
                  href={SearchIcon}
                ></i>
                <img src={SearchIcon} alt="..." className="searchIconResize" />
              </span>
            </div>
            <input
              className="form-control my-0 py-1"
              type="text"
              placeholder="Search"
              aria-label="Search"
            />
          </div>
        </div>

        <div className="form-group row">
          <div className="col-4">
            <div className="list-group" id="list-tab" role="tablist">
              <a
                className="list-group-item list-group-item-action active"
                id="list-home-list"
                data-toggle="list"
                href="#list-home"
                role="tab"
                aria-controls="home"
              >
                HR
              </a>
              <a
                className="list-group-item list-group-item-action"
                id="list-profile-list"
                data-toggle="list"
                href="#list-profile"
                role="tab"
                aria-controls="profile"
              >
                Workers
              </a>
              <a
                className="list-group-item list-group-item-action"
                id="list-messages-list"
                data-toggle="list"
                href="#list-messages"
                role="tab"
                aria-controls="messages"
              >
                Signs HR Documents
              </a>
              <a
                className="list-group-item list-group-item-action"
                id="list-settings-list"
                data-toggle="list"
                href="#list-settings"
                role="tab"
                aria-controls="settings"
              >
                Administrations
              </a>
              <a
                className="list-group-item list-group-item-action"
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
          <div className="col-8">
            <div className="tab-content" id="nav-tabContent">
              <div
                className="tab-pane fade show active"
                id="list-home"
                role="tabpanel"
                aria-labelledby="list-home-list"
              >
                <div className="row m-3">
                  <button className="btn btn-secondary">+ Add</button>
                </div>
                <div className="row m-3">
                  Group for Human resources' documents.
                </div>
              </div>
              <div
                className="tab-pane fade"
                id="list-profile"
                role="tabpanel"
                aria-labelledby="list-profile-list"
              >
                This group is for workers of the company. Workers will be able
                to create documents using authorities granted by this group.
              </div>
              <div
                className="tab-pane fade"
                id="list-messages"
                role="tabpanel"
                aria-labelledby="list-messages-list"
              >
                ...
              </div>
              <div
                className="tab-pane fade"
                id="list-settings"
                role="tabpanel"
                aria-labelledby="list-settings-list"
              >
                ...
              </div>
              <div
                className="tab-pane fade"
                id="list-settings2"
                role="tabpanel"
                aria-labelledby="list-settings-list"
              >
                ...
              </div>
            </div>
          </div>
        </div>
        <h4 className="d-flex justify-content-start">Paging data.</h4>
      </div>
    );
  }
}

export default Groups;
