import React, { useState } from "react";
import InputLine from "./../../../6-CommonElements/3-FormSingleInput/FormSingleInput";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";
import FormValidation from "./../../../6-CommonElements/5-FormInputValidationLine/Validation";

var GroupInformation = props => {
  var [groupNameExists, setGroupNameExists] = useState(false);
  var [descriptionValidation, setDescriptionValidation] = useState(true);

  const handleGroupNameChange = event => {
    event.persist();
    props.handleGroupNameChange(event);
    if (event.target.value.length > 0) {
      axios
        .get(serverUrl + "group/" + event.target.value + "/exists")
        .then(response => {
          setGroupNameExists(response.data);
          if (response.data) {
            event.target.setCustomValidity("Group name is taken.");
          } else {
            event.target.setCustomValidity("");
          }
        })
        .catch(error => {
          console.log(error);
        });
    }
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
        type={"text"}
        placeholder={"Junior Java Programmers"}
        onChange={handleGroupNameChange}
        value={props.groupName}
        pattern={4}
        groupNameExists={groupNameExists}
      />

      <div className="form-group row mt-3">
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
