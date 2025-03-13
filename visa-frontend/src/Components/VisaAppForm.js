import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Form, Button, Container, Card, Modal, Alert } from "react-bootstrap";
import axios from "axios";
import AppNavbar from "./Navbar";

const countries = [
  "Afghanistan", "Albania", "Algeria", "Andorra", "Angola",
    "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan",
    "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus",
    "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Brazil",
    "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon",
    "Canada", "Chile", "China", "Colombia", "Comoros", "Congo",
    "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic",
    "Denmark", "Djibouti", "Dominica", "Dominican Republic",
    "Ecuador", "Egypt", "El Salvador", "Estonia", "Ethiopia",
    "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia",
    "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea",
    "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran",
    "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan",
    "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait",
    "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho",
    "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg",
    "Malaysia", "Maldives", "Mali", "Malta", "Mexico", "Moldova",
    "Monaco", "Mongolia", "Montenegro", "Morocco", "Myanmar",
    "Namibia", "Nepal", "Netherlands", "New Zealand", "Nicaragua",
    "Niger", "Nigeria", "North Macedonia", "Norway", "Oman",
    "Pakistan", "Panama", "Paraguay", "Peru", "Philippines",
    "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda",
    "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Singapore",
    "Slovakia", "Slovenia", "South Africa", "Spain", "Sri Lanka",
    "Sudan", "Sweden", "Switzerland", "Syria", "Taiwan",
    "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga",
    "Trinidad and Tobago", "Tunisia", "Turkey", "Uganda",
    "Ukraine", "United Arab Emirates", "United Kingdom",
    "United States", "Uruguay", "Uzbekistan", "Vatican City",
    "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"
];

