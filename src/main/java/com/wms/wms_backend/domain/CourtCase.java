/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.wms.wms_backend.domain;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "court_cases")
public class CourtCase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long caseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", referencedColumnName = "citizenId", nullable = false)
    private Citizen applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "respondent_id", referencedColumnName = "citizenId", nullable = false)
    private Citizen respondent;

    // Bulletproof Cardinality: One Court Case can have multiple warrants. 
    // mappedBy = "courtCase" tells Hibernate that the Warrant entity owns the foreign key.
    @JsonIgnore
    @OneToMany(mappedBy = "courtCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Warrant> warrants = new ArrayList<>();

    public CourtCase() {}

    public CourtCase(Citizen applicant, Citizen respondent) {
        this.applicant = applicant;
        this.respondent = respondent;
    }

    // Standard Getters and Setters
    public Long getCaseId() { return caseId; }
    public void setCaseId(Long caseId) { this.caseId = caseId; }
    public Citizen getApplicant() { return applicant; }
    public void setApplicant(Citizen applicant) { this.applicant = applicant; }
    public Citizen getRespondent() { return respondent; }
    public void setRespondent(Citizen respondent) { this.respondent = respondent; }
    public List<Warrant> getWarrants() { return warrants; }
    public void setWarrants(List<Warrant> warrants) { this.warrants = warrants; }
    
    // Helper method to keep both sides of the relationship in sync
    public void addWarrant(Warrant warrant) {
        warrants.add(warrant);
        warrant.setCourtCase(this);
    }
}