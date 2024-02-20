package http;

import io.restassured.response.Response;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class CommonResponse<T> {
    private static final Logger logger = LoggerFactory.getLogger(CommonResponse.class);

    private T body;
    private String errorMessage;
    private String content;
    private int statusCode;
    private boolean isSuccessful;

    public CommonResponse(Response response, Class<T> responseType) {
        this.content = response.getBody().asString();
        this.statusCode = response.getStatusCode();
        this.isSuccessful = response.getStatusCode() >= 200 && response.getStatusCode() < 300;

        try {
            if (response.getBody() != null && response.getBody().asString() != null) {
                this.body = response.getBody().as(responseType);
            }
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }
}
