/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.wms.wms_backend.domain;


import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "warrants")
public class Warrant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warrantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", referencedColumnName = "caseId", nullable = false)
    private CourtCase courtCase;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amountOwed;

    @Column(nullable = false)
    private String status; 

    public Warrant() {}

    public Warrant(CourtCase courtCase, BigDecimal amountOwed, String status) {
        this.courtCase = courtCase;
        this.amountOwed = amountOwed;
        this.status = status;
    }

    // Standard Getters and Setters
    public Long getWarrantId() { return warrantId; }
    public void setWarrantId(Long warrantId) { this.warrantId = warrantId; }
    public CourtCase getCourtCase() { return courtCase; }
    public void setCourtCase(CourtCase courtCase) { this.courtCase = courtCase; }
    public BigDecimal getAmountOwed() { return amountOwed; }
    public void setAmountOwed(BigDecimal amountOwed) { this.amountOwed = amountOwed; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
