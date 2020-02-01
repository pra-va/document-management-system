import React, { Component } from "react";

class AddButton extends Component {
  constructor(props) {
    super(props);
    this.state = { added: false, groupName: "" };
  }

  handleAdd = event => {
    event.preventDefault();
    this.setState({ added: !this.state.added });

    this.props.changeAddedStatus(this.state.groupName, this.state.added);
  };

  componentDidMount() {
    this.setState({ added: this.props.added, groupName: this.props.groupName });
  }

  render() {
    return (
      <button
        onClick={this.handleAdd}
        className={this.state.added ? "btn btn-danger" : "btn btn-secondary"}
      >
        {this.state.added ? "Remove" : "Add"}
      </button>
    );
  }
}

export default AddButton;
