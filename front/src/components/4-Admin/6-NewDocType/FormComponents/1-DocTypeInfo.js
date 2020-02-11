import React from "react";
import Input from "./../../../6-CommonElements/3-FormSingleInput/FormSingleInput";

const DocTypeInfo = props => {
  const handleDocTypeChange = event => {
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
    </div>
  );
};

export default DocTypeInfo;
