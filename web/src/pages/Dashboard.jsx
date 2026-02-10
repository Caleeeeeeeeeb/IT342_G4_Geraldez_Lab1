import React, { useEffect, useState } from "react";
import { getDashboard } from "../services/api";
import { useNavigate } from "react-router-dom";
import '../styles/dashboard.css'; // Use this for smoother redirects

function Dashboard() {
  const [data, setData] = useState("");
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate(); // Initialize hook

  useEffect(() => {
    let mounted = true;
    
    // 1. Check if token exists immediately
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "/login"; // Kick them out instantly
        return;
    }

    async function load() {
      try {
        const d = await getDashboard();
        
        // 2. CHECK: If backend returned nothing (401 Unauthorized), kick them out
        if (!d) {
             localStorage.removeItem("token"); // Clean up invalid token
             window.location.href = "/login";
             return;
        }

        if (mounted) setData(d);
        
      } catch (err) {
        // 3. If API crashes, kick them out
        localStorage.removeItem("token");
        window.location.href = "/login";
      } finally {
        if (mounted) setLoading(false);
      }
    }
    
    load();
    return () => (mounted = false);
  }, []);

  function logout() {
    localStorage.removeItem("token");
    navigate("/login"); // Smoother logout
  }

  return (
    <div>
      <div className="topbar">
        <h3 style={{margin:0}}>Dashboard</h3>
        <div>
          <a href="/profile" style={{marginRight:12}}>Profile</a>
          <button className="btn secondary" onClick={logout}>Logout</button>
        </div>
      </div>
      
      <div className="card-centered">
        <h4>Overview</h4>
        {loading ? <p className="muted">Loading...</p> : <p>{data}</p>}
      </div>
    </div>
  );
}

export default Dashboard;