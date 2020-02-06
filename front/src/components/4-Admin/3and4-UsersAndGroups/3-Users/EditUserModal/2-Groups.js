import React, { Component } from "react";
import "./2-Groups.css";
import Table from "./../../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import axios from "axios";
import AddOrRemoveButton from "./../../../../6-CommonElements/4-Buttons/1-AddRemove/ButtonAddOrRemove";

class Groups extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tableData: [],
      userGroups: [],
      allGroups: [],
      notAddedGroups: [],
      addedGroups: []
    };
  }

  dataFields = ["number", "name", "addOrRemove"];
  columnNames = ["#", "Name", "Add/Remove"];

  componentDidMount() {
    this.getGroupData();
  }

  componentDidUpdate() {
    if (this.state.userGroups.length !== this.props.userGroups.length) {
      this.setState({ userGroups: this.props.userGroups });
    }
  }

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
        this.setState({ tableData: tmpGroups });
        this.filterAddedGroups();
      }
    }
  };

  filterAddedGroups = () => {
    let filterGroups = this.state.allGroups;
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
    this.setState({ notAddedGroups: notAdded });
    this.setState({ addedGroups: added });
    this.props.setAddeduserGroups(added);
  };

  getGroupData = () => {
    axios
      .get("http://localhost:8080/dvs/api/groups")
      .then(response => {
        let tempData = response.data.map((item, index) => {
          let isItemAdded = this.hasUserAddedGroup(item.name);
          return {
            number: index + 1,
            name: item.name,
            addOrRemove: (
              <AddOrRemoveButton
                itemName={item.name}
                changeAddedStatus={this.changeAddedStatus}
                added={isItemAdded}
              />
            ),
            added: isItemAdded
          };
        });
        this.setState({
          allGroups: tempData
        });
        this.filterAddedGroups();
      })
      .catch(error => {
        console.log(error);
      });
  };

  hasUserAddedGroup = groupName => {
    for (let i = 0; i < this.state.userGroups.length; i++) {
      const element = this.state.userGroups[i];
      if (element === groupName) {
        return true;
      }
    }
    return false;
  };

  render() {
    return (
      <div className="mx-3">
        <div className="row d-flex justify-content-start">
          <h3 className="d-flex justify-content-start">
            2. Add user to groups.
          </h3>
        </div>

        <Table
          dataFields={this.dataFields}
          columnNames={this.columnNames}
          tableData={this.state.notAddedGroups}
          searchBarId={"currentGroupsSearchBar"}
        />
      </div>
    );
  }
}

export default Groups;
