import React, { Component } from "react";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";

class UserDetails extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      userData: null,
      name: "Unable to load.",
      surname: "Unable to load.",
      role: "Unable to load.",
      userGroups: "None"
    };
  }

  componentDidMount() {
    if (this.props.username !== this.state.username) {
      this.setState({ username: this.props.username });
    }
    if (this.state.username.length > 0) {
      this.fetchUserData();
    }
  }

  componentDidUpdate() {
    if (this.props.username !== this.state.username) {
      this.setState({ username: this.props.username });
    }
    if (this.state.username.length > 0) {
      this.fetchUserData();
    }
  }

  setUpData = data => {
    this.setState({
      name: data.name,
      surname: data.surname,
      role: data.role,
      userGroups:
        data.groupList.length === 0 ? "None" : this.reduceGroups(data.groupList)
    });
  };

  reduceGroups = list => {
    const reducedList = list.reduce((sum, item, index) => {
      if (index === 0) {
        return (sum = item);
      } else {
        return (sum += ", " + item);
      }
    });
    return reducedList;
  };

  fetchUserData = () => {
    console.log(serverUrl + "user/" + this.state.username);
    axios
      .get(serverUrl + "user/" + this.state.username)
      .then(response => {
        if (this.state.userData === null) {
          this.setState({ userData: response.data });
          this.setUpData(response.data);
        }
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <div style={{ width: "95%" }}>
        <h5>First Name: {this.state.name}</h5>
        <h5>Last Name: {this.state.surname}</h5>
        <h5>Role: {this.state.role}</h5>
        <hr className="m-1" />
        <h5>Users groups: {this.state.userGroups}</h5>
      </div>
    );
  }
}

export default UserDetails;
