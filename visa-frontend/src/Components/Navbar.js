import React from "react";
import { Navbar, Container, Button } from "react-bootstrap";
import { FaPassport } from "react-icons/fa";
import { useNavigate, useLocation } from "react-router-dom";

const AppNavbar = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => {
    localStorage.removeItem("token"); // ✅ Clear token
    navigate("/"); // ✅ Redirect to login
  };

  return (
    <Navbar bg="dark" variant="dark">
      <Container>
        <Navbar.Brand href="/">
          <FaPassport className="me-2" /> H1B Portal
        </Navbar.Brand>
        {location.pathname === "/dashboard" && (
          <Button variant="danger" onClick={handleLogout}>
            Logout
          </Button>
        )}
      </Container>
    </Navbar>
  );
};

export default AppNavbar;
