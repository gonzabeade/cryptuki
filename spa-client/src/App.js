import Navbar from './components/Navbar';
import AdminNavBar from './components/AdminNavBar/AdminNavBar';

import React, {Suspense, lazy} from "react";
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Loader from "./components/Loader";
import './index.css'
import Error from "./views/Error";
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from 'react-toastify';
import LoggedGate from "./components/LoggedGate";
import {AuthProvider} from "./contexts/AuthContext";
import ComplaintHub from "./views/ComplaintsLanding/complaintHub";
import SolveComplaint from "./views/SolveComplaint/SolveComplaint";
import SolveKycAdmin from "./views/SolveKycAdmin/SolveKycAdmin";
import KycLanding from "./views/KycLanding/KycLanding";


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
  return (
      <AuthProvider>
      <BrowserRouter>
              <div className="App">
                  <ToastContainer/>
                  {/*<Navbar></Navbar>*/}
                  <AdminNavBar/>
                  <div className="content">
                      <Suspense fallback={<Loader/>}>
                          <Routes>
                              <Route path="/register" element={<Register/>}/>
                              <Route path="/login" element={<Login/>}/>
                              <Route path="/offer/:id" element={<BuyOffer/>}/>
                              <Route path="/trade/:id" element={<LoggedGate><Trade/></LoggedGate>}/>
                              <Route path="/support" element={<Support/>}/>
                              <Route path="/buyer/" element={<LoggedGate><BuyerDashboard/></LoggedGate>}/>
                              <Route path="/seller/" element={<LoggedGate><SellerDashboard/></LoggedGate>}/>
                              <Route path="/trade/:id/receipt" element={<LoggedGate><Receipt/></LoggedGate>}/>
                              <Route path="/chat/:id" element={<LoggedGate><SellerTrade/></LoggedGate>}/>
                              <Route path="/seller/offer/:id"
                                     element={<LoggedGate><SellerOfferDashboard/></LoggedGate>}/>
                              <Route path="/offer/upload" element={<LoggedGate><UploadAd/></LoggedGate>}/>
                              <Route path="/kyc/upload" element={<LoggedGate><UploadKyc/></LoggedGate>}/>
                              <Route path="/offer/:id/edit" element={<LoggedGate><EditOffer/></LoggedGate>}/>
                              <Route path="/verify" element={<Verify/>}/>
                              <Route path="/admin" element={<ComplaintHub/>}/>
                              <Route path="/admin/complaint/:id" element={<SolveComplaint/>}/>
                              <Route path="/admin/kyc" element={<KycLanding/>}/>
                              <Route path="/admin/kyc/:username" element={<SolveKycAdmin/>}/>
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
