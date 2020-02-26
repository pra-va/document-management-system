import React, { useState } from "react";
import EditDocument from "./../EditDocument/EditDocument";

const EditButton = props => {
  const [showModal, setShowModal] = useState(false);
  const handleShowModal = () => setShowModal(true);
  const handleCloseModal = () => setShowModal(false);

  return (
    <div>
      <button
        className="btn btn-secondary btn-sm btn-edit"
        onClick={handleShowModal}
      >
        Edit/View
      </button>
      <EditDocument
        show={showModal}
        hide={handleCloseModal}
        item={props.item}
      />
    </div>
  );
};

export default EditButton;
