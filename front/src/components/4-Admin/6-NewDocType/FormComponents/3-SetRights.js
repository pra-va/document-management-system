import React, { Component } from "react";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";

class SetRights extends Component {
  constructor(props) {
    super(props);
    this.state = {
      addedGroups: []
    };
  }

  dataFields = ["number", "name", "create", "sign", "addOrRemove"];
  columnNames = ["#", "Name", "Create", "Sign", ""];

  componentDidMount() {
    if (this.props.addedGroups) {
      if (this.props.addedGroups.length !== this.state.addedGroups.length) {
        this.setState({ addedGroups: this.props.addedGroups });
      }
    }
  }

  componentDidUpdate() {
    if (this.props.addedGroups) {
      if (this.props.addedGroups.length !== this.state.addedGroups.length) {
        this.setState({ addedGroups: this.props.addedGroups });
      }
    }
  }

  render() {
    return (
      <div className="m-3">
        <div className="row d-flex justify-content-start">
          <h3 className="d-flex justify-content-start">3. Set rights.</h3>
        </div>

        <Table
          dataFields={this.dataFields}
          columnNames={this.columnNames}
          tableData={this.state.addedGroups}
          searchBarId={"addedGroupsSearchBar"}
        />
      </div>
    );
  }
}

export default SetRights;
