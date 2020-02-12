import React from "react";
import $ from "jquery";
import "./PopOver.css";

$(function() {
  $('[data-toggle="popover"]').popover();
});

// popOverApparance={...} popOverTitle={""} popOverContent={""}
const PopOver = props => {
  const handleClickEvent = event => {
    event.preventDefault();
  };

  console.log(props);

  return (
    <span
      title={props.popOverTitle}
      data-toggle="popover"
      id="popover"
      data-trigger="hover"
      href="#0"
      onClick={handleClickEvent}
      data-content={props.popOverContent}
    >
      {props.popOverApparance}
    </span>
  );
};

export default PopOver;
