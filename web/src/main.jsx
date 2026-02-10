import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './styles/global.css'
import { BrowserRouter } from 'react-router-dom' // <--- MUST BE HERE

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter> {/* <--- WRAPPING THE APP */}
      <App />
    </BrowserRouter>
  </React.StrictMode>,
)