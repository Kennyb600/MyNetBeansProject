package com.wms.wms_backend.service;

import com.wms.wms_backend.domain.Citizen;
import com.wms.wms_backend.dto.CitizenDTO;
import com.wms.wms_backend.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// @Service tells Spring Boot this class holds business logic
@Service
public class CitizenService {

    private final CitizenRepository citizenRepository;

    @Autowired
    public CitizenService(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    // --- Helper Method ---
    private CitizenDTO convertToDTO(Citizen citizen) {
        return new CitizenDTO(
                citizen.getCitizenId(),
                citizen.getFirstName(),
                citizen.getLastName(),
                citizen.getRole()
        );
    }

    // 1. Get all citizens
    public List<CitizenDTO> getAllCitizens() {
        return citizenRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 2. Get citizen by ID
    public Optional<CitizenDTO> getCitizenById(Long id) {
        return citizenRepository.findById(id).map(this::convertToDTO);
    }

    // 3. Get citizens by role
    public List<CitizenDTO> getCitizensByRole(String role) {
        return citizenRepository.findByRole(role).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 4. Create a new citizen
    public CitizenDTO createCitizen(CitizenDTO citizenDTO) {
        Citizen citizen = new Citizen(
                citizenDTO.getFirstName(),
                citizenDTO.getLastName(),
                citizenDTO.getRole()
        );
        Citizen savedCitizen = citizenRepository.save(citizen);
        return convertToDTO(savedCitizen);
    }
}