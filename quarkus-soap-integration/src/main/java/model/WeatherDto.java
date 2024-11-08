package model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WeatherDto {
    private BigDecimal temperature;
    private String conditions;
    private BigDecimal humidity;
}
