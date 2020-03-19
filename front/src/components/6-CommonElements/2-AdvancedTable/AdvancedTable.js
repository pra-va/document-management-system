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

// dataLength - table data length
// requestNewData(page, sizePerPage) - method to call for new data from server
class Table extends Component {
  constructor(props) {
    super(props);
    this.state = {
      dataFields: this.props.dataFields,
      columnNames: this.props.columnNames,
      tableData: [],
      page: 1,
      sizePerPage: 8,
      sortField: undefined,
      sortOrder: undefined,
      tableSelectType: "checkbox",
      searchValue: "",
      selectedItems: []
    };
  }

  componentDidUpdate() {
    if (this.props.setSelectedItems !== undefined) {
      const { tableData, selectedItems } = this.state;
      const tableDataProp = this.props.tableData;
      const selectedItemsProp = this.props.setSelectedItems();

      if (
        JSON.stringify(tableData) !== JSON.stringify(tableDataProp) ||
        JSON.stringify(selectedItems) !== JSON.stringify(selectedItemsProp)
      ) {
        this.setState({
          tableData: [...this.props.tableData],
          selectedItems: this.props.setSelectedItems()
        });
      }
    }
  }

  setSearchValue = newValue => {
    this.setState({ searchValue: newValue });
  };

  handleRowSelect = (row, isSelect) => {
    this.props.handleRowSelect(row, isSelect);
  };

  handleTableChange = (type, { page, sizePerPage, sortField, sortOrder }) => {
    this.props.requestNewData(
      page - 1,
      sizePerPage,
      sortField,
      sortOrder,
      this.state.searchValue
    );
    this.setState(() => ({
      page,
      sizePerPage,
      sortField,
      sortOrder
    }));
    if (this.props.getPagingData !== undefined) {
      this.props.getPagingData({
        limit: sizePerPage,
        order: sortOrder,
        page: page - 1,
        searchValueString: this.state.searchValue,
        sortBy: sortField
      });
    }
  };

  createColumns = () => {
    if (this.props.columns !== undefined) {
      return this.props.columns;
    }

    let columns = this.state.dataFields.map((item, index) => {
      return {
        dataField: item,
        text: this.state.columnNames[index],
        sort: item === "number" ? false : true
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

  isTableWithPagination = () => {
    if (this.props.pagingData !== undefined) {
      if (this.props.pagingData.totalItems > 8) {
        return true;
      }
    }
    return false;
  };

  render() {
    const selectRow = {
      mode:
        this.props.selectType !== undefined
          ? this.props.selectType
          : "checkbox",
      clickToSelect: true,
      hideSelectColumn: this.props.selectType === "checkbox" ? false : true,
      bgColor: this.props.select !== undefined ? "#6c757d" : "",
      onSelect: this.handleRowSelect,
      onSelectAll:
        this.props.handleSelectAll === undefined
          ? () => {}
          : this.props.handleSelectAll,
      selected: this.state !== undefined ? this.state.selectedItems : []
    };

    const paginationOption = {
      custom: true,
      totalSize: 0
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
              <CustomSearchBar
                {...props.searchProps}
                id={props.searchBarId}
                setSearchValue={this.setSearchValue}
              />

              {this.isTableWithPagination() ? (
                <PaginationProvider
                  pagination={paginationFactory(paginationOption)}
                >
                  {({ paginationProps, paginationTableProps }) => (
                    <div>
                      <BootstrapTable
                        remote={{ pagination: true, sort: true, search: true }}
                        keyField="id"
                        {...props.baseProps}
                        {...paginationTableProps}
                        classes="table-striped table-dark table-sm overflow-auto"
                        selectRow={selectRow}
                        pagination={paginationFactory({
                          page:
                            this.props.pagingData !== undefined
                              ? this.props.pagingData.currentPage + 1
                              : 0,
                          sizePerPage:
                            this.props.pagingData !== undefined
                              ? this.props.pagingData.pageSize
                              : 8,
                          totalSize:
                            this.props.pagingData !== undefined
                              ? this.props.pagingData.totalItems
                              : 0,
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
                        })}
                        onTableChange={this.handleTableChange}
                        wrapperClasses="table-responsive"
                        hover
                      />
                    </div>
                  )}
                </PaginationProvider>
              ) : (
                <BootstrapTable
                  remote={{ pagination: true, sort: true, search: true }}
                  keyField="id"
                  {...props.baseProps}
                  classes="table-striped table-dark table-sm overflow-auto "
                  onTableChange={this.handleTableChange}
                  wrapperClasses="table-responsive"
                  hover
                  selectRow={selectRow}
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
