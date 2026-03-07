/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.wms.wms_backend.domain;



import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "citizens")
public class Citizen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long citizenId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String role;

    // Bulletproof Cardinality: One Citizen can be the applicant in multiple cases
    
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<CourtCase> casesAsApplicant = new ArrayList<>();

    // Bulletproof Cardinality: One Citizen can be the respondent in multiple cases
   
    @OneToMany(mappedBy = "respondent", cascade = CascadeType.ALL)
    private List<CourtCase> casesAsRespondent = new ArrayList<>();

    public Citizen() {}

    public Citizen(String firstName, String lastName, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    // Standard Getters and Setters
    public Long getCitizenId() { return citizenId; }
    public void setCitizenId(Long citizenId) { this.citizenId = citizenId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public List<CourtCase> getCasesAsApplicant() { return casesAsApplicant; }
    public void setCasesAsApplicant(List<CourtCase> casesAsApplicant) { this.casesAsApplicant = casesAsApplicant; }
    public List<CourtCase> getCasesAsRespondent() { return casesAsRespondent; }
    public void setCasesAsRespondent(List<CourtCase> casesAsRespondent) { this.casesAsRespondent = casesAsRespondent; }
}