package api.Custom;

import java.util.Arrays;
import java.util.List;

public class CustomTableRequestResponse {
    private final List<String> request;
    private final List<String> response;

    public CustomTableRequestResponse(String request, String response) {
        this.request = Arrays.asList(request);
        this.response = Arrays.asList(response);
    }

    public List<String> getRequest() {
        return this.request;
    }

    public List<String> getResponse() {
        return this.response;
    }
}
