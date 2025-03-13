import React, { useEffect, useState } from "react";
import { Navbar, Container, Button } from "react-bootstrap";
import { FaPassport } from "react-icons/fa";
import { useNavigate, useLocation } from "react-router-dom";

const AppNavbar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    // ✅ Check if user is logged in (token exists)
    const token = localStorage.getItem("token");
    setIsAuthenticated(!!token);
  }, [location]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("empId");
    navigate("/");
  };

  return (
    <Navbar bg="dark" variant="dark">
      <Container>
        <Navbar.Brand
          onClick={() => isAuthenticated && navigate("/dashboard")}
          style={{ cursor: isAuthenticated ? "pointer" : "default" }}
        >
          <FaPassport className="me-2" /> H1B Portal
        </Navbar.Brand>

        {isAuthenticated && (
          <Button variant="danger" onClick={handleLogout}>
            Logout
          </Button>
        )}
      </Container>
    </Navbar>
  );
};

export default AppNavbar;