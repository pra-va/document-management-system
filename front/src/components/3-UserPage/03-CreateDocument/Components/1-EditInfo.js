import React from "react";
import Input from "./../../../6-CommonElements/3-FormSingleInput/FormSingleInput";
import Validation from "./../../../6-CommonElements/5-FormInputValidationLine/Validation";

const EditInfo = props => {
  const handleNameChange = event => {
    props.handleNameChange(event.target.value);
  };

  const handleDescriptionChange = event => {
    props.handleDescriptionChange(event.target.value);
  };

  return (
    <div>
      <h3 className="d-flex justify-content-start">
        1. Enter document information.
      </h3>

      <Input
        id={"inputDocName"}
        labelName={"Document name:"}
        required={true}
        type={"text"}
        placeholder={"Quit Job Application"}
        onChange={handleNameChange}
        value={props.name}
        pattern={4}
      />

      <div className="form-group row mt-3">
        <label
          htmlFor="inputGroupDescription"
          className="col-sm-2 col-form-label"
        >
          Document description:
        </label>
        <div className="col-sm-10">
          <textarea
            className="form-control"
            id="inputDocDescription"
            maxLength="500"
            rows="3"
            onChange={handleDescriptionChange}
            value={props.description}
          ></textarea>
          <Validation
            satisfied={true}
            output={
              "Document description can not be longer than 500 characters."
            }
          />
        </div>
      </div>
    </div>
  );
};

export default EditInfo;
