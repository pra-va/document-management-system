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
    const { onlyPdfFiles, setOnlyPdfFiles, values } = props;
    let filteredFilesOnlyPDF = true;

    for (let i = 0; i < values.length; i++) {
      const element = values[i];
      if (element.file !== undefined) {
        if (element.file.type !== "application/pdf") {
          filteredFilesOnlyPDF = false;
          break;
        }
      }
    }

    if (onlyPdfFiles !== filteredFilesOnlyPDF) {
      setOnlyPdfFiles(filteredFilesOnlyPDF);
    }

    if (numberOfFiles > 0) {
      return (
        <Validation
          satisfied={filteredFilesOnlyPDF}
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
