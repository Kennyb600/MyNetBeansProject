/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.wms.wms_backend.repository;

import com.wms.wms_backend.domain.Warrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarrantRepository extends JpaRepository<Warrant, Long> {
    
    // Custom query: Find all warrants that have a specific status (e.g., "Active" vs "Executed")
    List<Warrant> findByStatus(String status);
    
    // Custom query: Find all warrants attached to a specific court case ID
    List<Warrant> findByCourtCaseCaseId(Long caseId);
}