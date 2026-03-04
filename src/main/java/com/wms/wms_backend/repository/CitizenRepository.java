/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.wms.wms_backend.repository;

import com.wms.wms_backend.domain.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    
    // Custom query: Find citizens based on their role (e.g., "Applicant" or "Respondent")
    List<Citizen> findByRole(String role);
    
    // Custom query: Find a citizen by exact first and last name
    List<Citizen> findByFirstNameAndLastName(String firstName, String lastName);
}
