package emailRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailRequest {
    @JsonProperty
    private String to;
    @JsonProperty
    private String subject;
    @JsonProperty
    private String content;
    @JsonProperty
    private byte[] attachmentContent;
    @JsonProperty
    private String attachmentName;
    @JsonProperty
    private String attachmentContentType;
}
