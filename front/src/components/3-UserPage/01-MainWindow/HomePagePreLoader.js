import React, { useContext } from "react";
import RoleContext from "../../5-Context/UserRole";
import HomePage from "./HomePage";

var ContextReciever = props => {
  const roleContext = useContext(RoleContext);
  roleContext.role = props.history.location.state.isUserAdmin;

  return (
    <div>
      <HomePage role={roleContext.role} />
    </div>
  );
};

export default ContextReciever;
