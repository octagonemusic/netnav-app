import { useEffect, useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";
import GraphComponent from "./GraphComponent";
import ShortestPath from "./ShortestPath";

function App() {
  const [data, setData] = useState();
  const [loading, setLoading] = useState(true);
  const [shortestPath, setShortestPath] = useState([]);

  //make a request to the backend
  const fetchData = async () => {
    try {
      const response = await fetch("http://localhost:8080/routerRouteData");
      const data = await response.json();
      setData(data);
    } catch (error) {
      console.error(error);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchData();
  }, []);

  function changeShortestPath(shortestPath) {
    setShortestPath(shortestPath);
  }

  return (
    <>
      {loading ? (
        <h1>Loading...</h1>
      ) : (
        <GraphComponent data={data} shortestPath={shortestPath} />
      )}
      <ShortestPath changeShortestPath={changeShortestPath} />
    </>
  );
}

export default App;
