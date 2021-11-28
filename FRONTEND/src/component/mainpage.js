import React, { useEffect } from "react";
import axios from "axios";

export default function MainPage() {
  const [apidata, setapidata] = React.useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const res = await axios.get("http://localhost:8080/dataauthmultiple");
        setapidata(res.data);
      } catch (err) {
        console.log(err);
      }
    }
    fetchData();
  }, []);
  return (
    <div>
      {apidata.map((ticket, index) => (
        <p key={index}>{ticket.subject}</p>
      ))}
    </div>
  );
}
