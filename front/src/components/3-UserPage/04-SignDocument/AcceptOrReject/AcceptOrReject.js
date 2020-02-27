import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import axios from "axios";
import serverUrl from "./../../../7-properties/1-URL";
import PopOver from "./../../../6-CommonElements/8-PopOver/PopOver";

class AcceptOrReject extends Component {
  constructor(props) {
    super(props);
    this.state = { username: "", comment: "" };
  }

  componentDidMount() {
    this.getUsername();
  }

  componentDidUpdate() {
    console.log(this.props.item);
  }

  getUsername = () => {
    axios
      .get(serverUrl + "loggedin")
      .then(response => {
        this.setState({ username: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  downloadFile = (event, uid, fileName) => {
    event.preventDefault();
    axios
      .request({
        url: serverUrl + "files/" + uid,
        method: "GET",
        responseType: "blob"
      })
      .then(({ data }) => {
        const downloadUrl = window.URL.createObjectURL(new Blob([data]));
        const link = document.createElement("a");
        link.href = downloadUrl;
        link.setAttribute("download", fileName);
        document.body.appendChild(link);
        link.click();
        link.remove();
      });
  };

  generateAttachedFilesList = () => {
    return this.props.item.filesAttached.map((item, index) => {
      return (
        <div
          key={index}
          className="row p-1 d-flex justify-content-center info-div"
        >
          <button
            className="btn btn-outline-dark"
            onClick={event => this.downloadFile(event, item.uid, item.fileName)}
          >
            {item.fileName}
          </button>
        </div>
      );
    });
  };

  handleDescriptionChange = event => {
    this.setState({ comment: event.target.value });
  };

  handleApprove = event => {
    event.preventDefault();
    axios
      .post(serverUrl + "doc/approve/" + this.props.item.uid, {
        username: this.state.username
      })
      .then(response => {
        console.log("accepted");
        this.props.hide();
        window.location.reload();
      })
      .catch(error => {
        console.log(error);
      });
  };

  handleDecline = event => {
    event.preventDefault();

    const postData = {
      reasonToReject: this.state.comment,
      username: this.state.username
    };

    axios
      .post(serverUrl + "doc/reject/" + this.props.item.uid, postData)
      .then(response => {
        console.log("accepted");
        this.props.hide();
        window.location.reload();
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <div>
        <Modal
          show={this.props.show}
          onHide={this.props.hide}
          aria-labelledby="example-modal-sizes-title-lg"
        >
          <Modal.Header closeButton>
            <Modal.Title id="example-modal-sizes-title-lg">
              Sign / Decline Document
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <form>
              <div className="row p-3">
                <div className="col-3">Document ID:</div>
                <div className="col-9">{this.props.item.uid}</div>
              </div>
              <div className="row p-3">
                <div className="col-3">Name:</div>
                <div className="col-9">{this.props.item.name}</div>
              </div>
              <div className="row p-3">
                <div className="col-3">Document Type:</div>
                <div className="col-9">{this.props.item.type}</div>
              </div>
              <div className="row p-3">
                <div className="col-3">Description:</div>
                <div className="col-9">{this.props.item.description}</div>
              </div>
              <div className="row p-3">
                <div className="col-3">Submitted:</div>
                <div className="col-9">
                  {this.props.item.dateSubmit
                    ? this.props.item.dateSubmit.substring(0, 10)
                    : ""}
                </div>
              </div>
              <div className="row p-3">
                <div className="col-3">Created By</div>
                <div className="col-9">{this.props.item.author}</div>
              </div>
              <div className="row p-3">
                <div className="col-3">Attached Files:</div>
                <div className="col-9">{this.generateAttachedFilesList()}</div>
              </div>
              <div className="row p-3">
                <div className="col-3">Decline Reason:</div>
                <div className="col-9">
                  {" "}
                  <textarea
                    className="form-control"
                    id="inputDocDescription"
                    rows="3"
                    onChange={this.handleDescriptionChange}
                    value={this.state.comment}
                  ></textarea>
                </div>
              </div>
              <div className="row d-flex justify-content-center">
                <button className="btn btn-secondary btn-lg m-3">Close</button>
                <PopOver
                  popOverApparance={
                    <button
                      className="btn btn-secondary btn-lg m-3"
                      onClick={this.handleDecline}
                      disabled={this.state.comment.length === 0 ? true : false}
                    >
                      Decline
                    </button>
                  }
                  popOverTitle={""}
                  popOverContent={
                    'Fill in "Decline Reason" to be able to decline this document.'
                  }
                />

                <PopOver
                  popOverApparance={
                    <button
                      className="btn btn-secondary btn-lg m-3"
                      onClick={this.handleApprove}
                      disabled={this.state.comment.length === 0 ? false : true}
                    >
                      Sign
                    </button>
                  }
                  popOverTitle={""}
                  popOverContent={
                    'Leave "Decline Reason" empty to sign this document.'
                  }
                />
              </div>
            </form>
          </Modal.Body>
        </Modal>
      </div>
    );
  }
}

export default AcceptOrReject;
