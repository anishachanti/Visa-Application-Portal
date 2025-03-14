import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from "./Components/Login";
import Dashboard from "./Components/Dashboard";
import VisaApplicationForm from "./Components/VisaApplicationForm";
import ReportingManagerDashboard from "./Components/ReportingManagerDashboard";

const PrivateRoute = ({ children }) => {
  const token = localStorage.getItem("token");
  return token ? children : <Navigate to="/" />;
};

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
        <Route path="/apply-visa" element={<PrivateRoute><VisaApplicationForm /></PrivateRoute>} />
        <Route path="/manager-dashboard" element={<PrivateRoute><ReportingManagerDashboard /></PrivateRoute>} /> {/* ✅ Added Manager Dashboard */}
      </Routes>
    </Router>
  );
};

export default App;
