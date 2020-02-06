import React from "react";
import Validation from "./../5-FormInputValidationLine/Validation";

// PROPS

// id={""} labelName={""} required={true/false} type={""} placeholder={""} onChange={} value={}
// pattern={0/1/2} ([0] - password, [1] - no space, [2] - with space)
var InputLine = props => {
  // [0] - password, [1] - no space, [2] - with space
  const patternTypes = [
    "[A-Za-z0-9]{8,20}",
    "[a-zA-Z0-9]{1,20}",
    "[a-zA-Z0-9 ]{1,20}"
  ];

  var passwordLength = () => {
    if (props.pattern === 0) {
      return (
        <Validation
          output={"Password must be between 8 and 20 characters long."}
          satisfied={
            props.value.length > 7 && props.value.length < 21 ? true : false
          }
        />
      );
    }
  };

  var fieldNotEmpty = () => {
    if (
      props.pattern !== 0 &&
      !props.labelName.toLowerCase().includes("username")
    ) {
      return (
        <Validation
          output={"Field can not be empty and longer than 20 characters long."}
          satisfied={
            props.value.length > 0 && props.value.length < 21 ? true : false
          }
        />
      );
    }
  };

  var usernameLength = () => {
    if (props.labelName.toLowerCase().includes("username")) {
      return (
        <Validation
          output={"Username must be between 4 and 20 characters long."}
          satisfied={
            props.value.length > 3 && props.value.length < 21 ? true : false
          }
        />
      );
    }
  };

  var usernameExists = () => {
    if (props.labelName.toLowerCase().includes("username")) {
      return (
        <Validation
          output={"Username must be unique."}
          satisfied={!props.usernameExists}
        />
      );
    }
  };

  var groupNameExists = () => {
    if (props.labelName.toLowerCase().includes("group name")) {
      return (
        <Validation
          output={"Group name must be unique."}
          satisfied={!props.groupNameExists}
        />
      );
    }
  };

  var oneWordAllowed = () => {
    return (
      <Validation
        output={"Field must contain 1 word."}
        satisfied={!props.value.includes(" ") && props.value.length > 0}
      />
    );
  };

  return (
    <div className="form-group row">
      <label htmlFor={props.id} className="col-sm-2 col-form-label">
        {props.labelName}
      </label>
      <div className="col-sm-10">
        <input
          autoComplete="on"
          required={props.required ? true : false}
          type={props.type}
          className="form-control"
          id={props.id}
          placeholder={props.placeholder}
          onChange={props.onChange}
          value={props.value}
          pattern={patternTypes[props.pattern]}
        />
        {passwordLength()}
        {fieldNotEmpty()}
        {usernameLength()}
        {usernameExists()}
        {oneWordAllowed()}
        {groupNameExists()}
      </div>
    </div>
  );
};

export default InputLine;
