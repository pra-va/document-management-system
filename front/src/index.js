// React starter imports
import React from "react";
import ReactDOM from "react-dom";
import "./index.css";

import * as serviceWorker from "./serviceWorker";

// Bootstrap imports
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";

// Router imports
import { Route, BrowserRouter } from "react-router-dom";
import { Switch } from "react-router";

//Table imports
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css";

// Other imports
import App from "./components/0-MainWindow/App";
import PageNotFound from "./components/1-Errors/PageNotFound";
import LoginForm from "./components/2-LoginForm/LoginForm";
import HomePage from "./components/3-UserPage/01-MainWindow/HomePage";
import ListOfUsers from "./components/4-Admin/3and4-UsersAndGroups/3-Users/ListOfUsers";
import ListOfGroups from "./components/4-Admin/3and4-UsersAndGroups/4-Groups/ListOfGroups";
import DocTypes from "./components/4-Admin/5-DocTypes/DocTypes";

ReactDOM.render(
  <BrowserRouter>
    <App>
      <Switch>
        <Route exact path="/dvs/" component={LoginForm} />
        <Route exact path="/dvs/home" component={HomePage} />
        <Route exact path="/dvs/users" component={ListOfUsers} />
        <Route exact path="/dvs/groups" component={ListOfGroups} />
        <Route exact path="/dvs/doctypes" component={DocTypes} />
        <Route exact path="/dvs/notfound" component={PageNotFound} />
        <Route path="/dvs/**" component={PageNotFound} />
        <Route component={PageNotFound} />
      </Switch>
    </App>
  </BrowserRouter>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
