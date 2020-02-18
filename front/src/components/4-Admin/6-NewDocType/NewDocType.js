import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import DocTypeInfo from "./FormComponents/1-DocTypeInfo";
import AssignToGroups from "./FormComponents/2-AssignToGroups";
import SetRights from "./FormComponents/3-SetRights";
import AddOrRemoveButton from "./../../6-CommonElements/4-Buttons/1-AddRemove/ButtonAddOrRemove";
import CheckBox from "./../../6-CommonElements/6-CheckBox/CheckBox";
import Validation from "./../../6-CommonElements/5-FormInputValidationLine/Validation";
import axios from "axios";
import serverUrl from "./../../7-properties/1-URL";

class NewDocType extends Component {
  constructor(props) {
    super(props);
    this.state = {
      docTypeName: "",
      allGroups: [],
      notAddedGroups: [],
      addedGroups: [],
      canCreate: [],
      canSign: [],
      readyToSubmit: true
    };
  }

  cleanUpCreateAndSignLists = () => {
    this.setState({ canCreate: [], canSign: [] });
  };

  handleCreateNewDocType = event => {
    const newDocType = {
      name: this.state.docTypeName,
      creating: this.state.canCreate,
      approving: this.state.canSign
    };

    axios
      .post(serverUrl + "doct/create", newDocType)
      .then(response => {
        event.preventDefault();
        window.location.reload();
        this.props.hideNewDocType();
      })
      .catch(error => {
        console.log(error);
      });
  };

  handleDocTypeNameChange = value => {
    this.setState({ docTypeName: value });
  };

  setUpGroups = data => {
    if (data.length > 0) {
      this.parseData(data);
    }
  };

  parseData = data => {
    let tempData = data.map((item, index) => {
      return {
        number: index + 1,
        name: item.name,
        addOrRemove: (
          <AddOrRemoveButton
            itemName={item.name}
            changeAddedStatus={this.changeAddedStatus}
            added={false}
          />
        ),
        create: (
          <CheckBox
            statusChange={this.handleCreateChangeStatus}
            id={"createRightsFor:" + item.name}
            ownerName={item.name}
            checked={false}
          />
        ),
        sign: (
          <CheckBox
            statusChange={this.handleSignChangeStatus}
            id={"signRightsFor:" + item.name}
            ownerName={item.name}
            checked={false}
          />
        ),
        added: false
      };
    });

    this.filterAddedGroups(tempData);
  };

  handleCreateChangeStatus = (status, checkBoxOwnerName) => {
    let ableToCreate = this.state.canCreate;
    if (status) {
      ableToCreate.push(checkBoxOwnerName);
    } else {
      ableToCreate.splice(ableToCreate.indexOf(checkBoxOwnerName), 1);
    }
    this.setState({ canCreate: ableToCreate });
    this.validateRights(this.state.addedGroups);
  };

  handleSignChangeStatus = (status, checkBoxOwnerName) => {
    let ableToSign = this.state.canSign;
    if (status) {
      ableToSign.push(checkBoxOwnerName);
    } else {
      ableToSign.splice(ableToSign.indexOf(checkBoxOwnerName), 1);
    }
    this.setState({ canSign: ableToSign });
    this.validateRights(this.state.addedGroups);
  };

  validateRights = data => {
    if (data.length === 0) {
      this.setState({ readyToSubmit: true });
      return;
    }

    for (let i = 0; i < data.length; i++) {
      const element = data[i].name;
      if (
        this.state.canCreate.includes(element) ||
        this.state.canSign.includes(element)
      ) {
        this.setState({ readyToSubmit: true });
        continue;
      } else {
        this.setState({ readyToSubmit: false });
        return;
      }
    }
  };

  changeAddedStatus = name => {
    let tmpGroups = this.state.allGroups;
    let tmpCreate = this.state.canCreate;
    let tmpSign = this.state.canSign;
    for (let i = 0; i < tmpGroups.length; i++) {
      const element = tmpGroups[i];
      if (element.name === name) {
        tmpGroups[i].added = !tmpGroups[i].added;
        tmpGroups[i].addOrRemove = (
          <AddOrRemoveButton
            itemName={element.name}
            changeAddedStatus={this.changeAddedStatus}
            added={element.added}
          />
        );
        tmpGroups[i].create = (
          <CheckBox
            statusChange={this.handleCreateChangeStatus}
            id={"createRightsFor:" + element.name}
            ownerName={element.name}
            checked={false}
          />
        );
        tmpGroups[i].sign = (
          <CheckBox
            statusChange={this.handleSignChangeStatus}
            id={"signRightsFor:" + element.name}
            ownerName={element.name}
            checked={false}
          />
        );
        tmpCreate.splice(tmpCreate.indexOf(element.name), 1);
        tmpSign.splice(tmpSign.indexOf(element.name), 1);
      }
    }
    this.setState({ allGroups: tmpGroups });
    this.filterAddedGroups(tmpGroups);
  };

  filterAddedGroups = groupData => {
    let filterGroups = groupData;
    let notAdded = [];
    let added = [];
    for (let i = 0; i < filterGroups.length; i++) {
      const element = filterGroups[i];
      if (element.added) {
        added.push(element);
      } else {
        notAdded.push(element);
      }
    }

    this.setState({
      allGroups: groupData,
      notAddedGroups: notAdded,
      addedGroups: added
    });

    this.validateRights(added);
  };

  render() {
    return (
      <Modal
        show={this.props.showNewDocType}
        onHide={this.props.hideNewDocType}
        size={"lg"}
        id="newDocTypeModal"
      >
        <Modal.Header closeButton>
          <Modal.Title>New Document Type</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={this.handleCreateNewDocType}>
            <DocTypeInfo
              docTypeValue={this.state.docTypeName}
              handleDocTypeNameChange={this.handleDocTypeNameChange}
            />
            <hr />
            <AssignToGroups
              tableData={this.state.notAddedGroups}
              setUpGroups={this.setUpGroups}
              cleanUpCreateAndSignLists={this.cleanUpCreateAndSignLists}
            />
            <hr />
            <SetRights addedGroups={this.state.addedGroups} />
            <Validation
              output={
                'All groups must have their rights set to "Sign",  "Create" or both. If not, please remove them from this list.'
              }
              satisfied={this.state.readyToSubmit}
            />
            <div className="form-group row d-flex justify-content-center">
              <div className="modal-footer ">
                <button
                  type="button"
                  className="btn btn-outline-dark"
                  onClick={this.props.hideNewDocType}
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="btn btn-dark"
                  data-dismiss="modal"
                  disabled={this.state.readyToSubmit ? false : true}
                >
                  Create
                </button>
              </div>
            </div>
          </form>
        </Modal.Body>
      </Modal>
    );
  }
}

export default NewDocType;
