import React, { Component } from "react";
import NotAddedDocTypes from "./Components/1-NotAddedDocTypes";
import AddedDocTypes from "./Components/2-AddedDocTypes";
import GroupLogo from "./../../../resources/team.svg";
import AddOrRemoveButton from "./../4-Buttons/1-AddRemove/ButtonAddOrRemove";
import PopOver from "./../8-PopOver/PopOver";
import CheckBox from "./../6-CheckBox/CheckBox";
import Validation from "./../5-FormInputValidationLine/Validation";

// readyToSubmit={function(readyToSubmitStatus)}
class AddRemoveDocType extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allDocTypes: [],
      tableData: [],
      notAddedDocTypes: [],
      addedDocTypes: [],
      canCreate: [],
      canSign: [],
      readyToSubmit: false
    };
  }

  parseData = data => {
    if (data) {
      const allDocTypes = data.map((item, index) => {
        return {
          number: index + 1,
          name: item.name,
          canCreate: (
            <PopOver
              popOverApparance={
                <img src={GroupLogo} alt="unable to load" className="invert" />
              }
              popOverTitle={"Groups with Create rights:"}
              popOverContent={
                item.groupsToCreate.length > 0
                  ? this.reduceList(item.creating)
                  : "None"
              }
            />
          ),
          canSign: (
            <PopOver
              popOverApparance={
                <img src={GroupLogo} alt="unable to load" className="invert" />
              }
              popOverTitle={"Groups with Sign rights:"}
              popOverContent={
                item.groupsToApprove.length > 0
                  ? this.reduceList(item.approving)
                  : "None"
              }
            />
          ),
          edit: (
            <AddOrRemoveButton
              itemName={item.name}
              changeAddedStatus={this.changeAddedStatus}
              added={false}
            />
          ),
          added: false,
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
          )
        };
      });
      this.filterDocTypes(allDocTypes);
    }
  };

  filterDocTypes = docTypesData => {
    let docTypesToFilter = docTypesData;
    let notAdded = [];
    let added = [];
    for (let i = 0; i < docTypesToFilter.length; i++) {
      const element = docTypesToFilter[i];
      if (element.added) {
        added.push(element);
      } else {
        notAdded.push(element);
      }
    }

    this.setState({
      allDocTypes: docTypesData,
      notAddedDocTypes: notAdded,
      addedDocTypes: added
    });

    this.validateRights(added);
  };

  changeAddedStatus = name => {
    let tmp = this.state.allDocTypes;
    for (let i = 0; i < tmp.length; i++) {
      const element = tmp[i];
      if (element.name === name) {
        tmp[i].added = !tmp[i].added;
        tmp[i].addOrRemove = (
          <AddOrRemoveButton
            itemName={element.name}
            changeAddedStatus={this.changeAddedStatus}
            added={element.added}
          />
        );
      }
    }

    this.filterDocTypes(tmp);
  };

  validateRights = data => {
    if (data.length === 0) {
      this.setState({ readyToSubmit: true });
      this.props.readyToSubmit(true);
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
        this.props.readyToSubmit(false);
        return;
      }
    }
    this.props.readyToSubmit(true);
  };

  reduceList = data => {
    if (data) {
      let reducedList = data.reduce((sum, item, index) => {
        if (index === 0) {
          return sum + item;
        } else {
          return sum + ", " + item;
        }
      });
      reducedList += ".";
      return reducedList;
    }
  };

  handleCreateChangeStatus = (status, checkBoxOwnerName) => {
    let ableToCreate = this.state.canCreate;
    if (status) {
      ableToCreate.push(checkBoxOwnerName);
    } else {
      if (ableToCreate.indexOf(checkBoxOwnerName) !== -1) {
        ableToCreate.splice(ableToCreate.indexOf(checkBoxOwnerName), 1);
      }
    }
    this.setState({ canCreate: ableToCreate });
    this.props.canCreate(ableToCreate);
    this.validateRights(this.state.addedDocTypes);
  };

  handleSignChangeStatus = (status, checkBoxOwnerName) => {
    let ableToSign = this.state.canSign;
    if (status) {
      ableToSign.push(checkBoxOwnerName);
    } else {
      if (ableToSign.indexOf(checkBoxOwnerName) !== -1) {
        ableToSign.splice(ableToSign.indexOf(checkBoxOwnerName), 1);
      }
    }
    this.setState({ canSign: ableToSign });
    this.props.canSign(ableToSign);
    this.validateRights(this.state.addedDocTypes);
  };

  doCleanUp = () => {
    this.setState({ create: [], sign: [] });
  };

  render() {
    return (
      <div>
        <NotAddedDocTypes
          notAddedDocTypes={this.state.notAddedDocTypes}
          doCleanUp={this.doCleanUp}
          fetchServerData={this.parseData}
        />
        <hr className="m-1" />
        <AddedDocTypes addedDocTypes={this.state.addedDocTypes} />
        <Validation
          output={
            "All added document types should have their rights set. Please remove them otherwise."
          }
          satisfied={this.state.readyToSubmit}
        />
      </div>
    );
  }
}

export default AddRemoveDocType;
