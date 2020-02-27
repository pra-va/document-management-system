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
      descriptionValidation: true,
      canCreate: [],
      canSign: []
    };
  }

  componentDidMount() {
    this.getGroupData();
  }

  componentDidUpdate() {}

  getGroupData = () => {
    axios
      .get(serverUrl + "groups/" + this.props.ownerName)
      .then(response => {
        console.log(response.data);
        this.setState({
          groupName: response.data.name,
          description: response.data.description,
          groupUsers: response.data.userList,
          canCreate: response.data.docTypesToCreateNames,
          canSign: response.data.docTypesToApproveNames
        });
        this.props.initalDataTransfer({
          groupName: response.data.name,
          groupDescription: response.data.description,
          groupUsers: response.data.userList,
          canCreate: response.data.docTypesToCreateNames,
          canSign: response.data.docTypesToApproveNames
        });
        this.props.setGroupUsers(response.data.userList);
        this.props.setCanCreate(response.data.docTypesToCreateNames);
        this.props.setCanCreate(response.data.docTypesToApproveNames);
      })
      .catch(error => console.log(error));
  };

  handleGroupNameChange = event => {
    event.persist();
    this.props.handleGroupNameChange(event.target.value);
    if (event.target.value.length > 0) {
      let tmpValue = this.isGroupNameTaken(event.target.value);
      this.setState({ groupNameExists: tmpValue });
      if (tmpValue && event.target.value !== this.props.ownerName) {
        event.target.setCustomValidity("Group name is taken.");
      } else {
        event.target.setCustomValidity("");
      }
      this.props.handleGroupNameChange(event.target.value);
    }
  };

  isGroupNameTaken = title => {
    axios
      .get(serverUrl + "group/" + title + "/exists")
      .then(response => {
        return response.data;
      })
      .catch(error => {
        console.log(error);
      });
  };

  handleGroupDescriptionChange = event => {
    this.setState({ description: event.target.value });
    this.handleDescriptionValidation(event.target.value.length);
    this.props.handleGroupDescriptionChange(event.target.value);
  };

  handleDescriptionValidation = descriptionLength => {
    if (descriptionLength > 500) {
      this.setState({ descriptionValidation: false });
    } else {
      this.setState({ descriptionValidation: true });
    }
  };

  render() {
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
          placeholder={"Enter new name of this Group"}
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
              value={this.state.description}
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
