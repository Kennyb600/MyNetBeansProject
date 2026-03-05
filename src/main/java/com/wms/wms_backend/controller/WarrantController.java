/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.wms.wms_backend.controller;

import com.wms.wms_backend.domain.Warrant;
import com.wms.wms_backend.repository.WarrantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/warrants")
public class WarrantController {

    private final WarrantRepository warrantRepository;

    // Constructor Injection: Spring Boot automatically provides the repository
    @Autowired
    public WarrantController(WarrantRepository warrantRepository) {
        this.warrantRepository = warrantRepository;
    }

    // ----------------------------------------------------
    // GET: http://localhost:9090/api/warrants
    // Retrieves all existing warrants from the database
    // ----------------------------------------------------
    @GetMapping
    public List<Warrant> getAllWarrants() {
        System.out.println("Web request received: Fetching all warrants!");
        return warrantRepository.findAll();
    }

    // ----------------------------------------------------
    // POST: http://localhost:9090/api/warrants
    // Creates a new warrant attached to an existing court case
    // ----------------------------------------------------
    @PostMapping
    public Warrant createWarrant(@RequestBody Warrant newWarrant) {
        System.out.println("Web request received: Issuing a new warrant!");
        return warrantRepository.save(newWarrant);
    }
}