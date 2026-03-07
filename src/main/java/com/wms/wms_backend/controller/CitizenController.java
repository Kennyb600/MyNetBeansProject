package com.wms.wms_backend.controller;

import com.wms.wms_backend.domain.Citizen;
import com.wms.wms_backend.dto.CitizenDTO;
import com.wms.wms_backend.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

    private final CitizenRepository citizenRepository;

    @Autowired
    public CitizenController(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    // --- Helper Method ---
    // This securely packs the database entity into our safe transfer box
    private CitizenDTO convertToDTO(Citizen citizen) {
        return new CitizenDTO(
                citizen.getCitizenId(),
                citizen.getFirstName(),
                citizen.getLastName(),
                citizen.getRole()
        );
    }

    // 1. Retrieve all citizens
    @GetMapping
    public ResponseEntity<List<CitizenDTO>> getAllCitizens() {
        List<Citizen> citizens = citizenRepository.findAll();
        
        List<CitizenDTO> citizenDTOs = citizens.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
                
        return new ResponseEntity<>(citizenDTOs, HttpStatus.OK);
    }

    // 2. Retrieve a specific citizen by their ID
    @GetMapping("/{id}")
    public ResponseEntity<CitizenDTO> getCitizenById(@PathVariable Long id) {
        Optional<Citizen> citizen = citizenRepository.findById(id);
        
        // If found, pack into DTO and return 200 OK. Otherwise, return 404 NOT FOUND.
        return citizen.map(value -> new ResponseEntity<>(convertToDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 3. Query citizens by their role (e.g., "Applicant" or "Respondent")
    @GetMapping("/role/{role}")
    public ResponseEntity<List<CitizenDTO>> getCitizensByRole(@PathVariable String role) {
        List<Citizen> citizens = citizenRepository.findByRole(role);
        
        if (citizens.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        List<CitizenDTO> citizenDTOs = citizens.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
                
        return new ResponseEntity<>(citizenDTOs, HttpStatus.OK);
    }

    // 4. Create a new citizen manually
    @PostMapping
    public ResponseEntity<CitizenDTO> createCitizen(@RequestBody CitizenDTO citizenDTO) {
        // Step A: Unpack the incoming DTO into a strict database entity
        Citizen citizen = new Citizen(
                citizenDTO.getFirstName(),
                citizenDTO.getLastName(),
                citizenDTO.getRole()
        );
        
        // Step B: Save it to the MySQL database
        Citizen savedCitizen = citizenRepository.save(citizen);
        
        // Step C: Repack the saved entity (which now has an auto-generated ID) back into a DTO to return
        return new ResponseEntity<>(convertToDTO(savedCitizen), HttpStatus.CREATED);
    }
}