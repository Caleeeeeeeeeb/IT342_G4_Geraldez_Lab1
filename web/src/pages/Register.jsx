import React, { useState } from "react";
import { register } from "../services/api";
import { useNavigate } from "react-router-dom";
import '../styles/auth.css'; // 1. Import this

function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate(); // 2. Initialize the hook

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    setSuccess("");
    setLoading(true);

    try {
      // Your API returns the full User object { id: 1, username: "..." }
      const data = await register(username, password);

      if (data && data.username) {
        setSuccess("Registration successful — redirecting to login...");
        
        // 3. Use navigate instead of window.location
        setTimeout(() => {
            navigate("/login"); 
        }, 1200);

      } else {
        setError("Registration failed. Please try again.");
      }
    } catch (err) {
      setError("Network error or server is down.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="auth-container">
      <h2>Create an account</h2>
      <p className="muted">Start a free account to access the dashboard.</p>
      
      {/* These handle the feedback nicely */}
      {error && <div className="error" style={{color: 'red'}}>{error}</div>}
      {success && <div className="success" style={{color: 'green'}}>{success}</div>}
      
      <form onSubmit={handleSubmit}>
        <div className="field">
          <label>Username</label>
          <input 
            className="input" 
            value={username} 
            onChange={(e) => setUsername(e.target.value)} 
            required 
          />
        </div>
        <div className="field">
          <label>Password</label>
          <input 
            type="password" 
            className="input" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            required 
          />
        </div>
        <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginTop: 12 }}>
          <button className="btn" disabled={loading}>
            {loading ? 'Creating...' : 'Create account'}
          </button>
          {/* Use Link instead of <a href> for instant navigation */}
          <span className="muted" onClick={() => navigate("/login")} style={{cursor: "pointer"}}>
             Already have an account?
          </span>
        </div>
      </form>
    </div>
  );
}

export default Register;