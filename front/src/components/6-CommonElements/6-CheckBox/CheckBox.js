import React, { Component } from "react";
import "./CheckBox.css";

// checkedStatus={function ()}
// id={id}
// ownerName={""}
class CheckBox extends Component {
  constructor(props) {
    super(props);
    this.state = { checked: props.checkedStatus, owner: props.ownerName };
  }

  componentDidUpdate() {
    if (this.state.checked !== this.props.checkedStatus) {
      this.setState({ checked: this.props.checkedStatus });
    }
  }

  handleChangeCheckedState = event => {
    this.props.statusChange(event.target.checked, this.state.owner);
  };

  doNothing = event => {};

  render() {
    return (
      <div>
        <input
          className="big-checkbox"
          autoComplete="on"
          type="checkbox"
          id={this.props.id}
          onClick={this.handleChangeCheckedState}
          onChange={this.doNothing}
          checked={this.state.checked}
        />
      </div>
    );
  }
}

export default CheckBox;
