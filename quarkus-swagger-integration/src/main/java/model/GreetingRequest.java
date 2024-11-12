package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GreetingRequest {
    @JsonProperty("message")
    private String message;
    @JsonProperty("language")
    private String language;
}
