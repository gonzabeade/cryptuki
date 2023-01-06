import Navbar from './components/Navbar';
import React, {Suspense, lazy} from "react";
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Loader from "./components/Loader";
import './index.css'
import Error from "./views/Error";
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from 'react-toastify';

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
const UploadAd = lazy(()=>import("./views/UploadAd/index"));
const EditOffer = lazy(()=>import("./views/EditOffer/index"));
const Verify = lazy(()=>import("./views/Verify/index"));




function App() {
  return (
      <BrowserRouter>
        <div className="App">
          <ToastContainer/>
          <Navbar></Navbar>
          <div className="content">
              <Suspense fallback={<Loader/>}>
                  <Routes>
                      <Route path="/register" element={<Register/>}/>
                      <Route path="/login" element={<Login/>}/>
                      <Route path="/offer/:id" element={<BuyOffer/>}/>
                      <Route path="/trade/:id" element={<Trade/>}/>
                      <Route path="/support" element={<Support/>}/>
                      <Route path="/buyer/" element={<BuyerDashboard/>}/>
                      <Route path="/seller/" element={<SellerDashboard/>}/>
                      <Route path="/trade/:id/receipt" element={<Receipt/>}/>
                      <Route path="/chat/:id" element={<SellerTrade/>}/>
                      <Route path="/seller/offer/:id" element={<SellerOfferDashboard/>}/>
                      <Route path="/offer/upload" element={<UploadAd/>}/>
                      <Route path="/offer/:id/edit" element={<EditOffer/>}/>
                      <Route path="/verify" element={<Verify/>}/>
                      <Route path="/" element={<Landing/>}/>
                      <Route path="*" element={<Error message={"No page found"} illustration={"/404.png"}/>}/>
                  </Routes>
              </Suspense>
          </div>
            <div className="shape-blob"></div>
            <div className="shape-blob one"></div>
            <div className="shape-blob two"></div>
            <div className="shape-blob left-[50%]"></div>
            <div className="shape-blob left-[5%] top-[80%]"></div>
        </div>
      </BrowserRouter>
  );
}

export default App; 
