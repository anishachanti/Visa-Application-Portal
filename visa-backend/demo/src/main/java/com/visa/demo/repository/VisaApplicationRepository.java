/*package com.visa.demo.repository;

import com.visa.demo.model.VisaApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface VisaApplicationRepository extends JpaRepository<VisaApplication, String> {

    // ✅ Fetch all PENDING applications assigned to a specific reporting manager
    @Query("SELECT v FROM VisaApplication v JOIN Employee e ON v.empId = e.empId WHERE e.reportingManagerId = :managerId AND v.status = 'PENDING'")
    List<VisaApplication> findByManagerId(String managerId);
}

 */

