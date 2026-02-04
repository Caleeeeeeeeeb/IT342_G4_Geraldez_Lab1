import { useState } from 'react'
import './App.css'

function App() {
  const [form, setForm] = useState({ username: '', email: '', password: '' })
  const [message, setMessage] = useState('')

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value })

  const handleSubmit = async (path) => {
    try {
      const res = await fetch(`/api/auth/${path}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      })
      const text = await res.text()
      setMessage(text)
    } catch (err) {
      setMessage('Error connecting to backend')
    }
  }

  return (
    <div className="app">
      <h1>Auth Demo</h1>

      <div className="form">
        <input name="username" placeholder="Username" value={form.username} onChange={handleChange} />
        <input name="email" placeholder="Email" value={form.email} onChange={handleChange} />
        <input name="password" type="password" placeholder="Password" value={form.password} onChange={handleChange} />

        <div className="buttons">
          <button onClick={() => handleSubmit('register')}>Register</button>
          <button onClick={() => handleSubmit('login')}>Login</button>
        </div>

        {message && <p className="message">{message}</p>}
      </div>

      <p className="note">Dev proxy is configured: requests to <code>/api</code> are forwarded to the backend.</p>
    </div>
  )
}

export default App
