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
import { Switch, Route } from "react-router";
import { BrowserRouter } from "react-router-dom";

// Other imports
import App from "./components/00-MainWindow/App";
import PageNotFound from "./components/01-Errors/PageNotFound";
import LoginPicker from "./components/02-LoginPicker/LoginPicker";
import LoginForm from "./components/03-LoginForm/LoginForm";
import HomePage from "./components/04-Homepage/HomePage";

ReactDOM.render(
  <BrowserRouter>
    <App>
      <Switch>
        <Route exact path="/" component={LoginPicker} />
        <Route exact path="/login" component={LoginForm} />
        <Route exact path="/home" component={HomePage} />

        <Route path="*" component={PageNotFound} />
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
