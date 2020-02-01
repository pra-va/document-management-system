import React, { Component } from "react";
import BootstrapTable from "react-bootstrap-table-next";
import ToolkitProvider from "react-bootstrap-table2-toolkit";
import paginationFactory, {
  PaginationProvider
} from "react-bootstrap-table2-paginator";
import "./AdvancedTable.css";

import CustomSearchBar from "./CustomSearch";

// Template for table data:
// products = [{ id: "asd", ... }];

// columns = [
//   {
//     dataField: "id",
//     text: "Product ID"
//   },
//   ...
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

  componentDidUpdate() {
    if (this.state.tableData !== this.props.tableData) {
      this.setState({ tableData: this.props.tableData });
    }
  }

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
      },
      {
        text: "24",
        value: 24
      }
    ]
  };

  render() {
    const paginationOption = {
      custom: true,
      totalSize: this.props.tableData.length
    };

    return (
      <div>
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
                      />
                    </div>
                  )}
                </PaginationProvider>
              ) : (
                <BootstrapTable keyField="id" {...props.baseProps} />
              )}
            </div>
          )}
        </ToolkitProvider>
      </div>
    );
  }
}

export default Table;
