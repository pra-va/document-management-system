import React, { useState } from "react";
import AcceptOrReject from "./AcceptOrReject/AcceptOrReject";

const SignOrReject = props => {
  const [showModal, setShowModal] = useState(false);
  const handleShowModal = () => setShowModal(true);
  const handleCloseModal = () => setShowModal(false);

  return (
    <div>
      <button
        className="btn btn-secondary btn-sm btn-edit"
        onClick={handleShowModal}
      >
        Sign/Reject
      </button>
      <AcceptOrReject
        show={showModal}
        hide={handleCloseModal}
        item={props.item}
      />
    </div>
  );
};

export default SignOrReject;
