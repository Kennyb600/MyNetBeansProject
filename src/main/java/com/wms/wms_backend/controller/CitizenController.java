package com.wms.wms_backend.controller;

import com.wms.wms_backend.dto.CitizenDTO;
import com.wms.wms_backend.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

    private final CitizenService citizenService;

    // Injecting the Service instead of the Repository
    @Autowired
    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @GetMapping
    public ResponseEntity<List<CitizenDTO>> getAllCitizens() {
        List<CitizenDTO> citizens = citizenService.getAllCitizens();
        return new ResponseEntity<>(citizens, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitizenDTO> getCitizenById(@PathVariable Long id) {
        Optional<CitizenDTO> citizen = citizenService.getCitizenById(id);
        
        return citizen.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<CitizenDTO>> getCitizensByRole(@PathVariable String role) {
        List<CitizenDTO> citizens = citizenService.getCitizensByRole(role);
        
        if (citizens.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(citizens, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CitizenDTO> createCitizen(@RequestBody CitizenDTO citizenDTO) {
        CitizenDTO savedCitizen = citizenService.createCitizen(citizenDTO);
        return new ResponseEntity<>(savedCitizen, HttpStatus.CREATED);
    }
}