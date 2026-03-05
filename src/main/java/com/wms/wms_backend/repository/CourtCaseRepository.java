/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.wms.wms_backend.repository;

import com.wms.wms_backend.domain.CourtCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtCaseRepository extends JpaRepository<CourtCase, Long> {
    
    // Custom query: Find all cases where a specific citizen ID is the applicant
    List<CourtCase> findByApplicantCitizenId(Long applicantId);
    
    // Custom query: Find all cases where a specific citizen ID is the respondent
    List<CourtCase> findByRespondentCitizenId(Long respondentId);
}