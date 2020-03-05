import React from "react";
import "./ContentWrapper.css";

const ContentWrapper = props => {
  return (
    <div id="contentWrapper" className="mx-0 my-5 p-5">
      {props.content}
    </div>
  );
};

export default ContentWrapper;