const VisaAppForm = () => {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const empId = localStorage.getItem("empId");

  const [formData, setFormData] = useState({
    firstName: "",
    middleName: "",
    lastName: "",
    designation: "",
    email: "",
    dob: "",
    rmId: "",
    nationality: "",
    passportNo: "",
    passportIssuedDate: "",
    passportExpiryDate: "",
    countryOfBirth: "",
    cityOfBirth: "",
    citizenship: "",
    educationQualification: "",
    startYear: "",
    endYear: "",
    percentage: "",
    institutionUniversityName: "",
    masterGPA: "",
    visaType: "",
    petitionNumber: "",
    addressType: "",
    city: "",
    state: "",
    country: "",
    village: "",
    street: "",
    doorNo: "",
    landMark: "",
    passportFile: null,
    msDegreeFile: null
  });

  const [showMastersModal, setShowMastersModal] = useState(false);
  const [errors, setErrors] = useState({});
  const [success, setSuccess] = useState("");

  useEffect(() => {
    if (!token) {
      navigate("/");
    }
  }, [navigate, token]);

  const handleChange = (e) => {
    const { name, value, type, checked, files } = e.target;
    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : (type === "file" ? files[0] : value)
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    console.log("Submitting Form Data:", formData); // 🔍 Log form data
    debugger;

     let newErrors = {};

   if (!/^\d+$/.test(empId)) newErrors.empId = "⚠ Employee ID must be numeric.";
       if (!/^[A-Za-z\s]+$/.test(formData.firstName)) newErrors.firstName = "⚠ Only alphabets allowed.";
       if (!/^[A-Za-z\s]+$/.test(formData.lastName)) newErrors.lastName = "⚠ Only alphabets allowed.";
       if (!/^[A-Za-z]\d{7}$/.test(formData.passportNo)) newErrors.passportNo = "⚠ Format: Letter + 7 digits.";
       if (!formData.nationality) newErrors.nationality = "⚠ Nationality is required.";
       if (!formData.visaType) newErrors.visaType = "⚠ Visa Type is required.";
       if (!formData.citizenship) newErrors.citizenship = "⚠ Citizenship is required.";

       // Date Validations
       const issueDate = new Date(formData.passportIssuedDate);
       const expiryDate = new Date(formData.passportExpiryDate);
       if (issueDate >= expiryDate) {
         newErrors.passportExpiryDate = "⚠ Expiry Date must be after Issue Date.";
       }

       // Age Restriction for H1B Visa
       const birthDate = new Date(formData.dob);
       const today = new Date();
       let age = today.getFullYear() - birthDate.getFullYear();
       const monthDiff = today.getMonth() - birthDate.getMonth();
       if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
         age--;
       }
       if (formData.visaType === "H1B" && age < 18) {
         newErrors.visaType = "❌ Must be 18+ for H1B visa.";
       }

       // File Validations
       if (formData.passportFile && formData.passportFile.type !== "application/pdf") {
         newErrors.passportFile = "⚠ Passport file must be a PDF.";
       }
       if (formData.msDegreeFile && formData.msDegreeFile.type !== "application/pdf") {
         newErrors.msDegreeFile = "⚠ MS Degree file must be a PDF.";
       }

    setErrors(newErrors);
    if (Object.keys(newErrors).length > 0) return;

    const formDataToSend = new FormData();
    formDataToSend.append("employeeDetails", new Blob(
      [JSON.stringify({
        empId,
        firstName: formData.firstName,
        middleName: formData.middleName,
        lastName: formData.lastName,
        designation: formData.designation,
        email: formData.email,
        dob: formData.dob,
        rmId: formData.rmId
      })], { type: "application/json" }));

    formDataToSend.append("passportDetails", new Blob(
      [JSON.stringify({
        passportNo: formData.passportNo,
        nationality: formData.nationality,
        passportIssuedDate: formData.passportIssuedDate,
        passportExpiryDate: formData.passportExpiryDate,
        countryOfBirth: formData.countryOfBirth,
        cityOfBirth: formData.cityOfBirth,
        citizenship: formData.citizenship
      })], { type: "application/json" }));

    formDataToSend.append("educationDetails", new Blob(
      [JSON.stringify({
        educationQualification: formData.educationQualification,
        startYear: formData.startYear,
        endYear: formData.endYear,
        percentage: formData.percentage,
        institutionUniversityName: formData.institutionUniversityName,
        masterGPA: formData.masterGPA
      })], { type: "application/json" }));

    formDataToSend.append("visaDetails", new Blob(
      [JSON.stringify({
        visaType: formData.visaType,
        petitionNumber: formData.petitionNumber
      })], { type: "application/json" }));

    formDataToSend.append("employeeAddress", new Blob(
      [JSON.stringify({
        addressType: formData.addressType,
        city: formData.city,
        state: formData.state,
        country: formData.country,
        village: formData.village,
        street: formData.street,
        doorNo: formData.doorNo,
        landMark: formData.landMark
      })], { type: "application/json" }));

    formDataToSend.append("passportFile", formData.passportFile);
    if (formData.msDegreeFile) formDataToSend.append("msDegreeFile", formData.msDegreeFile);

    try {
      const response = await axios.post(`http://localhost:9090/api/visa/apply/${formData.passportNo}`, formDataToSend, {
        headers: { Authorization: `Bearer ${token}`, "Content-Type": "multipart/form-data" },
      });

      console.log("✅ API Response:", response.data);
      debugger;

      if (response.status === 200) {
        setSuccess("✅ Visa application submitted successfully!");
        setErrors({});
        setTimeout(() => navigate("/dashboard"), 2000);
      }
    } catch (err) {
      console.error("❌ API Error:", err);
      debugger;

      setErrors({ formSubmit: "❌ Failed to submit visa application." });
      setSuccess("");
    }
  };

  return (
    <>
      <AppNavbar />
      <Container className="d-flex justify-content-center align-items-center min-vh-100">
        <Card className="form-card">
          <Card.Body>
            <h2 className="text-center mb-3">Visa Application Form</h2>
            {errors.formSubmit && <Alert variant="danger" className="text-center">{errors.formSubmit}</Alert>}
            {success && <Alert variant="success" className="text-center">{success}</Alert>}
            <Form onSubmit={handleSubmit}>
              <Form.Group className="mb-3">
                <Form.Label>First Name</Form.Label>
                <Form.Control type="text" name="firstName" value={formData.firstName} onChange={handleChange} required />
                {errors.firstName && <p className="text-danger">{errors.firstName}</p>}
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Passport Number</Form.Label>
                <Form.Control type="text" name="passportNo" value={formData.passportNo} onChange={handleChange} required />
                {errors.passportNo && <p className="text-danger">{errors.passportNo}</p>}
              </Form.Group>

              {/* Passport Dates */}
                            <Form.Group className="mb-3">
                              <Form.Label>Passport Issued Date</Form.Label>
                              <Form.Control type="date" name="passportIssuedDate" value={formData.passportIssuedDate} onChange={handleChange} required />
                            </Form.Group>

                            <Form.Group className="mb-3">
                              <Form.Label>Passport Expiry Date</Form.Label>
                              <Form.Control type="date" name="passportExpiryDate" value={formData.passportExpiryDate} onChange={handleChange} required />
                            </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Nationality</Form.Label>
                <Form.Control as="select" name="nationality" value={formData.nationality} onChange={handleChange} required>
                  <option value="">--Select Nationality--</option>
                  {countries.map((country, index) => (
                    <option key={index} value={country}>{country}</option>
                  ))}
                </Form.Control>
                {errors.nationality && <p className="text-danger">{errors.nationality}</p>}
              </Form.Group>

              <Form.Group className="mb-3">
                              <Form.Label>Visa Type</Form.Label>
                              <Form.Control
                                as="select"
                                name="visaType"
                                value={formData.visaType}
                                onChange={handleChange}
                                required
                              >
                                <option value="">Select Visa Type</option>
                                <option value="H1B">H1B</option>
                                <option value="L1">L1</option>
                                <option value="F1">F1</option>
                              </Form.Control>
                              {errors.visaType && <p className="text-danger">{errors.visaType}</p>}
                            </Form.Group>

              <Form.Group className="mb-3">
                                <Form.Check
                                  type="checkbox"
                                  label="Do you have a previously approved petition?"
                                  checked={formData.hasApprovedPetition}
                                  onChange={(e) => {
                                    setFormData({ ...formData, hasApprovedPetition: e.target.checked });
                                  }}
                                />
                              </Form.Group>

                              {formData.hasApprovedPetition && (
                                <Form.Group className="mb-3">
                                  <Form.Label>Petition Number</Form.Label>
                                  <Form.Control
                                    type="text"
                                    name="approvedPetitionNumber"
                                    value={formData.approvedPetitionNumber}
                                    onChange={handleChange}
                                    placeholder="Enter Petition Number"
                                  />
                                </Form.Group>
                              )}


                             <Form.Group className="mb-3">
                               <Form.Check
                                 type="checkbox"
                                 label="Do you hold a Master’s Degree from a USA University?"
                                 checked={formData.hasMastersDegree}
                                 onChange={(e) => {
                                   setFormData({ ...formData, hasMastersDegree: e.target.checked });
                                   setShowMastersModal(e.target.checked);
                                 }}
                               />
                             </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Upload Passport PDF</Form.Label>
                <Form.Control type="file" name="passportFile" accept="application/pdf" onChange={handleChange} required />
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Upload MS Degree PDF (Optional)</Form.Label>
                <Form.Control type="file" name="msDegreeFile" accept="application/pdf" onChange={handleChange} />
              </Form.Group>

              <Button variant="primary" className="w-100" type="submit">
                Submit Application
              </Button>
            </Form>
          </Card.Body>
        </Card>
      </Container>
    </>
  );
};

export default VisaAppForm;

