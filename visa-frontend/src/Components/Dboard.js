import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Card, Button, Alert, Row, Col } from "react-bootstrap";
import axios from "axios";
import AppNavbar from "./Navbar";

const Dboard = () => {
  const [userId, setUserId] = useState("");
  const [role, setRole] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    const storedRole = localStorage.getItem("role");
    const storedUserId = localStorage.getItem("empId");

    if (!token) {
      navigate("/");
      return;
    }

    setUserId(storedUserId || "Unknown User");
    setRole(storedRole || "EMPLOYEE");

    // Prevent API call if no userId is found
    if (!storedUserId) return;

    axios
      .get(`http://localhost:9090/api/dashboard/employee/${storedUserId}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        if (response.data && response.data.empId) {
          setUserId(response.data.empId);
        }
      })
      .catch(() => {
        setError("Failed to load dashboard data");
      });
  }, [navigate]);

  return (
    <>
      <AppNavbar />
      <Container className="mt-4">
        {/* Buttons for Reporting Manager */}
        {role === "MANAGER" && (
          <Row className="mb-4 justify-content-start">
            <Col xs={12} md={4}>
              <Button
                variant="success"
                className="w-100 py-3 fw-bold"
                style={{ fontSize: "1.2rem", borderRadius: "10px" }}
                onClick={() => navigate("/apply-visa")}
              >
                Apply for Visa
              </Button>
            </Col>
            <Col xs={12} md={4}>
              <Button
                variant="primary"
                className="w-100 py-3 fw-bold"
                style={{ fontSize: "1.2rem", borderRadius: "10px" }}
                onClick={() => navigate("/manager-dashboard")}
              >
                Manager Dashboard
              </Button>
            </Col>
          </Row>
        )}

        {/* Dashboard Card */}
        <Container className="d-flex justify-content-center align-items-center min-vh-50">
          <Card
            style={{
              width: "450px",
              padding: "20px",
              borderRadius: "12px",
              boxShadow: "0px 4px 15px rgba(0,0,0,0.2)",
              backgroundColor: "#ffffff",
            }}
          >
            <Card.Body className="text-center">
              <h2 className="mb-3" style={{ color: "#007bff", fontWeight: "bold" }}>
                H1B Dashboard
              </h2>
              <hr />
              {error ? (
                <Alert variant="danger">{error}</Alert>
              ) : (
                <p className="mt-3">
                  <strong>User ID:</strong> {userId}
                  <br />
                  <strong>Role:</strong> {role === "MANAGER" ? "Reporting Manager" : "Employee"}
                </p>
              )}

              {/* Employee View: Only Apply for Visa */}
              {role === "EMPLOYEE" && (
                <Button
                  variant="success"
                  className="mt-3 w-100 py-3 fw-bold"
                  style={{ fontSize: "1.2rem", borderRadius: "10px" }}
                  onClick={() => navigate("/apply-visa")}
                >
                  Apply for Visa
                </Button>
              )}
            </Card.Body>
          </Card>
        </Container>
      </Container>
    </>
  );
};

export default Dboard;
