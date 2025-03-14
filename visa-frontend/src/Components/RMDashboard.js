/*import React, { useEffect, useState } from "react";
import { Container, Table, Button, Alert, Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import AppNavbar from "./Navbar";

const RMDashboard = () => {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const navigate = useNavigate();

  const token = localStorage.getItem("token");
  const managerId = localStorage.getItem("empId");

  useEffect(() => {
    if (!token) {
      navigate("/");
      return;
    }
    fetchEmployees();
  }, [navigate, token]);

  const fetchEmployees = async () => {
    setLoading(true);
    try {
      const response = await axios.get(
        `http://localhost:9090/api/dashboard/reporting-manager/${managerId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      console.log("API Response (Employee List):", response.data); // Debugging

       response.data.forEach(emp => {
            console.log(`Employee ${emp.empId}:`, emp.passportDetails?.visaDetails);
            if (!emp.passportDetails?.visaDetails?.visaId) {
              console.warn(`⚠️ Missing visaId for Employee ID ${emp.empId}`);
            }
          });

      setEmployees(response.data);
      setError("");
    } catch (err) {
      console.error("Error fetching employees:", err);
      setError("Failed to load employee applications.");
    }
    setLoading(false);
  };

  const updateVisaStatus = async (visaId, newStatus) => {
    try {
      const response = await axios.put(
        `http://localhost:9090/api/visa/update-status/${visaId}/${newStatus}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );

      console.log("Visa Update API Response:", response.data); // Debugging


      if (response.status === 200) {
        setSuccessMessage(`Visa application ${newStatus.toLowerCase()} successfully!`);
        fetchEmployees(); // Refresh data after update
      }
    } catch (err) {
      setError("Failed to update visa status.");
    }
  };

  return (
    <>
      <AppNavbar />
      <Container className="mt-4">
        <h2 className="text-center mb-3">Reporting Manager Dashboard</h2>
        {error && <Alert variant="danger">{error}</Alert>}
        {successMessage && <Alert variant="success">{successMessage}</Alert>}

        {loading ? (
          <div className="text-center">
            <Spinner animation="border" variant="primary" />
          </div>
        ) : (
          <Table striped bordered hover responsive>
            <thead>
              <tr>
                <th>Employee ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Visa Type</th>
                <th>Visa Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {employees.length === 0 ? (
                <tr>
                  <td colSpan="6" className="text-center">
                    No employees found under your supervision.
                  </td>
                </tr>
              ) : (
                employees.map((emp) => (
                  <tr key={emp.empId}>
                    <td>{emp.empId}</td>
                      <td>
                                          {emp.firstName && emp.lastName
                                            ? `${emp.firstName} ${emp.lastName}`
                                            : "N/A"}
                       </td>
                    <td>{emp.email ||
                    "N/A"}</td>
                    <td>{emp.passportDetails?.visaDetails?.visaType || "Not Applied"}</td>
                    <td>{emp.passportDetails?.visaDetails?.visaStatus || "Pending"}</td>
                    <td>
                      {emp.passportDetails?.visaDetails?.visaStatus !== "Not Applied" ? (
                        <>
                        <Button
                          variant="success"
                          size="sm"
                          className="me-2"
                          onClick={() => {
                            console.log("Clicked Approve for Employee:", emp);

                            const visaId = emp.passportDetails?.visaDetails?.visaId;

                            if (!visaId) {
                              console.error("🚨 Missing visaId! Employee Data:", emp);
                              alert("Visa ID is missing! Cannot update status.");
                              return;
                            }

                            updateVisaStatus(visaId, "APPROVED");
                          }}
                        >
                          Approve
                        </Button>

                        <Button
                          variant="danger"
                          size="sm"
                          onClick={() => {
                            console.log("Clicked Reject for Employee:", emp);

                            const visaId = emp.passportDetails?.visaDetails?.visaId;

                            if (!visaId) {
                              console.error("🚨 Missing visaId! Employee Data:", emp);
                              alert("Visa ID is missing! Cannot update status.");
                              return;
                            }

                            updateVisaStatus(visaId, "REJECTED");
                          }}
                        >
                          Reject
                        </Button>


                        </>
                      ) : (
                        <span className="text-muted">No application</span>
                      )}
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </Table>
        )}
      </Container>
    </>
  );
};

export default RMDashboard;
*/


