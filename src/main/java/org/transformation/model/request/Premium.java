package org.transformation.model.request;

import java.math.BigDecimal;

public class Premium {
    private String policyNumber;
    private BigDecimal premiumAmount;
    private String premiumScheme;
    private int validPeriod;
    private String validDate;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public BigDecimal getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(BigDecimal premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public String getPremiumScheme() {
        return premiumScheme;
    }

    public void setPremiumScheme(String premiumScheme) {
        this.premiumScheme = premiumScheme;
    }

    public int getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(int validPeriod) {
        this.validPeriod = validPeriod;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    @Override
    public String toString() {
        return "Premium{" +
                "policyNumber='" + policyNumber + '\'' +
                ", premiumAmount=" + premiumAmount +
                ", premiumScheme='" + premiumScheme + '\'' +
                ", validPeriod=" + validPeriod +
                ", validDate='" + validDate + '\'' +
                '}';
    }
}
