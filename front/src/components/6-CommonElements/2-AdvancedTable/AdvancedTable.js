import React, { Component } from "react";
import BootstrapTable from "react-bootstrap-table-next";
import ToolkitProvider from "react-bootstrap-table2-toolkit";
import paginationFactory, {
  PaginationProvider
} from "react-bootstrap-table2-paginator";
import "./AdvancedTable.css";

import CustomSearchBar from "./CustomSearch";

// Template for table data:
// dataFields = [{ id: "asd", ... }];

// columnNames = [
//   {
//     dataField: "id",
//     text: "Product ID"
//   },
//   ...selectedRow
// ];

// table rows keys should have number dataField name.

class Table extends Component {
  constructor(props) {
    super(props);
    this.state = {
      dataFields: this.props.dataFields,
      columnNames: this.props.columnNames,
      tableData: []
    };
  }

  componentDidMount() {
    if (this.state.tableData !== this.props.tableData) {
      this.setState({ tableData: this.props.tableData });
    }
  }

  componentDidUpdate() {
    if (this.state.tableData !== this.props.tableData) {
      this.setState({ tableData: this.props.tableData });
    }
  }

  handleRowSelect = (row, isSelect) => {
    if (this.props.selectedRow) {
      this.props.selectedRow(row);
    }
  };

  createColumns = () => {
    let columns = this.state.dataFields.map((item, index) => {
      return {
        dataField: item,
        text: this.state.columnNames[index],
        sort: true
      };
    });
    return columns;
  };

  options = {
    pageStartIndex: 1,
    firstPageText: "First",
    prePageText: "Back",
    nextPageText: "Next",
    lastPageText: "Last",
    nextPageTitle: "First page",
    prePageTitle: "Pre page",
    firstPageTitle: "Next page",
    lastPageTitle: "Last page",
    onPageChange: () => {
      window.scrollTo(0, 0);
    },
    sizePerPageList: [
      {
        text: "8",
        value: 8
      },
      {
        text: "16",
        value: 16
      }
    ]
  };

  selectRow = {
    mode: "radio",
    clickToSelect: true,
    hideSelectColumn: true,
    bgColor: this.props.select !== undefined ? "#000000" : "",
    onSelect: this.handleRowSelect
  };

  render() {
    const paginationOption = {
      custom: true,
      totalSize: this.props.tableData.length
    };

    return (
      <div id={this.props.id}>
        <ToolkitProvider
          keyField="number"
          data={this.state.tableData}
          columns={this.createColumns()}
          search
        >
          {props => (
            <div>
              <CustomSearchBar {...props.searchProps} id={props.searchBarId} />

              {this.state.tableData.length > 8 ? (
                <PaginationProvider
                  pagination={paginationFactory(paginationOption)}
                >
                  {({ paginationProps, paginationTableProps }) => (
                    <div>
                      <BootstrapTable
                        keyField="id"
                        {...props.baseProps}
                        {...paginationTableProps}
                        pagination={paginationFactory(this.options)}
                        classes="table-striped table-dark table-sm"
                        selectRow={this.selectRow}
                        hover
                      />
                    </div>
                  )}
                </PaginationProvider>
              ) : (
                <BootstrapTable
                  keyField="id"
                  {...props.baseProps}
                  classes="table-striped table-dark table-sm"
                  hover
                  selectRow={this.selectRow}
                />
              )}
            </div>
          )}
        </ToolkitProvider>
      </div>
    );
  }
}

export default Table;
