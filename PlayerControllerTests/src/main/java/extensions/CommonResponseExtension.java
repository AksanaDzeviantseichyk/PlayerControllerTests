package extensions;

import http.CommonResponse;

public class CommonResponseExtension {
    public static <T> CommonResponse<T> throwIfNotTargetStatus(CommonResponse<T> response, int expectedStatusCode) {
        if (response.getStatusCode() != expectedStatusCode) {
            throw new RuntimeException(String.format("Status code is %d but should be %d", response.getStatusCode(), expectedStatusCode));
        }
        return response;
    }
}
