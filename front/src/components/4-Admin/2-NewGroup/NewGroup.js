import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import GroupInformation from "./FormComponents/1-GroupInformation";
import AddUsersToGroup from "./FormComponents/2-AddUsersToGroup";
import GroupUsers from "./FormComponents/3-GroupUsers";
import AddDocumentTypes from "./FormComponents/4-AddDocumentTypes";
import SetRights from "./FormComponents/5-SetRights";
import axios from "axios";
import AddRemoveButton from "./../../6-CommonElements/4-Buttons/1-AddRemove/ButtonAddOrRemove";

class NewGroup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      groupName: "",
      groupDescription: "",
      usersList: [],
      notAddedUsers: [],
      addedUsers: []
    };
  }

  handleNewGroupSubmit = event => {
    event.preventDefault();
    axios
      .post("http://localhost:8080/dvs/api/creategroup", {
        description: this.state.groupDescription,
        groupName: this.state.groupName,
        userList: this.state.addedUsers.map(item => {
          return item.username;
        })
      })
      .then(response => {
        this.props.hideNewGroup();
      })
      .catch(error => console.log(error));
  };

  handleGroupNameChange = event => {
    this.setState({ groupName: event.target.value });
  };

  handleGroupDescriptionChange = event => {
    this.setState({ groupDescription: event.target.value });
  };

  componentDidMount() {
    this.connectForUsersData();
  }

  connectForUsersData = () => {
    axios
      .get("http://localhost:8080/dvs/api/users")
      .then(response => {
        let tmpUsersData = response.data.map((item, index) => {
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

        this.setState({
          usersList: tmpUsersData,
          notAddedUsers: tmpUsersData
        });
      })
      .catch(error => {
        console.log(error);
      });
  };

  changeAddedStatus = username => {
    let tmpUsers = this.state.usersList;
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
        this.setState({ usersList: tmpUsers });
        this.filterAddedGroups();
      }
    }
  };

  filterAddedGroups = () => {
    let filterUsers = this.state.usersList;
    let tmpNotAdded = [];
    let tmpAdded = [];
    for (let i = 0; i < filterUsers.length; i++) {
      const element = filterUsers[i];
      if (element.added) {
        tmpAdded.push(element);
      } else {
        tmpNotAdded.push(element);
      }

      this.setState({ notAddedUsers: tmpNotAdded, addedUsers: tmpAdded });
    }
  };

  render() {
    return (
      <Modal
        show={this.props.showNewGroup}
        onHide={this.props.hideNewGroup}
        size="lg"
      >
        <Modal.Header closeButton>
          <Modal.Title>Create New Group</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={this.handleNewGroupSubmit}>
            <GroupInformation
              handleGroupNameChange={this.handleGroupNameChange}
              groupName={this.state.groupName}
              handleGroupDescriptionChange={this.handleGroupDescriptionChange}
              groupDescription={this.state.groupDescription}
            />
            <hr className="m-1" />
            <AddUsersToGroup notAddedUsers={this.state.notAddedUsers} />
            <hr className="m-1" />
            <GroupUsers addedUsers={this.state.addedUsers} />
            <hr className="m-1" />
            <AddDocumentTypes />
            <hr className="m-1" />
            <SetRights />
            <div className="form-group row d-flex justify-content-center">
              <div className="modal-footer ">
                <button
                  type="button"
                  className="btn btn-outline-dark"
                  onClick={this.props.hideNewGroup}
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="btn btn-dark"
                  data-dismiss="modal"
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

export default NewGroup;
