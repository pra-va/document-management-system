import React, { Component } from "react";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";

class userGroups extends Component {
  constructor(props) {
    super(props);
    this.state = {
      userGroups: []
    };
  }

  dataFields = ["number", "name", "addOrRemove"];
  columnNames = ["#", "Name", ""];

  componentDidMount() {
    if (this.props.userGroups) {
      if (this.props.userGroups.length !== this.state.userGroups.length) {
        this.setState({ userGroups: this.props.userGroups });
      }
    }
  }

  componentDidUpdate() {
    if (this.props.userGroups) {
      if (this.props.userGroups.length !== this.state.userGroups.length) {
        this.setState({ userGroups: this.props.userGroups });
      }
    }
  }

  render() {
    return (
      <div className="m-3">
        <div className="row d-flex justify-content-start">
          <h3 className="d-flex justify-content-start">3. Users' groups.</h3>
        </div>

        <Table
          dataFields={this.dataFields}
          columnNames={this.columnNames}
          tableData={this.state.userGroups}
          searchBarId={"addedGroupsSearchBar"}
        />
      </div>
    );
  }
}

export default userGroups;
