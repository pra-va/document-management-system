import React from "react";

// output={""} satisfied={true/false/condition that returns true/false}
const InputValidation = props => {
  return (
    <small
      className={
        props.satisfied
          ? "form-text text-muted text-left .text-muted"
          : "form-text text-muted text-left .text-danger"
      }
    >
      {props.satisfied ? (
        <span role="img" aria-label="Condition met.">
          &#10004;
        </span>
      ) : (
        <span role="img" aria-label="Condition not met.">
          &#10060;
        </span>
      )}
      {" " + props.output}
    </small>
  );
};

export default InputValidation;
