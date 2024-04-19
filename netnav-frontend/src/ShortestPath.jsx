import React, { useState } from "react";

const ShortestPath = ({ changeShortestPath }) => {
  const [startNode, setStartNode] = useState("");
  const [endNode, setEndNode] = useState("");
  const [path, setPath] = useState(""); // new state variable for the shortest path
  const [distance, setDistance] = useState();

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
      <input type="submit" value="Submit" />
      <p>Shortest Path: {path}</p> {/* display the shortest path */}
      <p>Time taken to traverse: {distance} ms</p> {/* display the distance */}
    </form>
  );
};

export default ShortestPath;
