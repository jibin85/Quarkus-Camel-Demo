package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceResponse {
    private Double originalRate;
    private Double mrp;
    private Double addition;
}
