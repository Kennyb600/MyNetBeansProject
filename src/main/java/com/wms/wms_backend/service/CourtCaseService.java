package com.wms.wms_backend.service;

import com.wms.wms_backend.domain.Citizen;
import com.wms.wms_backend.domain.CourtCase;
import com.wms.wms_backend.dto.CitizenDTO;
import com.wms.wms_backend.dto.CourtCaseDTO;
import com.wms.wms_backend.dto.WarrantDTO;
import com.wms.wms_backend.repository.CitizenRepository;
import com.wms.wms_backend.repository.CourtCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourtCaseService {

    private final CourtCaseRepository courtCaseRepository;
    private final CitizenRepository citizenRepository; // We need this to verify citizens exist

    @Autowired
    public CourtCaseService(CourtCaseRepository courtCaseRepository, CitizenRepository citizenRepository) {
        this.courtCaseRepository = courtCaseRepository;
        this.citizenRepository = citizenRepository;
    }

    // --- Helper Method for DTO Conversion ---
    private CourtCaseDTO convertToDTO(CourtCase courtCase) {
        CitizenDTO applicantDTO = new CitizenDTO(
                courtCase.getApplicant().getCitizenId(),
                courtCase.getApplicant().getFirstName(),
                courtCase.getApplicant().getLastName(),
                courtCase.getApplicant().getRole()
        );

        CitizenDTO respondentDTO = new CitizenDTO(
                courtCase.getRespondent().getCitizenId(),
                courtCase.getRespondent().getFirstName(),
                courtCase.getRespondent().getLastName(),
                courtCase.getRespondent().getRole()
        );

        List<WarrantDTO> warrantDTOs = courtCase.getWarrants().stream()
                .map(warrant -> new WarrantDTO(
                        warrant.getWarrantId(),
                        warrant.getAmountOwed(),
                        warrant.getStatus()
                )).collect(Collectors.toList());

        return new CourtCaseDTO(courtCase.getCaseId(), applicantDTO, respondentDTO, warrantDTOs);
    }

    // 1. Retrieve all Court Cases
    public List<CourtCaseDTO> getAllCases() {
        return courtCaseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 2. Retrieve a specific Court Case by ID
    public Optional<CourtCaseDTO> getCaseById(Long id) {
        return courtCaseRepository.findById(id).map(this::convertToDTO);
    }

    // 3. Create a new Court Case (Business Logic Enforced!)
    public CourtCaseDTO createCase(Long applicantId, Long respondentId) {
        // Rule: Verify Applicant exists
        Citizen applicant = citizenRepository.findById(applicantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicant ID not found in registry"));

        // Rule: Verify Respondent exists
        Citizen respondent = citizenRepository.findById(respondentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Respondent ID not found in registry"));

        // If both exist, create and save the case
        CourtCase newCase = new CourtCase(applicant, respondent);
        CourtCase savedCase = courtCaseRepository.save(newCase);
        
        return convertToDTO(savedCase);
    }
}