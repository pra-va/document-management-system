import React from "react";
import $ from "jquery";
import "./PopOver.css";

// popOverApparance={...} popOverTitle={""} popOverContent={""}
const PopOver = props => {
  const handleClickEvent = event => {
    event.preventDefault();
  };

  $(function() {
    $('[data-toggle="popover"]').popover();
  });

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
