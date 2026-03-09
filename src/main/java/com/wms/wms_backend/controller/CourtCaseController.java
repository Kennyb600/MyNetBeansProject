package com.wms.wms_backend.controller;

import com.wms.wms_backend.dto.CourtCaseDTO;
import com.wms.wms_backend.service.CourtCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cases")
public class CourtCaseController {

    private final CourtCaseService courtCaseService;

    @Autowired
    public CourtCaseController(CourtCaseService courtCaseService) {
        this.courtCaseService = courtCaseService;
    }

    @GetMapping
    public ResponseEntity<List<CourtCaseDTO>> getAllCases() {
        List<CourtCaseDTO> cases = courtCaseService.getAllCases();
        return new ResponseEntity<>(cases, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtCaseDTO> getCaseById(@PathVariable Long id) {
        Optional<CourtCaseDTO> courtCase = courtCaseService.getCaseById(id);
        
        return courtCase.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // New Endpoint to create a case!
    @PostMapping
    public ResponseEntity<CourtCaseDTO> createCase(@RequestParam Long applicantId, @RequestParam Long respondentId) {
        CourtCaseDTO savedCase = courtCaseService.createCase(applicantId, respondentId);
        return new ResponseEntity<>(savedCase, HttpStatus.CREATED);
    }
}