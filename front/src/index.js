// React starter imports
import React from "react";
import ReactDOM from "react-dom";
import "./index.css";

import * as serviceWorker from "./serviceWorker";

// Bootstrap imports
import "bootstrap/dist/css/bootstrap.min.css";
// Uncomment if needed for additional bootstrap functionallity.
// import $ from "jquery";
import "bootstrap/dist/js/bootstrap.bundle.min";

// Router imports
// import { Switch, Route } from "react-router";
// import { BrowserRouter } from "react-router-dom";

import { HashRouter as Router, Route, BrowserRouter } from "react-router-dom";
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

ReactDOM.render(
  // <BrowserRouter>
  //   <App>
  //     <Switch>
  <BrowserRouter>
    <Router>
      <App>
        <Switch>
          <Route exact path="/" component={LoginForm} />
          <Route exact path="/home" component={HomePage} />
          <Route exact path="/users" component={ListOfUsers} />
          <Route exact path="/groups" component={ListOfGroups} />
          <Route path="*" component={PageNotFound} />
          <Route component={PageNotFound} />
        </Switch>
      </App>
    </Router>
  </BrowserRouter>,
  //     </Switch>
  //   </App>
  // </BrowserRouter>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
