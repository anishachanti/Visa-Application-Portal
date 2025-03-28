import React, { useEffect, useState } from "react";
import { Table, Button, Container, Form, Spinner, Alert } from "react-bootstrap";
import axios from "axios";
import "../styles/dashboardStyles.css";

const VisaProcessingDashboard = () => {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [searchEmpId, setSearchEmpId] = useState("");
  const [updatedStatuses, setUpdatedStatuses] = useState({});

  useEffect(() => {
    fetchApplications();
  }, []);

  const fetchApplications = async () => {
    const token = localStorage.getItem("token");

    try {
      const response = await axios.get(`http://localhost:9090/api/visa/visa-team-applications?timestamp=${new Date().getTime()}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      setApplications(response.data);
    } catch (error) {
      setError("❌ Failed to load applications.");
      console.log("Error fetching applications:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleStatusChange = (empId, newStatus) => {
    setUpdatedStatuses((prev) => ({ ...prev, [empId]: newStatus }));
  };

  const submitUpdate = async (empId) => {
    if (!updatedStatuses[empId]) return; // Don't update if no change

    const token = localStorage.getItem("token");
    try {
        await axios.put(
          `http://localhost:9090/api/visa/update-status/${empId}?status=${updatedStatuses[empId]}`, // ✅ empId in URL, status as query param
          null,  // ✅ No request body needed
          { headers: { Authorization: `Bearer ${token}` } }
        );

      alert(`✅ Status updated for Employee ID: ${empId}`);

      /*if (["Petition Approved", "Petition Rejected"].includes(updatedStatuses[empId])) {
              setApplications(applications.filter((app) => app.empId !== empId));
            } else {
              fetchApplications(); // ✅ Refresh data for other cases
            }
       */



      fetchApplications(); // Refresh data*/


      setUpdatedStatuses((prev) => {
        const newStatuses = { ...prev };
        delete newStatuses[empId]; // Remove updated status after submission
        return newStatuses;
      });
    } catch (error) {
      alert("❌ Error updating visa status.");
      console.log("Update error:", error);
    }
  };

  return (
    <Container className="mt-5">
      <h2 className="text-center text-primary mb-4">Visa Processing Team Dashboard</h2>

      <Form.Group controlId="searchEmpId" className="mb-3">
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
        </div>
      ) : applications.length === 0 ? (
        <Alert variant="warning" className="text-center">
          No approved visa applications found.
        </Alert>
      ) : (
        <Table striped bordered hover responsive className="mt-3">
          <thead className="table-dark">
            <tr>
              <th>Employee ID</th>
              <th>Full Name</th>
              <th>Visa Type</th>
              <th>Date Approved</th>
              <th>Status</th>
              <th>Action</th> {/* ✅ New column for Submit button */}
            </tr>
          </thead>
          <tbody>
            {applications
              .filter((app) => app.empId.includes(searchEmpId))
              .map((app) => (
                <tr key={app.empId}>
                  <td>{app.empId}</td>
                  <td>{app.fullName}</td>
                  <td>{app.visaType}</td>
                  <td>{app.dateApproved ? new Date(app.dateApproved).toLocaleDateString() : "N/A"}</td>

                  <td>
                    <Form.Select
                      value={updatedStatuses[app.empId] || app.status}
                      onChange={(e) => handleStatusChange(app.empId, e.target.value)}
                    >
                      <option value="Approved">Approved</option>
                      <option value="Petition Submitted">Petition Submitted</option>
                      <option value="Petition Approved">Petition Approved</option>
                      <option value="Petition Rejected">Petition Rejected</option>
                    </Form.Select>
                  </td>
                  <td>
                    <Button
                      variant="success"
                      size="sm"
                      onClick={() => submitUpdate(app.empId)}
                      disabled={!updatedStatuses[app.empId]}
                    >
                      Submit
                    </Button>
                  </td>
                </tr>
              ))}
          </tbody>
        </Table>
      )}
    </Container>
  );
};

export default VisaProcessingDashboard;
