import React from "react";
import "./App.css";
import RoleContext from "../5-Context/UserRole";

function App(props) {
  return (
    <div className="App">
      <RoleContext.Provider value={{ role: "" }}>
        {props.children}
      </RoleContext.Provider>
    </div>
  );
}

export default App;
