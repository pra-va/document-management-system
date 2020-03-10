import React, { Component } from "react";
import Table from "./../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";
import Validation from "./../../../6-CommonElements/5-FormInputValidationLine/Validation";

class SelectType extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tableData: [],
      isRowSelected: false,
      pagingData: {},
      selectedValue: "",
      initialDataFetched: false
    };
  }

  componentDidUpdate() {
    const { username } = this.props;
    let { tableData, initialDataFetched } = this.state;
    if (username !== "" && tableData.length === 0 && !initialDataFetched) {
      this.fetchUserDocTypes(0, 8, null, null, "");
      this.setState({ initialDataFetched: true });
    }
  }

  columns = [
    // { dataField: "number", text: "#", sort: false },
    { dataField: "name", text: "Type", sort: true }
  ];

  fetchUserDocTypes = (
    page,
    sizePerPage,
    sortField,
    order,
    searchValueString
  ) => {
    const { username } = this.props;
    const pagingData = {
      limit: sizePerPage,
      order: order,
      page: page,
      sortBy: sortField,
      searchValueString: searchValueString
    };
    if (username !== "") {
      axios
        .post(serverUrl + username + "/dtypescreate", pagingData)
        .then(response => {
          if (response.data.length !== 0 && response.data !== undefined) {
            this.processData(response.data.docTypes);
            this.setState({ pagingData: response.data.pagingData });
          }
        })
        .catch(error => {
          console.log(error);
        });
    }
  };

  processData = data => {
    var tableData = data.map((item, index) => {
      return {
        number: index + 1,
        name: item,
        select: (
          <button className="btn btn-secondary btn-sm" onClick={this.doNothing}>
            Select
          </button>
        )
      };
    });
    this.setState({ tableData: tableData });
  };

  doNothing = event => {
    event.preventDefault();
  };

  selectedRow = row => {
    this.props.handleDocTypeSelect(row.name);
    this.setState({ isRowSelected: true, selectedValue: row.name });
  };

  setSelectedItems = () => {
    const { tableData, selectedValue } = this.state;
    for (let index = 0; index < tableData.length; index++) {
      const element = tableData[index].name;
      if (selectedValue === element) {
        return [index + 1];
      }
    }
  };

  render() {
    return (
      <div>
        <h3 className="d-flex justify-content-start">
          2. Select document type.
        </h3>

        <Table
          id={"usersDocTypes"}
          tableData={this.state.tableData}
          searchBarId={"searchDocType"}
          requestNewData={this.fetchUserDocTypes}
          pagingData={this.state.pagingData}
          columns={this.columns}
          selectType={"radio"}
          select={true}
          handleRowSelect={this.selectedRow}
          setSelectedItems={this.setSelectedItems}
        />
        <Validation
          output="Document type must be selected."
          satisfied={this.state.isRowSelected}
        />
      </div>
    );
  }
}

export default SelectType;
