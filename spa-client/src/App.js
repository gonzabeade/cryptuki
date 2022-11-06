import logo from './logo.svg';
import './App.css';
import Navbar from './components/Navbar';
import Landing from './views/Landing';
import React from "react";
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Register from './views/Register';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Navbar></Navbar>
        <div className="content">
        <Routes>
          <Route path="/" element={<Landing/>}/>
          <Route path="/register" element={<Register/>}/>
        </Routes>
        </div>
      </div>
    </BrowserRouter>

  );
}

export default App; 
