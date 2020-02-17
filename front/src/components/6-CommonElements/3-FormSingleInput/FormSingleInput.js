import React from "react";
import Validation from "./../5-FormInputValidationLine/Validation";

// PROPS

// id={""} labelName={""} required={true/false} type={""} placeholder={""} onChange={} value={}
// pattern={0/1/2} ([0] - password, [1] - no space, [2] - with space, [3] - username, [4] - longer name with special characters)
var InputLine = props => {
  // [0] - password, [1] - no space, [2] - with space, [3] - username, [4] - longer name with special characters
  const patternTypes = [
    "[^' ']{8,20}",
    "[^' ']{1,20}",
    "^.{1,20}$",
    "[a-zA-Z0-9]{4,20}",
    "^.{1,50}$"
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

  var noSpecialCharactersAllowed = () => {
    if (props.labelName.toLowerCase().includes("username")) {
      let checkLetter = e => {
        var k = e.charCodeAt(0);
        return (
          (k > 64 && k < 91) ||
          (k > 96 && k < 123) ||
          k === 8 ||
          k === 32 ||
          (k >= 48 && k <= 57)
        );
      };

      if (props.value) {
        for (let i = 0; i < props.value.length; i++) {
          const element = props.value[i];
          if (!checkLetter(element)) {
            return (
              <Validation
                output={"No special characters allowed."}
                satisfied={false}
              />
            );
          }
        }
        return (
          <Validation
            output={"No special characters allowed."}
            satisfied={true}
          />
        );
      } else {
        return (
          <Validation
            output={"No special characters allowed."}
            satisfied={true}
          />
        );
      }
    }
  };

  var fieldNotEmpty = () => {
    if (
      (props.pattern !== 0 || props.patter === 4) &&
      !props.labelName.toLowerCase().includes("username") &&
      !props.labelName.toLowerCase().includes("group name")
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

  var longerName = () => {
    if (props.pattern === 4) {
      return (
        <Validation
          output={"Field must be between 1 and 50 characters long."}
          satisfied={
            props.value.length > 0 && props.value.length < 51 ? true : false
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
    if (
      !props.labelName.toLowerCase().includes("group name") &&
      !props.labelName.toLowerCase().includes("first name") &&
      !props.labelName.toLowerCase().includes("last name") &&
      !props.labelName.toLowerCase().includes("document type name")
    ) {
      return (
        <Validation
          output={"Field must contain 1 word."}
          satisfied={!props.value.includes(" ") && props.value.length > 0}
        />
      );
    }
  };

  return (
    <div className="form-group row mb-0 mt-3">
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
        {longerName()}
        {groupNameExists()}
        {noSpecialCharactersAllowed()}
      </div>
    </div>
  );
};

export default InputLine;
