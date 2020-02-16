import React from "react";
import Input from "./../../../../6-CommonElements/3-FormSingleInput/FormSingleInput";
import Validation from "./../../../../6-CommonElements/5-FormInputValidationLine/Validation";

const DocTypeInfo = props => {
  const handleDocTypeChange = event => {
    if (props.docTypeUnique) {
      event.target.setCustomValidity("Document Type name must be unique.");
    } else {
      event.target.setCustomValidity("");
    }
    props.handleDocTypeNameChange(event.target.value);
  };

  return (
    <div>
      <h3 className="d-flex justify-content-start">1. Enter information.</h3>
      <Input
        id={"groupNameInput"}
        labelName={"Document type name:"}
        required={true}
        type={"text"}
        placeholder={"Vacation request"}
        onChange={handleDocTypeChange}
        value={props.docTypeValue}
        pattern={4}
      />
      <div className="row mt-0 pt-0">
        <div className="col-2"></div>
        <div className="col-10">
          <Validation
            output="Field must be unique."
            satisfied={!props.docTypeUnique}
          />
        </div>
      </div>
    </div>
  );
};

export default DocTypeInfo;
