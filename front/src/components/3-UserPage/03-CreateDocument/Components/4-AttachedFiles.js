import React from "react";
import Alert from "./../../../6-CommonElements/11-Alert/Alert";
import Validation from "./../../../6-CommonElements/5-FormInputValidationLine/Validation";

const AttachedFiles = props => {
  var numberOfFiles = 0;

  const generateFileList = () => {
    if (props.values !== undefined) {
      return props.values.map((item, index) => {
        numberOfFiles++;
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

  const checkFileTypes = () => {
    let areAllFilesPdf = true;

    props.values.forEach(element => {
      if (element.file.type !== "application/pdf") {
        areAllFilesPdf = false;
      }
    });

    if (numberOfFiles > 0) {
      return (
        <Validation
          satisfied={areAllFilesPdf}
          output="Only files with PDF format are allowed."
        />
      );
    }
  };

  return (
    <div>
      {generateFileList()}
      {checkFileTypes()}
    </div>
  );
};

export default AttachedFiles;
