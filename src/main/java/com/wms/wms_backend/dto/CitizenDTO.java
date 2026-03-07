package com.wms.wms_backend.dto;

public class CitizenDTO {

    private Long citizenId;
    private String firstName;
    private String lastName;
    private String role;

    public CitizenDTO() {
    }

    public CitizenDTO(Long citizenId, String firstName, String lastName, String role) {
        this.citizenId = citizenId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    // Getters
    public Long getCitizenId() { return citizenId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getRole() { return role; }

    // Setters
    public void setCitizenId(Long citizenId) { this.citizenId = citizenId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setRole(String role) { this.role = role; }
}