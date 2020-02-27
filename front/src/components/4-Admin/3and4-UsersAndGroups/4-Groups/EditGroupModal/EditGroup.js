import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import GroupInformation from "./1-EditGroupInformation";
import AddUsersToGroup from "./2-AddUsersToGroup";
import GroupUsers from "./3-GroupUsers";
import axios from "axios";
import AddRemoveButton from "./../../../../6-CommonElements/4-Buttons/1-AddRemove/ButtonAddOrRemove";
import serverUrl from "./../../../../7-properties/1-URL";
import AddRemoveDocTypes from "./4-AddRemoveDocType";

class EditGroup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      groupName: "",
      groupDescription: "",
      groupUsers: [],
      notAddedUsers: [],
      addedUsers: [],
      readyToSubmit: true,
      canCreate: [],
      canSign: []
    };
  }

  componentDidMount() {}

  componentDidUpdate() {}

  setUpData = data => {
    this.parseUsersData(data);
  };

  handleGroupNameChange = value => {
    this.setState({ groupName: value });
  };

  handleGroupDescriptionChange = value => {
    this.setState({ groupDescription: value });
  };

  parseUsersData = data => {
    let tmpUsersData = data.map((item, index) => {
      return {
        number: index + 1,
        name: item.name,
        surname: item.surname,
        username: item.username,
        role: item.role,
        add: (
          <AddRemoveButton
            itemName={item.username}
            added={false}
            changeAddedStatus={this.changeAddedStatus}
          />
        ),
        added: false
      };
    });

    this.filterAddedGroups(tmpUsersData);
  };

  changeAddedStatus = username => {
    let tmpUsers = this.state.groupUsers;
    for (let i = 0; i < tmpUsers.length; i++) {
      const element = tmpUsers[i];
      if (element.username === username) {
        tmpUsers[i].added = !tmpUsers[i].added;
        tmpUsers[i].add = (
          <AddRemoveButton
            itemName={element.username}
            added={element.added}
            changeAddedStatus={this.changeAddedStatus}
          />
        );
      }
    }
    this.filterAddedGroups(tmpUsers);
  };

  setGroupUsers = groupUsers => {
    this.setState({ groupUsers: groupUsers });
  };

  setAddedUsers = addedUsers => {
    this.setState({ addedUsers: addedUsers });
  };

  filterAddedGroups = data => {
    let filterUsers = data;
    let tmpNotAdded = [];
    let tmpAdded = [];
    for (let i = 0; i < filterUsers.length; i++) {
      const element = filterUsers[i];
      if (element.added) {
        tmpAdded.push(element);
      } else {
        tmpNotAdded.push(element);
      }
    }
    this.setState({
      groupUsers: data,
      notAddedUsers: tmpNotAdded,
      addedUsers: tmpAdded
    });
  };

  readyToSubmit = readyToSubmit => {
    this.setState({ readyToSubmit: readyToSubmit });
  };

  canCreate = data => {
    this.setState({ canCreate: data });
  };

  canSign = data => {
    this.setState({ canSign: data });
  };

  initalDataTransfer = data => {
    this.setState({ ...data });
  };

  handleSubmit = event => {
    event.preventDefault();

    const editedGroup = {
      description: this.state.groupDescription,
      docTypesToCreate: this.state.canCreate,
      docTypesToApprove: this.state.canSign,
      newName: this.state.groupName,
      userList: this.state.addedUsers.map(item => {
        return item.username;
      })
    };
    console.log("SUBMITING");
    console.log(editedGroup);
    axios
      .post(serverUrl + "groups/update/" + this.props.ownerName, editedGroup)
      .then(response => {
        console.log(response);
        /*window.location.reload();
        this.props.onHide();*/
      })
      .catch(error => console.log(error));
  };

  render() {
    return (
      <Modal
        show={this.props.show}
        onHide={this.props.onHide}
        size={"lg"}
        id="editGroupModal"
      >
        <Modal.Header closeButton>
          <Modal.Title>Edit Group</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={this.handleSubmit}>
            <GroupInformation
              ownerName={this.props.ownerName}
              handleGroupNameChange={this.handleGroupNameChange}
              handleGroupDescriptionChange={this.handleGroupDescriptionChange}
              groupName={this.state.groupName}
              groupDescription={this.state.groupDescription}
              setGroupUsers={this.setGroupUsers}
              setCanCreate={this.canCreate}
              setCanSign={this.canSign}
              initalDataTransfer={this.initalDataTransfer}
            />
            <hr className="m-1" />
            <AddUsersToGroup
              groupUsers={this.state.groupUsers}
              setAddedUsers={this.setAddedUsers}
              //setUpData={this.setUpData}
              notAddedUsers={this.state.notAddedUsers}
            />
            <hr className="m-1" />
            <GroupUsers groupUsers={this.state.addedUsers} />
            <hr className="m-1" />

            <AddRemoveDocTypes
              readyToSubmit={this.readyToSubmit}
              canCreate={this.canCreate}
              canSign={this.canSign}
            />

            <div className="form-group row d-flex justify-content-center">
              <div className="modal-footer ">
                <button
                  type="button"
                  className="btn btn-outline-dark"
                  onClick={this.props.onHide}
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="btn btn-dark"
                  data-dismiss="modal"
                  disabled={this.state.readyToSubmit ? false : true}
                >
                  Save
                </button>
              </div>
            </div>
          </form>
        </Modal.Body>
      </Modal>
    );
  }
}

export default EditGroup;
