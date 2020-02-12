import React, { useState } from "react";
import InputLine from "./../../../6-CommonElements/3-FormSingleInput/FormSingleInput";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";
import FormValidation from "./../../../6-CommonElements/5-FormInputValidationLine/Validation";

var GroupInformation = props => {
  var [groupNameExists, setGroupNameExists] = useState(false);
  var [descriptionValidation, setDescriptionValidation] = useState(true);

  const checkIfGroupNameExists = groupName => {
    if (groupName.length > 0) {
      axios
        .get(serverUrl + "group/" + groupName + "/exists")
        .then(response => {
          setGroupNameExists(response.data);
        })
        .catch(error => {
          console.log(error);
        });
    }
  };

  const handleGroupNameChange = event => {
    props.handleGroupNameChange(event);
    checkIfGroupNameExists(event.target.value);
  };

  const handleGroupDescriptionChange = event => {
    props.handleGroupDescriptionChange(event);
    handleDescriptionValidation(event.target.value.length);
  };

  const handleDescriptionValidation = descriptionLength => {
    if (descriptionLength > 500) {
      setDescriptionValidation(false);
    } else {
      setDescriptionValidation(true);
    }
  };

  return (
    <div>
      <h3 className="d-flex justify-content-start">
        1. Enter new group information.
      </h3>

      <InputLine
        id={"inputGroupName"}
        labelName={"Group name:"}
        required={true}
        asd
        type={"text"}
        placeholder={"Junior Java Programmers"}
        onChange={handleGroupNameChange}
        value={props.groupName}
        pattern={4}
        groupNameExists={groupNameExists}
      />

      <div className="form-group row">
        <label
          htmlFor="inputGroupDescription"
          className="col-sm-2 col-form-label"
        >
          Group description:
        </label>
        <div className="col-sm-10">
          <textarea
            className="form-control"
            id="inputGroupDescription"
            maxLength="500"
            rows="3"
            onChange={handleGroupDescriptionChange}
            value={props.groupDescription}
          ></textarea>
          <FormValidation
            satisfied={descriptionValidation}
            output={"Group description can not be longer than 500 characters."}
          />
        </div>
      </div>
    </div>
  );
};

export default GroupInformation;
