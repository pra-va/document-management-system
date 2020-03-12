import React from "react";
import Alert from "./../../../6-CommonElements/11-Alert/Alert";

const AttachedFiles = props => {
  const generateFileList = () => {
    if (props.values !== undefined) {
      return props.values.map((item, index) => {
        return (
          <Alert
            key={item.number}
            index={item.number}
            text={item.fileName + " (" + item.size + ")"}
            execute={props.handleRemove}
          />
        );
      });
    }
  };

  return <div>{generateFileList()}</div>;
};

export default AttachedFiles;
