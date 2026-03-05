/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.wms.wms_backend.service;

import com.wms.wms_backend.domain.Citizen;
import com.wms.wms_backend.domain.CourtCase;
import com.wms.wms_backend.domain.Warrant;
import com.wms.wms_backend.repository.CitizenRepository;
import com.wms.wms_backend.repository.CourtCaseRepository;
import com.wms.wms_backend.repository.WarrantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WarrantService {

    private final CitizenRepository citizenRepository;
    private final CourtCaseRepository courtCaseRepository;
    private final WarrantRepository warrantRepository;

    // Constructor Injection is the best practice for Spring Boot services
    @Autowired
    public WarrantService(CitizenRepository citizenRepository, 
                          CourtCaseRepository courtCaseRepository, 
                          WarrantRepository warrantRepository) {
        this.citizenRepository = citizenRepository;
        this.courtCaseRepository = courtCaseRepository;
        this.warrantRepository = warrantRepository;
    }

    // @Transactional ensures that if one save fails, the whole process rolls back
    @Transactional
    public void createMockCourtData() {
        System.out.println("--- Starting Data Generation ---");

        // 1. Create the Applicant and Respondent
        Citizen applicant = new Citizen("John", "Doe", "Applicant");
        Citizen respondent = new Citizen("Jane", "Smith", "Respondent");
        
        // Save them to the database to generate their IDs
        citizenRepository.save(applicant);
        citizenRepository.save(respondent);
        System.out.println("Citizens saved to database.");

        // 2. Create a Court Case linking them together
        CourtCase newCase = new CourtCase(applicant, respondent);
        courtCaseRepository.save(newCase);
        System.out.println("Court Case saved to database.");

        // 3. Issue a Warrant against the Respondent for this case
        Warrant benchWarrant = new Warrant(newCase, new BigDecimal("50000.00"), "Active");
        warrantRepository.save(benchWarrant);
        System.out.println("Warrant saved to database.");

        System.out.println("--- Data Generation Complete! ---");
    }
}
