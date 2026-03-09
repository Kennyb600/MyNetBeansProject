package com.wms.wms_backend.service;

import com.wms.wms_backend.domain.Citizen;
import com.wms.wms_backend.domain.CourtCase;
import com.wms.wms_backend.domain.Warrant;
import com.wms.wms_backend.dto.WarrantDTO;
import com.wms.wms_backend.repository.CitizenRepository;
import com.wms.wms_backend.repository.CourtCaseRepository;
import com.wms.wms_backend.repository.WarrantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarrantService {

    private final WarrantRepository warrantRepository;
    private final CourtCaseRepository courtCaseRepository;
    private final CitizenRepository citizenRepository;

    @Autowired
    public WarrantService(WarrantRepository warrantRepository, CourtCaseRepository courtCaseRepository, CitizenRepository citizenRepository) {
        this.warrantRepository = warrantRepository;
        this.courtCaseRepository = courtCaseRepository;
        this.citizenRepository = citizenRepository;
    }

    // --- Helper Method ---
    private WarrantDTO convertToDTO(Warrant warrant) {
        return new WarrantDTO(
                warrant.getWarrantId(),
                warrant.getAmountOwed(),
                warrant.getStatus()
        );
    }

    // 1. Retrieve all warrants
    public List<WarrantDTO> getAllWarrants() {
        return warrantRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 2. Retrieve a specific warrant
    public Optional<WarrantDTO> getWarrantById(Long id) {
        return warrantRepository.findById(id).map(this::convertToDTO);
    }

    // 3. Issue a new Warrant (Business Logic: Must belong to a valid case)
    public WarrantDTO issueWarrant(Long caseId, BigDecimal amountOwed) {
        CourtCase courtCase = courtCaseRepository.findById(caseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Court Case ID not found."));

        Warrant newWarrant = new Warrant(courtCase, amountOwed, "Active");
        Warrant savedWarrant = warrantRepository.save(newWarrant);

        return convertToDTO(savedWarrant);
    }

    // 4. Update Warrant Status
    public WarrantDTO updateWarrantStatus(Long warrantId, String newStatus) {
        Warrant warrant = warrantRepository.findById(warrantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Warrant ID not found."));

        warrant.setStatus(newStatus);
        
        if ("Executed".equalsIgnoreCase(newStatus)) {
            warrant.setAmountOwed(BigDecimal.ZERO);
        }

        Warrant updatedWarrant = warrantRepository.save(warrant);
        return convertToDTO(updatedWarrant);
    }

    // ------------------------------------------------------------------------
    // MOCK DATA GENERATOR (Keeps your application startup from crashing!)
    // ------------------------------------------------------------------------
    @Transactional
    public void createMockCourtData() {
        // Only generate mock data if the database is currently empty
        if (citizenRepository.count() == 0) {
            Citizen applicant = new Citizen("John", "Doe", "Applicant");
            Citizen respondent = new Citizen("Jane", "Smith", "Respondent");
            
            citizenRepository.save(applicant);
            citizenRepository.save(respondent);

            CourtCase courtCase = new CourtCase(applicant, respondent);
            courtCaseRepository.save(courtCase);

            Warrant warrant = new Warrant(courtCase, new BigDecimal("50000.00"), "Active");
            warrantRepository.save(warrant);
            
            System.out.println("Mock data successfully generated!");
        }
    }
}