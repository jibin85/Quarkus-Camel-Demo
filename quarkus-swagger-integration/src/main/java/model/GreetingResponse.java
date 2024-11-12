package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GreetingResponse {
    @JsonProperty("message")
    private String message;
    @JsonProperty("timestamp")
    private long timestamp;
}
