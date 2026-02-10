import React, { useState } from "react";
import { login } from "../services/api";
import { useNavigate } from "react-router-dom";
import '../styles/auth.css'; // 1. Import Hook

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate(); // 2. Initialize Hook

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const data = await login(username, password);

      // 3. Success! Save token and move to dashboard
      if (data && data.token) {
        localStorage.setItem("token", data.token); // THIS is the key to staying logged in!
        navigate("/dashboard"); // Instant redirect
      } 
    } catch (err) {
      // 4. If api.js throws an error, it means 401 Unauthorized (Wrong Password)
      console.error(err);
      setError("Invalid username or password"); 
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="auth-container">
      <h2>Welcome back</h2>
      <p className="muted">Sign in to continue to your dashboard.</p>
      
      {/* Red error box */}
      {error && <div className="error" style={{color: 'red', marginBottom: '10px'}}>{error}</div>}
      
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
            {loading ? 'Signing in...' : 'Sign in'}
          </button>
          
          {/* Use standard HTML link or useNavigate for register too */}
          <span 
            className="muted" 
            onClick={() => navigate("/register")} 
            style={{cursor: "pointer", textDecoration: "underline"}}
          >
            Create account
          </span>
        </div>
      </form>
    </div>
  );
}

export default Login;