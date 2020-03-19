import React, { useState } from "react";
import logo from "./../../../../resources/logo.png";
import "./../../Form.css";
import CreateUser from "./../CreateInitialUser/NewUser";

const LoginPresentation = props => {
  const [showCreateUser, setShowCreateUser] = useState(false);
  const handleCloseCreateUser = () => setShowCreateUser(false);
  const handleShowCreateUser = event => {
    event.preventDefault();
    setShowCreateUser(true);
  };

  const handleUsernameChange = event => {
    props.setUsername(event.target.value);
  };

  const handlePasswordChange = event => {
    props.setPassword(event.target.value);
  };

  const handleIncorectPasswordStateReset = () => {
    props.setLoginFailed(false);
  };

  return (
    <div className="row d-flex justify-content-center">
      <CreateUser show={showCreateUser} onHide={handleCloseCreateUser} />
      <form
        onSubmit={props.handleSubmit}
        id="loginForm"
        className="align-items-lg m-4"
      >
        <div className="login-form p-4 border border-dark rounded-lg">
          <img className="mb-3 width" src={logo} alt="unable to load" />
          <div className="form-group">
            <label
              className="d-flex justify-content-start col-form-label-lg mb-0 pb-0"
              htmlFor="username"
            >
              Username
            </label>
            <input
              type="text"
              className="form-control"
              id="username"
              aria-describedby="username"
              onChange={handleUsernameChange}
              autoComplete="on"
              disabled={props.initialCreate}
            />
          </div>
          <div className="form-group">
            <label
              className="d-flex justify-content-start col-form-label-lg mb-0 pb-0 pt-0"
              htmlFor="password"
            >
              Password
            </label>
            <input
              type="password"
              className="form-control"
              id="password"
              onChange={handlePasswordChange}
              autoComplete="on"
              disabled={props.initialCreate}
            />
          </div>
          {props.initialCreate ? (
            <button
              className="btn btn-black btn-lg btn-block mt-4"
              onClick={handleShowCreateUser}
            >
              Create Admin
            </button>
          ) : (
            <button
              type="submit"
              className="btn btn-black btn-lg btn-block mt-4"
            >
              Log In
            </button>
          )}
        </div>

        <div
          className={
            props.loginFailed
              ? "alert alert-danger alert-dismissible fade show my-3"
              : "alert alert-danger alert-dismissible fade show my-3 invisible"
          }
          role="alert"
        >
          <h5 className="mb-0">Incorrect Username or Password!</h5>
          <button
            id="loginFormButton"
            type="button"
            className="close"
            aria-label="Close"
            onClick={handleIncorectPasswordStateReset}
          >
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
      </form>
    </div>
  );
};

export default LoginPresentation;
