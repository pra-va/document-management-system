import React, { Component } from "react";

// FIX ID
// checkedStatus={function ()}
// id={id}
// ownerName={""}
class CheckBox extends Component {
  constructor(props) {
    super(props);
    this.state = { checked: props.checked, owner: this.props.ownerName };
  }

  handleChangeCheckedState = event => {
    this.setState({ checked: event.target.checked });
    this.props.statusChange(event.target.checked, this.state.owner);
  };

  returnCheckedStatus = () => {
    this.props.checkedStatus(this.state.checked);
  };

  componentWillUnmount() {
    this.props.statusChange(false, this.state.owner);
  }

  doNothing = event => {
    console.log(event.target.checked);
  };

  render() {
    return (
      <div>
        <input
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
