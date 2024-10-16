package org.transformation.model.request;

import java.math.BigDecimal;


public class Claim {
    private BigDecimal maxClaimAmt;

    public BigDecimal getMaxClaimAmt() {
        return maxClaimAmt;
    }

    public void setMaxClaimAmt(BigDecimal maxClaimAmt) {
        this.maxClaimAmt = maxClaimAmt;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "maxClaimAmt=" + maxClaimAmt +
                '}';
    }
}
