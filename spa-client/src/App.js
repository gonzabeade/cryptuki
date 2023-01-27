import Navbar from './components/Navbar';
import AdminNavBar from './components/AdminNavBar/AdminNavBar';

import React, {Suspense, lazy, useState} from "react";
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Loader from "./components/Loader";
import './index.css'
import Error from "./views/Error";
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from 'react-toastify';
import LoggedGate from "./components/LoggedGate";
import {AuthProvider} from "./contexts/AuthContext";
import ComplaintHub from "./views/ComplaintsLanding/ComplaintHub";
import SolveComplaint from "./views/SolveComplaint/SolveComplaint";
import SolveKycAdmin from "./views/SolveKycAdmin/SolveKycAdmin";
import KycLanding from "./views/KycLanding/KycLanding";
import useUserService from "./hooks/useUserService";
import NavbarAll from "./components/NavbarAll";


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
const UploadKyc = lazy(()=>import("./views/UploadKyc/index"));
const EditOffer = lazy(()=>import("./views/EditOffer/index"));
const Verify = lazy(()=>import("./views/Verify/index"));




function App() {
    const userService = useUserService();
  return (
      <AuthProvider>
      <BrowserRouter>
              <div className="App">
                  <ToastContainer/>
                  <NavbarAll/>
                  <div className="content">
                      <Suspense fallback={<Loader/>}>
                          <Routes>
                              <Route path="/register" element={<Register/>}/>
                              <Route path="/login" element={<Login/>}/>
                              <Route path="/offer/:id" element={<BuyOffer/>}/>
                              <Route path="/trade/:id" element={<LoggedGate children={<Trade/>}/>}/>
                                <Route path="/trade/:id/support" element={<Support/>}/>
                              <Route path="/buyer/" element={<LoggedGate children={<BuyerDashboard/>} />}/>
                              <Route path="/seller/" element={<LoggedGate children={<SellerDashboard/>}/>}/>
                              <Route path="/trade/:id/receipt" element={<LoggedGate children={<Receipt/>}/>}/>
                              <Route path="/chat/:id" element={<LoggedGate children={<SellerTrade/>}/>}/>
                              <Route path="/seller/offer/:id"
                                     element={<LoggedGate children={<SellerOfferDashboard/>}/>}/>
                              <Route path="/offer/upload" element={<LoggedGate children={<UploadAd/>}/>}/>
                              <Route path="/kyc/upload" element={<LoggedGate children={<UploadKyc/>}/>}/>
                              <Route path="/offer/:id/edit" element={<LoggedGate children={<EditOffer/>}/>}/>
                              <Route path="/verify" element={<Verify/>}/>
                              <Route path="/admin" element={<LoggedGate children={<ComplaintHub/>} admin={true} />} />
                              <Route path="/admin/complaint/:id" element={<LoggedGate children={<SolveComplaint/>} admin={true}/>}/>
                              <Route path="/admin/kyc" element={<LoggedGate children={<KycLanding/>} admin={true}/>}/>
                              <Route path="/admin/kyc/:username" element={<LoggedGate children={<SolveKycAdmin/>} admin={true}/> }/>
                              <Route path="/forbidden" element={<Error message={"Forbidden action"} illustration={"/images/403.png"}/>}/>
                              <Route path="/" element={<Landing/>}/>
                              <Route path="*" element={<Error message={"No page found"} illustration={"/images/404.png"}/>}/>
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
      </AuthProvider>

  );
}

export default App; 
