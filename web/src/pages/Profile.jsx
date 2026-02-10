import React, { useEffect, useState } from "react";
import { getProfile } from "../services/api";
import { useNavigate } from "react-router-dom";
import '../styles/dashboard.css'; // Recommended for smoother redirects

function Profile() {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    let mounted = true;

    async function load() {
      try {
        const u = await getProfile();

        // 1. Security Check: If API returns null/error, kick them out
        if (!u || !u.username) {
            localStorage.removeItem("token"); // Clear bad token
            window.location.href = "/login";
            return;
        }

        if (mounted) setUser(u);
      } catch (err) {
        // 2. Network/Crash Check
        localStorage.removeItem("token");
        window.location.href = "/login";
      }
    }
    load();
    return () => (mounted = false);
  }, []);

  if (!user) {
    return <div className="card-centered"><p className="muted">Loading profile...</p></div>;
  }

  return (
    <div className="card-centered">
      <div style={{display:'flex',justifyContent:'space-between',alignItems:'center'}}>
        <h3 style={{margin:0}}>Profile</h3>
        {/* Use navigate to go back without reloading */}
        <button onClick={() => navigate("/dashboard")} className="btn secondary" style={{padding: "5px 10px"}}>Back</button>
      </div>

      <div style={{marginTop:12}} className="profile-row">
        
        <div style={{marginBottom: 10}}>
          <strong>ID</strong>
          <div className="muted">#{user.id}</div>
        </div>

        <div style={{marginBottom: 10}}>
          <strong>Username</strong>
          <div className="muted">{user.username}</div>
        </div>

        <div>
          <strong>Role</strong>
          <div className="muted">{user.role}</div>
        </div>

      </div>
    </div>
  );
}

export default Profile;