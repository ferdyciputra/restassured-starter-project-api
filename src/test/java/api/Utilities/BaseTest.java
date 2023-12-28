package api.Utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    protected static String BASE_URL;
    protected final String ROOT_PATH = new File(System.getProperty("user.dir")).getAbsolutePath();
    protected final String ROOT_PATH_SCHEMA_JSON = ROOT_PATH + "/schema-json/";
    protected final String ROOT_PATH_DATASET = ROOT_PATH + "/dataset/";
    protected static ExtentTest extentTest;
    protected final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected static String TEST_NAME;
    private final String ROOT_PATH_RESOURCES = ROOT_PATH + "/src/test/resources/";
    private final int MAX_TIMEOUT_MILLISECONDS = 10000;

    @BeforeTest
    protected void setUpTest() throws IOException {
        loadProperties();
    }

    private void loadProperties() throws IOException {
        File file = new File(ROOT_PATH_RESOURCES + "api.properties");
        String absolutePath = file.getAbsolutePath();
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(absolutePath));

        BASE_URL = appProps.getProperty("baseUrl");
    }

    public RequestSpecification getSpecRequest() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(BASE_URL);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setAccept(ContentType.JSON);
        requestSpecBuilder.setConfig(getDefaultConfig());

        return requestSpecBuilder.build();
    }

    public ResponseSpecification getSpecResponse() {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200);

        return responseSpecBuilder.build();
    }

    public ResponseSpecification getSpecResponse(Integer expectStatusCode) {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(expectStatusCode);

        return responseSpecBuilder.build();
    }

    /**
     *  Set Config Log Only Enable If Validation Fails,
     *  Set Config Connection Timeout
     * @return RestAssuredConfig
     */
    public RestAssuredConfig getDefaultConfig(){
        LogConfig logConfig = LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails();

        return RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.socket.timeout", MAX_TIMEOUT_MILLISECONDS)
                        .setParam("http.connection.timeout", MAX_TIMEOUT_MILLISECONDS))
                .logConfig(logConfig);
    }

    public void sendLogExtentTest(Status status, String message) {
        if (extentTest != null) {
            switch (status) {
                case INFO:
                    extentTest.log(Status.INFO, message);
                    break;
                case SKIP:
                    extentTest.log(Status.SKIP, message);
                    break;
                case WARNING:
                    extentTest.log(Status.WARNING, message);
                    break;
                case FAIL:
                    extentTest.log(Status.FAIL, message);
                    break;
                case PASS:
                    extentTest.log(Status.PASS, message);
                    break;
                default:
                    break;
            }
        }
    }

    public void sendLogExtentTest(Status status, Markup markup) {
        if (extentTest != null) {
            switch (status) {
                case INFO:
                    extentTest.log(Status.INFO, markup);
                    break;
                case SKIP:
                    extentTest.log(Status.SKIP, markup);
                    break;
                case WARNING:
                    extentTest.log(Status.WARNING, markup);
                    break;
                case FAIL:
                    extentTest.log(Status.FAIL, markup);
                    break;
                case PASS:
                    extentTest.log(Status.PASS, markup);
                    break;
                default:
                    break;
            }
        }
    }

    public String createLabelPrimary(String message) {
        return "<span class='badge badge-info'>" + message + "</span>";
    }

    public Object[][] convertTwoDimensionalArrayFromListHashMap (List<HashMap<Object, Object>> dataList) {
        return dataList.stream()
                .map(data -> new Object[] { data })
                .toArray(Object[][]::new);
    }
}
