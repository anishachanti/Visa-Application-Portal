import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Form, Button, Card, Container, Alert } from "react-bootstrap";
import axios from "axios";
import AppNavbar from "./Navbar";

const Login = () => {
  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    setError(""); // Clear previous error messages
    if (!userId || !password) {
      setError("⚠️ Please enter both Employee ID and Password.");
      return;
    }

    try {
      const response = await axios.post("http://localhost:9090/api/auth/login", {
        empId: userId,
        password: password,
      });

      if (response.status === 200 && response.data) {
        console.log("Login successful:", response.data);

        console.log("Assigned Role:", response.data.role);

        // ✅ Save token, role, and employee ID in localStorage
        localStorage.setItem("token", response.data.token);
        localStorage.setItem("role", response.data.role);
        localStorage.setItem("empId", response.data.empId);

        navigate("/dashboard");
      } else {
        setError("❌ Unexpected error. Please try again.");
      }
    } catch (err) {
      console.error("Login failed:", err);
      setError("❌ Invalid Employee ID or Password.");
    }
  };

  return (
    <>
      <AppNavbar />
      <Container className="d-flex justify-content-center align-items-center vh-100">
        <Card style={{ width: "400px", padding: "20px", borderRadius: "10px", boxShadow: "0px 4px 10px rgba(0,0,0,0.2)" }}>
          <Card.Body>
            <h2 className="text-center mb-4">Login</h2>
            <Form>
              <Form.Group className="mb-3">
                <Form.Label>User ID</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter your Employee ID"
                  value={userId}
                  onChange={(e) => setUserId(e.target.value)}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Password</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Enter your Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </Form.Group>

              {error && <Alert variant="danger" className="text-center">{error}</Alert>}

              <Button variant="primary" className="w-100" onClick={handleLogin}>
                Login
              </Button>
            </Form>
          </Card.Body>
        </Card>
      </Container>
    </>
  );
};

export default Login;
