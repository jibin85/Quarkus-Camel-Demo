package org.transformation.model.response;

import java.math.BigDecimal;

public class ClaimResponse {
    private BigDecimal claimAmt;

    public BigDecimal getClaimAmt() {
        return claimAmt;
    }

    public void setClaimAmt(BigDecimal claimAmt) {
        this.claimAmt = claimAmt;
    }

    @Override
    public String toString() {
        return "ClaimResponse{" +
                "claimAmt=" + claimAmt +
                '}';
    }
}
