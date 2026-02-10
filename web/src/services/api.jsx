const API_URL = "http://localhost:8080/api/auth";

export async function login(username, password) {
  const res = await fetch(`${API_URL}/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password })
  });
  return res.json();
}

export async function register(username, password) {
  const res = await fetch(`${API_URL}/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password })
  });
  return res.json();
}

export async function getDashboard() {
  const token = localStorage.getItem("token");
  const res = await fetch(`${API_URL}/dashboard`, {
    headers: { "Authorization": `Bearer ${token}` }
  });
  return res.text();
}


export async function getProfile() {
  const token = localStorage.getItem("token");
  const res = await fetch("http://localhost:8080/api/auth/profile", {
    headers: { "Authorization": `Bearer ${token}` }
  });
  return res.json();
}
