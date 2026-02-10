import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';

// FIX: Update imports to point to the "pages" folder
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Profile from './pages/Profile';

function App() {
  return (
    <Routes>
      {/* Redirect root URL to Login */}
      <Route path="/" element={<Navigate to="/login" />} />
      
      {/* Define the pages */}
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/dashboard" element={<Dashboard />} />
      <Route path="/profile" element={<Profile />} />
      
      {/* Catch-all for 404s */}
      <Route path="*" element={<Navigate to="/login" />} />
    </Routes>
  );
}

export default App;