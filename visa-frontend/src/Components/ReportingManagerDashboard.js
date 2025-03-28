import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Table, Button, Container, Alert, Spinner, Form } from "react-bootstrap";
import axios from "axios";
import AppNavbar from "./Navbar";
import "../styles/dashboardStyles.css";

const ReportingManagerDashboard = () => {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [searchEmpId, setSearchEmpId] = useState(""); // 🔍 Search State
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const managerId = localStorage.getItem("empId");

  useEffect(() => {
    if (!token) {
      navigate("/"); // Redirect to login if no token
      return;
    }
    fetchApplications();
  }, [navigate, token, searchEmpId]);

  // ✅ Fetch applications for the logged-in manager
  const fetchApplications = async () => {
    setLoading(true);
    setError("");
    try {
      const url =
        searchEmpId.trim() !== ""
          ? `http://localhost:9090/api/visa/search?empId=${searchEmpId}`
          : "http://localhost:9090/api/visa/manager-applications";

      const response = await axios.get(url, {
        headers: { Authorization: `Bearer ${token}` },
      });

      setApplications(response.data);
    } catch (error) {
      console.error("Error fetching applications:", error);
      setError("❌ Failed to load applications.");
    } finally {
      setLoading(false);
    }
  };


  // ✅ Handle Approve/Reject actions
  const handleDecision = async (empId, decision) => {
    setError("");
    try {
      await axios.put(
        `http://localhost:9090/api/visa/update-status/${empId}?status=${decision}`,
        null,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setApplications((prev) => prev.filter((app) => app.empId !== empId)); // ✅ Remove application from list after update
      alert(`✅ Visa application ${decision} successfully!`);
    } catch (error) {
      console.error("Error updating status:", error);
      setError("❌ You are not authorized to approve/reject this application.");
    }
  };

   const filteredApplications = applications.filter(app =>
      app.empId.includes(searchEmpId)
    );

  return (
    <>
      <AppNavbar />
      <Container className="mt-5">
        <h2 className="text-center text-primary mb-4">Applications Review</h2>

                <Form.Group controlId="searchEmpId">
                  <Form.Label>Search by Employee ID:</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Enter Employee ID"
                    value={searchEmpId}
                    onChange={(e) => setSearchEmpId(e.target.value)}
                  />
                </Form.Group>


        {error && <Alert variant="danger" className="text-center">{error}</Alert>}

        {loading ? (
          <div className="text-center">
            <Spinner animation="border" variant="primary" />
            <p>Loading applications...</p>
          </div>
        ) : applications.length === 0 ? (
          <Alert variant="info" className="text-center">✅ No pending applications to review.</Alert>
        ) : (
          <Table striped bordered hover responsive className="mt-3">
            <thead className="table-dark">
              <tr>
                <th>Employee ID</th>
                <th>Full Name</th>
                <th>Passport Number</th>
                <th>Visa Type</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {applications.map((app) => (
                <tr key={app.empId}>
                  <td>{app.empId}</td>
                  <td>{app.fullName}</td>
                  <td>{app.passportNumber}</td>
                  <td>{app.visaType}</td>
                  <td className="text-center">
                    <Button
                      variant="success"
                      className="me-2"
                      onClick={() => handleDecision(app.empId, "APPROVED")}
                    >
                      Approve
                    </Button>
                    <Button
                      variant="danger"
                      onClick={() => handleDecision(app.empId, "REJECTED")}
                    >
                      Reject
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        )}
      </Container>
    </>
  );
};

export default ReportingManagerDashboard;


