package com.wms.wms_backend.controller;

import com.wms.wms_backend.dto.WarrantDTO;
import com.wms.wms_backend.service.WarrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/warrants")
public class WarrantController {

    private final WarrantService warrantService;

    @Autowired
    public WarrantController(WarrantService warrantService) {
        this.warrantService = warrantService;
    }

    @GetMapping
    public ResponseEntity<List<WarrantDTO>> getAllWarrants() {
        List<WarrantDTO> warrants = warrantService.getAllWarrants();
        return new ResponseEntity<>(warrants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarrantDTO> getWarrantById(@PathVariable Long id) {
        Optional<WarrantDTO> warrant = warrantService.getWarrantById(id);
        return warrant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Issue a new warrant attached to a specific case
    @PostMapping("/issue")
    public ResponseEntity<WarrantDTO> issueWarrant(@RequestParam Long caseId, @RequestParam BigDecimal amountOwed) {
        WarrantDTO newWarrant = warrantService.issueWarrant(caseId, amountOwed);
        return new ResponseEntity<>(newWarrant, HttpStatus.CREATED);
    }

    // Update the status of a warrant (e.g., from Active to Executed)
    @PutMapping("/{id}/status")
    public ResponseEntity<WarrantDTO> updateStatus(@PathVariable Long id, @RequestParam String status) {
        WarrantDTO updatedWarrant = warrantService.updateWarrantStatus(id, status);
        return new ResponseEntity<>(updatedWarrant, HttpStatus.OK);
    }
}