package com.visa.demo.repository;

import com.visa.demo.model.VisaApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface VisaApplicationRepository extends JpaRepository<VisaApplication, String> {

    // ✅ Fetch all PENDING applications assigned to a specific reporting manager
    @Query("SELECT v FROM VisaApplication v JOIN Employee e ON v.empId = e.empId WHERE e.reportingManagerId = :managerId AND v.status = 'PENDING'")
    List<VisaApplication> findByManagerId(String managerId);

    // ✅ Search applications by Employee ID (Partial Match Allowed)
    @Query("SELECT v FROM VisaApplication v WHERE v.empId LIKE %:empId%")
     List<VisaApplication> searchByEmployeeId(@Param("empId")String empId);

   /* @Query("SELECT v FROM VisaApplication v WHERE v.status = 'APPROVED'")
    List<VisaApplication> findApprovedApplications();*/

    /*@Query("SELECT v FROM VisaApplication v WHERE v.status = 'APPROVED' ORDER BY v.dateApproved DESC")
    List<VisaApplication> findApprovedApplications();*/

    /*@Query("SELECT v FROM VisaApplication v WHERE v.status != 'REJECTED'")
    List<VisaApplication> findInProgressApplications();

     */

    @Query("SELECT v FROM VisaApplication v WHERE v.status IN ('Approved', 'Petition Submitted', 'Petition Approved','Petition Rejected')")
    List<VisaApplication> findInProgressApplications();




}


