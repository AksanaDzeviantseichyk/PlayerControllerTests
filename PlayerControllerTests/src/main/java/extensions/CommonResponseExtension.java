package extensions;

import http.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonResponseExtension {
    private static final Logger logger = LoggerFactory.getLogger(CommonResponseExtension.class);
    public static <T> CommonResponse<T> throwIfNotTargetStatus(CommonResponse<T> response, int expectedStatusCode) {
        if (response.getStatusCode() != expectedStatusCode) {
            logger.error("Status code is {} but should be {}", response.getStatusCode(), expectedStatusCode);
            throw new RuntimeException(String.format("Status code is %d but should be %d", response.getStatusCode(), expectedStatusCode));
        }
        return response;
    }
}
