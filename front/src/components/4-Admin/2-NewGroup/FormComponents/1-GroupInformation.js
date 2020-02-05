import React from "react";
import InputLine from "./../../../6-CommonElements/3-FormSingleInput/FormSingleInput";

var GroupInformation = props => {
  const handleGroupNameChange = event => {
    props.handleGroupNameChange(event);
  };

  const handleGroupDescriptionChange = event => {
    props.handleGroupDescriptionChange(event);
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
            rows="3"
            onChange={handleGroupDescriptionChange}
            value={props.groupDescription}
          ></textarea>
        </div>
      </div>
    </div>
  );
};

export default GroupInformation;
