import logo from './logo.svg';
import './App.css';
import Navbar from './components/Navbar';
import Landing from './views/Landing';

import React from "react";
import {BrowserRouter, Route, Switch} from 'react-router-dom';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
      <Navbar></Navbar>
      <div className="content">
        <Landing></Landing>
        {/* <Route path="/" element={<Landing/>}></Route> */}
      </div>
    </div>

    </BrowserRouter>

  );
}

export default App; 
