import React, { useEffect, useState } from "react";
import { DataSet, Network } from "vis-network/standalone/esm/vis-network";

const GraphComponent = ({ data, shortestPath }) => {
  const [network, setNetwork] = useState(null);

  useEffect(() => {
    const nodes = new DataSet(
      Array.from(
        new Set(
          data.flatMap((item) => [item.sourceRouterId, item.targetRouterId])
        )
      ).map((id) => {
        return { id: id.toString(), label: `ID: ${id}` };
      })
    );

    const edges = new DataSet(
      data.map((item) => {
        const from = item.sourceRouterId.toString();
        const to = item.targetRouterId.toString();
        return {
          from,
          to,
          label: `${item.bandwidth}Mbps\n\n${item.latency}ms\n\n${item.weight}ms`,
          length: item.weight / 5,
          font: { size: 12 },
        };
      })
    );

    const container = document.getElementById("mynetwork");
    const networkData = {
      nodes: nodes,
      edges: edges,
    };
    const options = {
      physics: {
        stabilization: true,
        barnesHut: {
          gravitationalConstant: -2000,
          centralGravity: 0.3,
          springLength: 95,
          springConstant: 0.04,
          damping: 0.09,
          avoidOverlap: 1,
        },
      },
    };

    setNetwork(new Network(container, networkData, options));
  }, [data]);

  useEffect(() => {
    // Update the colors of the edges based on the shortestPath
    if (!shortestPath)
      return alert("Start Node and End Node have not been initialized.");
    if (network) {
      const nodeIds = network.body.data.nodes.getIds();
      const updatedNodes = nodeIds.map((id) => {
        const isInShortestPath = shortestPath.includes(Number(id));
        const color = isInShortestPath ? "green" : "#49b7f2";
        return {
          id,
          color: { background: color },
          font: {
            color: isInShortestPath ? "#000" : "#343434",
            size: isInShortestPath ? 16 : 16,
            face: "Arial",
            bold: isInShortestPath,
          },
        };
      });
      network.body.data.nodes.update(updatedNodes);

      const edgeIds = network.body.data.edges.getIds();
      const updatedEdges = edgeIds.map((id) => {
        const edge = network.body.data.edges.get(id);
        const fromIndex = shortestPath.indexOf(Number(edge.from));
        const toIndex = shortestPath.indexOf(Number(edge.to));
        const color =
          fromIndex > -1 && toIndex > -1 && Math.abs(fromIndex - toIndex) === 1
            ? "green"
            : undefined;
        return { id, color: { color } };
      });
      network.body.data.edges.update(updatedEdges);
    }
  }, [shortestPath, network]);

  return <div id="mynetwork" style={{ width: "150%", height: "100vh" }} />;
};

export default GraphComponent;
