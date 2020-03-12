import React from "react";
import "./Alert.css";

// index={...}, text={"..."}
const Alert = props => {
  const number = props.index;

  return (
    <div
      className="alert alert-dark alert-dismissible fade show custom-alert"
      role={props.index + props.text}
    >
      {props.text}
      <button
        id={props.index}
        type="button"
        className="close"
        data-dismiss={props.index + props.text}
        aria-label="Close"
        onClick={() => {
          props.execute(number);
        }}
      >
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
  );
};

export default Alert;
