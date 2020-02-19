import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";

class EditGroup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      groupName: "",
      groupDescription: "",
      usersList: [],
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
  handleSubmit = event => {
    event.preventDefault();

    const editedGroup = {
      description: this.state.groupDescription,
      docTypesToCreate: this.state.canCreate,
      docTypesToSign: this.state.canSign,
      groupName: this.state.groupName,
      userList: this.state.addedUsers.map(item => {
        return item.username;
      })
    };

    axios
      .post(serverUrl + "groups/update/" + this.props.ownerName, editedGroup)
      .then(response => {
        window.location.reload();
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
      }
    }
    this.filterAddedGroups(tmpUsers);
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
      usersList: data,
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

  render() {
    return (
      <Modal
        show={this.props.show}
        onHide={this.props.onHide}
        size={"lg"}
        id="editUserModal"
      >
        <Modal.Header closeButton>
          <Modal.Title>Modal heading</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <h1>Edit group modal.</h1>
        </Modal.Body>
      </Modal>
    );
  }
}

export default EditGroup;
