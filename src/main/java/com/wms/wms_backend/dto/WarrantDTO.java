package com.wms.wms_backend.dto;

import java.math.BigDecimal;

public class WarrantDTO {

    private Long warrantId;
    private BigDecimal amountOwed;
    private String status;

    public WarrantDTO() {}

    public WarrantDTO(Long warrantId, BigDecimal amountOwed, String status) {
        this.warrantId = warrantId;
        this.amountOwed = amountOwed;
        this.status = status;
    }

    public Long getWarrantId() { return warrantId; }
    public void setWarrantId(Long warrantId) { this.warrantId = warrantId; }
    public BigDecimal getAmountOwed() { return amountOwed; }
    public void setAmountOwed(BigDecimal amountOwed) { this.amountOwed = amountOwed; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
