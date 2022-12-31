import Navbar from './components/Navbar';
import React, {Suspense, lazy} from "react";
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Loader from "./components/Loader";

//import all pages with lazy import
const Landing = lazy(()=>import("./views/Landing/index"));
const Register = lazy(()=>import("./views/Register/index"));
const Login = lazy(()=>import("./views/Login/index"));
const BuyOffer = lazy(()=>import("./views/BuyOffer/index"));
const Trade = lazy(()=>import("./views/Trade/index"));
const Support = lazy(()=>import("./views/Support/index"));
const BuyerDashboard = lazy(()=>import("./views/BuyerDashboard/index"));
const SellerDashboard = lazy(()=>import("./views/SellerDashboard/index"));
const Receipt = lazy(()=>import("./views/Receipt/index"));
const SellerTrade = lazy(()=>import("./views/SellerTrade/index"));
const SellerOfferDashboard = lazy(()=>import("./views/SellerOfferDashboard/index"));


function App() {
  return (
      <BrowserRouter>
        <div className="App">
          <Navbar></Navbar>
          <div className="content">
              <Suspense fallback={<Loader/>}>
                  <Routes>
                      <Route path="/register" element={<Register/>}/>
                      <Route path="/login" element={<Login/>}/>
                      <Route path="/offer/:id" element={<BuyOffer/>}/>
                      <Route path="/trade/:id" element={<Trade/>}/>
                      <Route path="/support" element={<Support/>}/>
                      <Route path="/buyer/:username" element={<BuyerDashboard/>}/>
                      <Route path="/seller/:username" element={<SellerDashboard/>}/>
                      <Route path="/trade/:id/receipt" element={<Receipt/>}/>
                      <Route path="/chat/:id" element={<SellerTrade/>}/>
                      <Route path="/seller/offer/:id" element={<SellerOfferDashboard/>}/>
                      <Route path="*" element={<Landing/>}/>
                  </Routes>
              </Suspense>
          </div>
        </div>
      </BrowserRouter>
  );
}

export default App; 
