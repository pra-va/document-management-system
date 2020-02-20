import React, { Component } from "react";
import Table from "./../../2-AdvancedTable/AdvancedTable";

class SetRights extends Component {
  constructor(props) {
    super(props);
    this.state = {
      addedDocTypes: []
    };
  }

  dataFields = ["number", "name", "create", "sign", "addOrRemove"];
  columnNames = ["#", "Name", "Create", "Sign", ""];

  componentDidMount() {
    if (this.props.addedDocTypes) {
      if (this.props.addedDocTypes.length !== this.state.addedDocTypes.length) {
        this.setState({ addedDocTypes: this.props.addedDocTypes });
      }
    }
  }

  componentDidUpdate() {
    if (this.props.addedDocTypes) {
      if (this.props.addedDocTypes.length !== this.state.addedDocTypes.length) {
        this.setState({ addedDocTypes: this.props.addedDocTypes });
      }
    }
  }

  render() {
    return (
      <div>
        <h3 className="d-flex justify-content-start">5. Set rights.</h3>

        <Table
          id={"newDocTypeSetRights"}
          dataFields={this.dataFields}
          columnNames={this.columnNames}
          tableData={this.state.addedDocTypes}
          searchBarId={"addedGroupsSearchBar"}
        />
      </div>
    );
  }
}

export default SetRights;
