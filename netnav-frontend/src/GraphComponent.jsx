import React, { useEffect, useState } from "react";
import { DataSet, Network } from "vis-network/standalone/esm/vis-network";

const GraphComponent = ({ data, shortestPath }) => {
  const [network, setNetwork] = useState(null);

  useEffect(() => {
    if (shortestPath === undefined) {
      alert("From and To have not been given properly");
      return;
    }
    const nodes = new DataSet(
      Array.from(
        new Set(
          data.flatMap((item) => [item.sourceRouterId, item.targetRouterId])
        )
      ).map((id) => {
        const weight = data.find(
          (item) => item.sourceRouterId === id || item.targetRouterId === id
        )?.weight;
        const color = shortestPath.includes(id) ? "green" : undefined; // highlight color for nodes in the shortest path
        return { id: id.toString(), label: `ID: ${id}`, color };
      })
    );

    const edges = new DataSet(
      data.map((item) => {
        const from = item.sourceRouterId.toString();
        const to = item.targetRouterId.toString();
        const color =
          shortestPath.indexOf(from) > -1 &&
          shortestPath[shortestPath.indexOf(from) + 1] === to
            ? "green"
            : undefined; // highlight color for edges in the shortest path
        return {
          from,
          to,
          label: `${item.weight}`,
          length: item.weight / 5,
          color: {
            color: color,
            inherit: "to",
          },
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
        barnesHut: {
          avoidOverlap: 0,
        },
      },
    };

    setNetwork(new Network(container, networkData, options));
  }, [data, shortestPath]); // add shortestPath to the dependency array

  return <div id="mynetwork" style={{ width: "100%", height: "600px" }} />;
};

export default GraphComponent;
