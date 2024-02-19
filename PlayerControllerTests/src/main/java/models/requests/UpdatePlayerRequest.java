package models.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UpdatePlayerRequest {
    @Builder.Default
    private int age = 0;
    @Builder.Default
    private String gender = null;
    @Builder.Default
    private String login = null;
    @Builder.Default
    private String password = null;
    @Builder.Default
    private String role = null;
    @Builder.Default
    private String screenName = null;
}
