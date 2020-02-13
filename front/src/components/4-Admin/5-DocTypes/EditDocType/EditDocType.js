import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import DocTypeInfo from "./FormComponents/1-DocTypeInfo";
import AssignToGroups from "./FormComponents/2-AssignToGroups";
import SetRights from "./FormComponents/3-SetRights";
import AddOrRemoveButton from "./../../../6-CommonElements/4-Buttons/1-AddRemove/ButtonAddOrRemove";
import CheckBox from "./../../../6-CommonElements/6-CheckBox/CheckBox";
import Validation from "./../../../6-CommonElements/5-FormInputValidationLine/Validation";
// import axios from "axios";
// import serverUrl from "./../../7-properties/1-URL";

class NewDocType extends Component {
  constructor(props) {
    super(props);
    this.state = {
      docTypeName: "",
      notAddedGroups: [],
      allGroups: [],
      addedGroups: [],
      canCreate: [],
      canSign: [],
      readyToSubmit: true
    };
  }

  cleanUpCreateAndSignLists = () => {
    this.setState({ canCreate: [], canSign: [] });
  };

  handleSubmit = event => {
    // const newDocType = {
    //   docTypeName: this.state.docTypeName,
    //   canCreate: this.state.canCreate,
    //   canSign: this.state.canSign
    // };

    // axios
    //   .post(serverUrl + "/doctype", newDocType)
    //   .then(response => {
    //     event.preventDefault();
    //     window.location.reload();
    //     this.props.hideNewDocType();
    //   })
    //   .catch(error => {
    //     console.log(error);
    //   });

    event.preventDefault();
    window.location.reload();
    this.props.hideNewDocType();
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
          />
        ),
        sign: (
          <CheckBox
            statusChange={this.handleSignChangeStatus}
            id={"signRightsFor:" + item.name}
            ownerName={item.name}
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
    this.validateRights();
  };

  handleSignChangeStatus = (status, checkBoxOwnerName) => {
    let ableToSign = this.state.canSign;
    if (status) {
      ableToSign.push(checkBoxOwnerName);
    } else {
      ableToSign.splice(ableToSign.indexOf(checkBoxOwnerName), 1);
    }
    this.setState({ canSign: ableToSign });
    this.validateRights();
  };

  validateRights = () => {
    if (this.state.addedGroups.length === 0) {
      this.setState({ readyToSubmit: false });
      return;
    }

    for (let i = 0; i < this.state.addedGroups.length; i++) {
      const element = this.state.addedGroups[i].name;
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
        this.setState({ allGroups: tmpGroups });
        this.filterAddedGroups(tmpGroups);
      }
    }
    this.validateRights();
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
  };

  render() {
    return (
      <Modal
        show={this.props.modalState}
        onHide={this.props.hideModal}
        size={"lg"}
        id="newDocTypeModal"
      >
        <Modal.Header closeButton>
          <Modal.Title>New Document Type</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={this.handleSubmit}>
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
                  onClick={this.props.hideModal}
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="btn btn-dark"
                  data-dismiss="modal"
                  disabled={this.state.readyToSubmit ? false : true}
                >
                  Submit
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
