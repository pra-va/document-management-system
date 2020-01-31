import React, { Component } from "react";

class AddButton extends Component {
  constructor(props) {
    super(props);
    this.state = { added: false };
  }

  handleAdd = event => {
    event.preventDefault();
    this.setState({ added: !this.state.added });
  };

  render() {
    return (
      <button
        onClick={this.handleAdd}
        className={this.state.added ? "btn btn-danger" : "btn btn-dark"}
      >
        {this.state.added ? "Remove" : "Add"}
      </button>
    );
  }
}

export default AddButton;
