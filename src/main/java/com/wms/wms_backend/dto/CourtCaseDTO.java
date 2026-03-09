package com.wms.wms_backend.dto;

import java.util.List;

public class CourtCaseDTO {

    private Long caseId;
    private CitizenDTO applicant;
    private CitizenDTO respondent;
    private List<WarrantDTO> warrants;

    public CourtCaseDTO() {}

    public CourtCaseDTO(Long caseId, CitizenDTO applicant, CitizenDTO respondent, List<WarrantDTO> warrants) {
        this.caseId = caseId;
        this.applicant = applicant;
        this.respondent = respondent;
        this.warrants = warrants;
    }

    public Long getCaseId() { return caseId; }
    public void setCaseId(Long caseId) { this.caseId = caseId; }
    public CitizenDTO getApplicant() { return applicant; }
    public void setApplicant(CitizenDTO applicant) { this.applicant = applicant; }
    public CitizenDTO getRespondent() { return respondent; }
    public void setRespondent(CitizenDTO respondent) { this.respondent = respondent; }
    public List<WarrantDTO> getWarrants() { return warrants; }
    public void setWarrants(List<WarrantDTO> warrants) { this.warrants = warrants; }
}