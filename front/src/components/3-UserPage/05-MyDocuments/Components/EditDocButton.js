import React, { useState } from "react";
import EditDocument from "./../EditDocument/EditDocument";

const EditButton = props => {
  const [showModal, setShowModal] = useState(false);
  const handleShowModal = () => setShowModal(true);
  const handleCloseModal = () => {
    setShowModal(false);
    props.reloadTable();
  };

  return (
    <div>
      <EditDocument
        reloadTable={props.reloadTable}
        show={showModal}
        hide={handleCloseModal}
        item={props.item}
      />
      <button
        className="btn btn-secondary btn-sm btn-edit"
        onClick={handleShowModal}
      >
        Edit/View
      </button>
    </div>
  );
};

export default EditButton;
