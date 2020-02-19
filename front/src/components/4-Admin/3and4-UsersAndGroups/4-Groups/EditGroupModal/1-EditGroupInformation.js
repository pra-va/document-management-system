import React, { Component } from "react";
import InputLine from "../../../../6-CommonElements/3-FormSingleInput/FormSingleInput";
import axios from "axios";
import serverUrl from "../../../../7-properties/1-URL";
import FormValidation from "../../../../6-CommonElements/5-FormInputValidationLine/Validation";

class GroupInformation extends Component {
  constructor(props) {
    super(props);
    this.state = {
      groupName: "",
      description: "",
      usersList: "",
      groupNameExists: false,
      descriptionValidation: true
    };
  }

  handleGroupNameChange = event => {
    this.props.handleGroupNameChange(event);
    if (event.target.value.length > 0) {
      axios
        .get(serverUrl + "group/" + event.target.value + "/exists")
        .then(response => {
          this.setSate({ groupNameExists: response.data });
          if (response.data && event.target.value !== this.props.groupName) {
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

  handleGroupDescriptionChange = event => {
    this.props.handleGroupDescriptionChange(event);
    this.handleDescriptionValidation(event.target.value.length);
  };

  handleDescriptionValidation = descriptionLength => {
    if (descriptionLength > 500) {
      this.setState({ descriptionValidation: false });
    } else {
      this.setState({ descriptionValidation: true });
    }
  };
  rednder() {
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
          onChange={this.handleGroupNameChange}
          value={this.props.groupName}
          pattern={4}
          groupNameExists={this.state.groupNameExists}
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
              onChange={this.handleGroupDescriptionChange}
              value={this.props.groupDescription}
            ></textarea>
            <FormValidation
              satisfied={this.state.descriptionValidation}
              output={
                "Group description can not be longer than 500 characters."
              }
            />
          </div>
        </div>
      </div>
    );
  }
}
export default GroupInformation;
