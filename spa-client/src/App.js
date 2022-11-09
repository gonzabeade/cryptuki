import logo from './logo.svg';
import './App.css';
import Navbar from './components/Navbar';
import Landing from './views/Landing';
import Login from './views/Login'; 
import React from "react";
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Register from './views/Register';
import { AuthProvider } from './contexts/AuthContext';
import { AxiosProvider } from './contexts/AxiosContext';

function App() {
  return (
    <AuthProvider>
      <AxiosProvider>
        <BrowserRouter>
          <div className="App">
            <Navbar></Navbar>
            <div className="content">
            <Routes>
              <Route path="/" element={<Landing/>}/>
              <Route path="/register" element={<Register/>}/>
              <Route path="/login" element={<Login/>}/>
            </Routes>
            </div>
          </div>
        </BrowserRouter>
      </AxiosProvider>
    </AuthProvider>
  );
}

export default App; 
