import React, { Component } from "react";
import Table from "../../../6-CommonElements/2-AdvancedTable/AdvancedTable";
import axios from "axios";
import serverUrl from "../../../7-properties/1-URL";
import CheckBox from "../../../6-CommonElements/6-CheckBox/CheckBox";

class AssignRights extends Component {
  constructor(props) {
    super(props);
    this.state = {
      pagingData: {},
      selectedDocTypes: [],
      tableData: [],
      canCreate: [],
      canSign: [],
      dataFromServer: []
    };
  }

  columns = [
    // { dataField: "number", text: "#", sort: false },
    { dataField: "name", text: "Group Name", sort: true },
    { dataField: "create", text: "Create", sort: false },
    { dataField: "sign", text: "Sign", sort: false }
  ];

  componentDidMount() {
    this.fetchDocTypeData(0, 8, null, null, "");
  }

  fetchDocTypeData = (
    page,
    sizePerPage,
    sortField,
    order,
    searchValueString
  ) => {
    const pageData = {
      limit: sizePerPage,
      order: order,
      page: page,
      sortBy: sortField,
      searchValueString: searchValueString
    };

    axios
      .post(serverUrl + "groups", pageData)
      .then(response => {
        this.processData(
          response.data.groupList,
          this.state.canCreate,
          this.state.canSign
        );
        this.setState({
          pagingData: response.data.pagingData,
          dataFromServer: response.data.groupList
        });
      })
      .catch(error => {
        console.log(error);
      });
  };

  processData = (data, canCreate, canSign) => {
    this.setState({ tableData: [] });
    const tableData = data.map((item, index) => {
      return {
        number: index,
        name: item.name,
        create: (
          <CheckBox
            checkedStatus={canCreate.includes(item.name)}
            statusChange={this.statusChangeForCreate}
            id={"create:" + item.name}
            ownerName={item.name}
          />
        ),
        sign: (
          <CheckBox
            checkedStatus={canSign.includes(item.name)}
            statusChange={this.statusChangeForSign}
            id={"sign:" + item.name}
            ownerName={item.name}
          />
        )
      };
    });
    this.setState({ tableData: tableData });
  };

  statusChangeForSign = (isChecked, ownerName) => {
    const canSignTmp = [...this.state.canSign];
    if (isChecked) {
      if (!canSignTmp.includes(ownerName)) {
        canSignTmp.push(ownerName);
      }
    } else {
      if (canSignTmp.includes(ownerName)) {
        canSignTmp.splice(canSignTmp.indexOf(ownerName), 1);
      }
    }
    this.setState({ canSign: canSignTmp });
    this.processData(
      this.state.dataFromServer,
      this.state.canCreate,
      canSignTmp
    );
    this.props.setCanSign(canSignTmp);
  };

  statusChangeForCreate = (isChecked, ownerName) => {
    const canCreateTmp = [...this.state.canCreate];
    if (isChecked) {
      if (!canCreateTmp.includes(ownerName)) {
        canCreateTmp.push(ownerName);
      }
    } else {
      if (canCreateTmp.includes(ownerName)) {
        canCreateTmp.splice(canCreateTmp.indexOf(ownerName), 1);
      }
    }
    this.setState({ canCreate: canCreateTmp });
    this.processData(
      this.state.dataFromServer,
      canCreateTmp,
      this.state.canSign
    );
    this.props.setCanCreate(canCreateTmp);
  };

  handleRowSelect = (row, isSelect) => {
    const selectedDocTypes = this.state.selectedDocTypes;
    if (isSelect) {
      if (!selectedDocTypes.includes(row.name)) {
        selectedDocTypes.push(row.name);
      }
    } else {
      if (selectedDocTypes.includes(row.name)) {
        selectedDocTypes.splice(selectedDocTypes.indexOf(row.name), 1);
      }
    }
    this.setState({ selectedDocTypes: selectedDocTypes });
  };

  setSelectedItems = () => {
    const { tableData, selectedDocTypes } = this.state;
    let selectedItemNumbersForTable = [];
    for (let index = 0; index < tableData.length; index++) {
      const element = tableData[index].username;
      if (selectedDocTypes.includes(element)) {
        selectedItemNumbersForTable.push(index);
      }
    }
    return selectedItemNumbersForTable;
  };

  render() {
    return (
      <div>
        <h3 className="d-flex justify-content-start">2. Set rights.</h3>

        <Table
          id={"newUserGroups"}
          tableData={this.state.tableData}
          searchBarId={"createGroupUsersSearchBar"}
          requestNewData={this.fetchDocTypeData}
          pagingData={this.state.pagingData}
          columns={this.columns}
          selectType={"radio"}
          select={"false"}
          handleRowSelect={this.handleRowSelect}
          setSelectedItems={this.setSelectedItems}
        />
      </div>
    );
  }
}

export default AssignRights;
