import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from "./Components/Login";
import Dboard from "./Components/Dboard";
import VisaAppForm from "./Components/VisaAppForm";
import RMDashboard from "./Components/RMDashboard";

const PrivateRoute = ({ children }) => {
  const token = localStorage.getItem("token");
  return token ? children : <Navigate to="/" />;
};

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={<PrivateRoute><Dboard /></PrivateRoute>} />
        <Route path="/apply-visa" element={<PrivateRoute><VisaAppForm /></PrivateRoute>} />
        <Route path="/manager-dashboard" element={<PrivateRoute><RMDashboard /></PrivateRoute>} />
      </Routes>
    </Router>
  );
};

export default App;
