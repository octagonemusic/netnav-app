import React, { useState } from "react";
import { Modal, Button, Form, Toast } from "react-bootstrap";
import axios from "axios";

const ShortestPath = ({ updateData, changeShortestPath }) => {
  const [startNode, setStartNode] = useState("");
  const [endNode, setEndNode] = useState("");
  const [path, setPath] = useState(""); // new state variable for the shortest path
  const [distance, setDistance] = useState();
  const [showModal, setShowModal] = useState(false);
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState("");
  const [routeData, setRouteData] = useState({
    sourceRouterId: "",
    targetRouterId: "",
    latency: "",
    bandwidth: "",
  });

  const handleInputChange = (event) => {
    setRouteData({
      ...routeData,
      [event.target.name]: event.target.value,
    });
  };

  const handleFormSubmit = async (event) => {
    event.preventDefault();
    try {
      await axios.post(
        "http://localhost:8080/routerRouteData/addRoute",
        routeData
      );
      updateData();
      handleClose();
      setToastMessage("Route added successfully!");
      setShowToast(true);
    } catch (error) {
      handleClose();
      if (error.response && error.response.status === 400) {
        // The request was made and the server responded with a status code
        // that falls out of the range of 2xx
        setToastMessage("Bad Request: " + error.response.data);
      } else {
        // Something happened in setting up the request that triggered an Error
        setToastMessage("Error: " + error.message);
      }
      setShowToast(true);
    }
  };

  const handleClose = () => setShowModal(false);
  const handleShow = () => setShowModal(true);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const response = await fetch(
      `http://localhost:8080/routerRouteData/shortestPath?start=${startNode}&end=${endNode}`
    );
    const data = await response.json();
    changeShortestPath(data.shortestRoute);
    setPath(data.shortestRoute.join(" -> ")); // update the path state variable
    setDistance(data.shortestDistance); // update the distance state variable
  };

  return (
    <>
      <form onSubmit={handleSubmit}>
        <label>
          Start Node:
          <input
            type="text"
            value={startNode}
            onChange={(e) => setStartNode(e.target.value)}
          />
        </label>
        <label>
          End Node:
          <input
            type="text"
            value={endNode}
            onChange={(e) => setEndNode(e.target.value)}
          />
        </label>
        <input type="submit" value="Calculate" />
        <p></p>
        <p>Shortest Path: {path}</p> {/* display the shortest path */}
        <p>Time taken to traverse: {distance} ms</p>{" "}
        {/* display the distance */}
        <Button variant="primary" onClick={handleShow}>
          Add Route
        </Button>
      </form>
      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Add New Route</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleFormSubmit}>
            <Form.Group controlId="sourceRouterId">
              <Form.Label style={{ padding: "0rem", paddingTop: "1rem" }}>
                Source Router ID
              </Form.Label>
              <Form.Control
                type="text"
                name="sourceRouterId"
                onChange={handleInputChange}
              />
            </Form.Group>

            <Form.Group controlId="targetRouterId">
              <Form.Label style={{ padding: "0rem", paddingTop: "1rem" }}>
                Target Router ID
              </Form.Label>
              <Form.Control
                type="text"
                name="targetRouterId"
                onChange={handleInputChange}
              />
            </Form.Group>

            <Form.Group controlId="bandwidth">
              <Form.Label style={{ padding: "0rem", paddingTop: "1rem" }}>
                Bandwidth of the Connection
              </Form.Label>
              <Form.Control
                type="text"
                name="bandwidth"
                onChange={handleInputChange}
              />
            </Form.Group>

            <Form.Group controlId="latency">
              <Form.Label style={{ padding: "0rem", paddingTop: "1rem" }}>
                Latency of Connection
              </Form.Label>
              <Form.Control
                type="text"
                name="latency"
                onChange={handleInputChange}
              />
            </Form.Group>

            <Button
              variant="primary"
              type="submit"
              style={{ marginTop: "2rem" }}
            >
              Add Route
            </Button>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
      <Toast
        onClose={() => setShowToast(false)}
        show={showToast}
        delay={3000}
        autohide
      >
        <Toast.Header>
          <strong className="mr-auto">Notification</strong>
        </Toast.Header>
        <Toast.Body>{toastMessage}</Toast.Body>
      </Toast>
    </>
  );
};

export default ShortestPath;
