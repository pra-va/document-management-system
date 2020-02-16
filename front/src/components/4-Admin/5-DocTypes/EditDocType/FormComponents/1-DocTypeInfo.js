import React, { useState } from "react";
import Input from "./../../../../6-CommonElements/3-FormSingleInput/FormSingleInput";
import Validation from "./../../../../6-CommonElements/5-FormInputValidationLine/Validation";
import axios from "axios";
import serverUrl from "./../../../../7-properties/1-URL";

const DocTypeInfo = props => {
  var [docTypeNameExists, setDocTypeNameExists] = useState(false);

  const handleDocTypeChange = event => {
    event.persist();
    props.handleDocTypeNameChange(event.target.value);
    axios
      .get(serverUrl + "doct/" + event.target.value + "/exists")
      .then(response => {
        setDocTypeNameExists(response.data);
        if (response.data && response.data !== props.owner) {
          event.target.setCustomValidity("Document Type name must be unique.");
        } else {
          event.target.setCustomValidity("");
        }
      })
      .catch(error => {
        console.log(error);
      });
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
            satisfied={!docTypeNameExists}
          />
        </div>
      </div>
    </div>
  );
};

export default DocTypeInfo;
