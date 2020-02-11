import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import DocTypeInfo from "./FormComponents/1-DocTypeInfo";
import AssignToGroups from "./FormComponents/2-AssignToGroups";
import SetRights from "./FormComponents/3-SetRights";
import AddOrRemoveButton from "./../../6-CommonElements/4-Buttons/1-AddRemove/ButtonAddOrRemove";
import CheckBox from "./../../6-CommonElements/6-CheckBox/CheckBox";
import Validation from "./../../6-CommonElements/5-FormInputValidationLine/Validation";

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
      readyToSubmit: true,
      showModal: this.props.showNewDocType
    };
  }

  handleCreateNewDocType = event => {
    event.preventDefault();
    console.log("create new doc type submit");
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
      console.log("can create: " + this.state.canCreate.includes(element));
      console.log("can sign: " + this.state.canSign.includes(element));
      if (
        this.state.canCreate.includes(element) ||
        this.state.canSign.includes(element)
      ) {
        this.setState({ readyToSubmit: true });
        continue;
      } else {
        this.setState({ readyToSubmit: false });
        console.log("unable to create");
        return;
      }
    }

    console.log("able to create");
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
    console.log("change added status");
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
            />
            <hr />
            <SetRights addedGroups={this.state.addedGroups} />
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
                  disabled={this.state.readyToSubmit ? true : false}
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
