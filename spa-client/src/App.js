import logo from './logo.svg';
import './App.css';
import Navbar from './components/Navbar';
import Landing from './views/Landing';
import Login from './views/Login'; 
import React, {Suspense, lazy} from "react";
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Register from './views/Register';
import Loader from "./components/Loader";


//import all pages with lazy import
const Landing = lazy(()=>import("./views/Landing/index"));
const Register = lazy(()=>import("./views/Register/index"));
const Login = lazy(()=>import("./views/Login/index"));

function App() {
  return (
      <BrowserRouter>
        <div className="App">
          <Navbar></Navbar>
          <div className="content">
              <Suspense fallback={<Loader/>}>
                  <Routes>
                      <Route path="/" element={<Landing/>}/>
                      <Route path="/register" element={<Register/>}/>
                      <Route path="/login" element={<Login/>}/>
                  </Routes>
              </Suspense>

          </div>
        </div>
      </BrowserRouter>
  );
}

export default App; 
