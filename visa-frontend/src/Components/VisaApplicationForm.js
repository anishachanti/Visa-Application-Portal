import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Form, Button, Container, Card, Modal } from "react-bootstrap";
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

const VisaApplicationForm = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    empId: "",
    fullName: "",
    surname: "",
    dob: "",
    passportNumber: "",
    nationality: "",
    visaType: "",
   passportIssueDate: "",
   passportExpiryDate: "",
   countryOfBirth: "",
   cityOfBirth: "",
    citizenship: "",
    hasApprovedPetition: false,  // New checkbox field
    approvedPetitionNumber: "",  // New input field
    hasMastersDegree: false,  // New checkbox field
    universityName: "",  // New input field
    mastersGPA: "",  // New input field
    mastersCompletionDate: "",// New input field
    passportFile: null,
    msDegreeFile: null
  });

  const [showMastersModal, setShowMastersModal] = useState(false);

  const [errors, setErrors] = useState({ empId: "", fullName: "", passportNumber: "" });
  const [success, setSuccess] = useState("");


  const handleChange = (e) => {
    const { name, value, type, checked, files } = e.target;
    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : (type === "file" ? files[0] : value) // Handle file input
    });
  };



  const handleBlur = (e) => {
    const { name, value } = e.target;
    let newErrors = { ...errors };

    if (name === "empId") {
      if (!/^\d+$/.test(value)) {
        newErrors.empId = "⚠ Employee ID must contain only numbers.";
      } else {
        newErrors.empId = "";
      }
    }

    if (name === "fullName") {
      if (!/^[A-Za-z\s]+$/.test(value)) {
        newErrors.fullName = "⚠ Please enter a valid employee name.";
      } else {
        newErrors.fullName = "";
      }
    }

    if (name === "surname") {
        if (!/^[A-Za-z\s]+$/.test(value)) {
          newErrors.surname = "⚠ Please enter a valid surname (alphabets only).";
        } else {
          newErrors.surname = "";
        }
      }

    if (name === "passportNumber") {
      if (!/^[A-Za-z]\d{7}$/.test(value)) {
        newErrors.passportNumber = "⚠ Passport Number must start with a letter followed by 7 digits.";
      } else {
        newErrors.passportNumber = "";
      }
    }

    if (name === "passportExpiryDate" || name === "passportIssueDate") {
          const issueDate = new Date(formData.passportIssueDate);
          const expiryDate = new Date(formData.passportExpiryDate);

          if (issueDate >= expiryDate) {
            newErrors.passportExpiryDate = "⚠ Passport expiry date must be after the issue date.";
          } else {
            newErrors.passportExpiryDate = "";
          }
        }

      if (name === "citizenship" && !value) {
         newErrors.citizenship = "⚠ Citizenship is required.";
       } else {
         newErrors.citizenship = "";
       }

      if (name === "passportFile" && formData.passportFile) {
        if (formData.passportFile.type !== "application/pdf") {
          newErrors.passportFile = "⚠ Passport file must be a PDF.";
        } else {
          newErrors.passportFile = "";
        }
      }

      if (name === "msDegreeFile" && formData.msDegreeFile) {
        if (formData.msDegreeFile.type !== "application/pdf") {
          newErrors.msDegreeFile = "⚠ MS Degree file must be a PDF.";
        } else {
          newErrors.msDegreeFile = "";
        }
      }

    setErrors(newErrors);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(formData);

    if (!formData.citizenship) {
        setErrors({ ...errors, citizenship: "⚠ Citizenship is required." });
        return; // Prevent form submission
      }

    const token = localStorage.getItem("token");

    const issueDate = new Date(formData.passportIssueDate);
        const expiryDate = new Date(formData.passportExpiryDate);

        if (issueDate >= expiryDate) {
          setErrors({ passportExpiryDate: "⚠ Passport expiry date must be after the issue date." });
          return;
        }

        if (isNaN(issueDate)) {
            setErrors({ ...errors, passportIssueDate: "⚠ Invalid Passport Issue Date." });
            return;
          }

          // Validate Country and City of Birth
          if (!formData.countryOfBirth.trim()) {
            setErrors({ ...errors, countryOfBirth: "⚠ Country of Birth is required." });
            return;
          }

          if (!formData.cityOfBirth.trim()) {
            setErrors({ ...errors, cityOfBirth: "⚠ City of Birth is required." });
            return;
          }


    const birthDate = new Date(formData.dob);
    const today = new Date();
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();

      // Adjust age if the birthday hasn't occurred yet this year
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
    // eslint-disable-next-line
      age--;
    }

      // Validate age restriction for H1B visa
    if (formData.visaType === "H1B" && age < 18) {
      setErrors({ ...errors, visaType: "❌ Applicants must be 18 or older to apply for an H1B visa." });
      return; // Stop submission
    }

     // ✅ Preparing form data for file upload
      const formDataToSend = new FormData();
      formDataToSend.append(
              "visaApplication",
              new Blob(
                [
                  JSON.stringify({
                    empId: formData.empId,
                    fullName: formData.fullName,
                    surname: formData.surname,
                    dob: formData.dob,
                    passportNumber: formData.passportNumber,
                    nationality: formData.nationality,
                    visaType: formData.visaType,
                    passportIssueDate: formData.passportIssueDate,
                    passportExpiryDate: formData.passportExpiryDate,
                    countryOfBirth: formData.countryOfBirth,
                    cityOfBirth: formData.cityOfBirth,
                    citizenship: formData.citizenship,
                    hasApprovedPetition: formData.hasApprovedPetition,
                    approvedPetitionNumber: formData.approvedPetitionNumber,
                    hasMastersDegree: formData.hasMastersDegree,
                    universityName: formData.universityName,
                    mastersGPA: formData.mastersGPA,
                    mastersCompletionDate: formData.mastersCompletionDate,
                    passportFilePath: `${formData.empId}_${formData.fullName.replace(/\s+/g, "")}.pdf`,
                    degreeFilePath: formData.msDegreeFile
                      ? `${formData.empId}_${formData.fullName.replace(/\s+/g, "")}_MasterDegree.pdf`
                      : null,
                  }),
                ],
                { type: "application/json" }
              )
            );
      formDataToSend.append("passportFile", formData.passportFile);
      if (formData.msDegreeFile) {
        formDataToSend.append("msDegreeFile", formData.msDegreeFile);
      }

    try {
      const response = await axios.post(
        "http://localhost:9090/api/visa/apply",
        formDataToSend,
        {
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "multipart/form-data",
          },
        }
      );

      if (response.status === 200) {
        setSuccess("✅ Visa application submitted successfully!");
        setErrors({});
        setFormData({
          empId: "",
          fullName: "",
          surname: "",
          dob: "",
          passportNumber: "",
          nationality: "",
          visaType: "",
          passportIssueDate: "",
          passportExpiryDate: "",
          countryOfBirth: "",
          cityOfBirth: "",
          citizenship: "",
          hasApprovedPetition: false,
          approvedPetitionNumber: "",
          hasMastersDegree: false,
          universityName: "",
          mastersGPA: "",
          mastersCompletionDate: "",
          passportFile: null, // Clear passport file after submission
          msDegreeFile: null, // Clear MS degree file after submission
        });

        setShowMastersModal(false);
        setTimeout(() => navigate("/dashboard"), 2000);
      }
    } catch (err) {
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
            {errors.formSubmit && <p className="text-danger text-center">{errors.formSubmit}</p>}
            {success && <p className="text-success text-center">{success}</p>}
            <Form onSubmit={handleSubmit}>

              <Form.Group className="mb-3">
                <Form.Label>Employee ID</Form.Label>
                <Form.Control
                  type="text"
                  name="empId"
                  value={formData.empId}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  required
                />
                {errors.empId && <p className="text-danger">{errors.empId}</p>}
              </Form.Group>


              <Form.Group className="mb-3">
                <Form.Label>Name (as per Passport)</Form.Label>
                <Form.Control
                  type="text"
                  name="fullName"
                  value={formData.fullName}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  required
                />
                {errors.fullName && <p className="text-danger">{errors.fullName}</p>}
              </Form.Group>


              <Form.Group className="mb-3">
                <Form.Label>Passport Number</Form.Label>
                <Form.Control
                  type="text"
                  name="passportNumber"
                  value={formData.passportNumber}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  required
                />
                {errors.passportNumber && <p className="text-danger">{errors.passportNumber}</p>}
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Surname</Form.Label>
                <Form.Control
                  type="text"
                  name="surname"
                  value={formData.surname}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  required
                />
                {errors.surname && <p className="text-danger">{errors.surname}</p>}
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Date Of Birth</Form.Label>
                <Form.Control
                  type="date"
                  name="dob"
                  value={formData.dob}
                  onChange={handleChange}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Nationality</Form.Label>
                <Form.Control
                  type="text"
                  name="nationality"
                  value={formData.nationality}
                  onChange={handleChange}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Passport Issue Date</Form.Label>
                <Form.Control
                  type="date"
                  name="passportIssueDate"
                  value={formData.passportIssueDate}
                  onChange={handleChange}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Passport Expiry Date</Form.Label>
                <Form.Control
                  type="date"
                  name="passportExpiryDate"
                  value={formData.passportExpiryDate}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  required
                />
                {errors.passportExpiryDate && <p className="text-danger">{errors.passportExpiryDate}</p>}
              </Form.Group>



                            <Form.Group className="mb-3">
                              <Form.Label>Country of Birth</Form.Label>
                              <Form.Control
                                as="select"
                                name="countryOfBirth"
                                value={formData.countryOfBirth}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                required
                              >
                              {errors.countryOfBirth && <p className="text-danger">{errors.countryOfBirth}</p>}
                                <option value="">--Please choose a country--</option>
                                {countries.map((country, index) => (
                                  <option key={index} value={country}>
                                    {country}
                                  </option>
                                ))}
                              </Form.Control>
                            </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>City/Town of Birth</Form.Label>
                <Form.Control
                  type="text"
                  name="cityOfBirth"
                  value={formData.cityOfBirth}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  required
                />
                {errors.cityOfBirth && <p className="text-danger">{errors.cityOfBirth}</p>}
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
                              <Form.Label>Citizenship</Form.Label>
                              <Form.Control
                                type="text"
                                name="citizenship"
                                value={formData.citizenship}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                required
                              />
                              {errors.citizenship && <p className="text-danger">{errors.citizenship}</p>}
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
                 <Form.Control
                   type="file"
                   name="passportFile"
                   accept="application/pdf"
                   onChange={handleChange}
                   required
                 />
                 {errors.passportFile && <p className="text-danger">{errors.passportFile}</p>}
               </Form.Group>

               <Form.Group className="mb-3">
                 <Form.Label>Upload MS Degree PDF (Optional)</Form.Label>
                 <Form.Control
                   type="file"
                   name="msDegreeFile"
                   accept="application/pdf"
                   onChange={handleChange}
                 />
                 {errors.msDegreeFile && <p className="text-danger">{errors.msDegreeFile}</p>}
               </Form.Group>


              <Button variant="primary" className="w-100" type="submit">
                Submit Application
              </Button>

              <Modal
                show={showMastersModal}
                onHide={() => {
                  setShowMastersModal(false);
                }}
              >
                <Modal.Header closeButton>
                  <Modal.Title>Master’s Degree Details</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                  <Form.Group className="mb-3">
                    <Form.Label>University Name</Form.Label>
                    <Form.Control
                      type="text"
                      name="universityName"
                      value={formData.universityName}
                      onChange={(e) => setFormData({ ...formData, universityName: e.target.value })}
                      placeholder="Enter University Name"
                    />
                  </Form.Group>

                  <Form.Group className="mb-3">
                    <Form.Label>GPA</Form.Label>
                    <Form.Control
                      type="number"
                      step="0.01"
                      name="mastersGPA"
                      value={formData.mastersGPA}
                      onChange={(e) => setFormData({ ...formData, mastersGPA: parseFloat(e.target.value) || "" })}
                      placeholder="Enter GPA"
                    />
                  </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Completion Date</Form.Label>
                  <Form.Control
                    type="date"
                    name="mastersCompletionDate"
                    value={formData.mastersCompletionDate}
                    onChange={(e) => setFormData({ ...formData, mastersCompletionDate: e.target.value })}
                  />
                </Form.Group>

                </Modal.Body>
                <Modal.Footer>
                  <Button
                    variant="secondary"
                    onClick={() => {
                      setShowMastersModal(false);
                    }}
                  >
                    Close
                  </Button>
                </Modal.Footer>
               </Modal>

            </Form>
          </Card.Body>
        </Card>
      </Container>
    </>
  );
};

export default VisaApplicationForm;



