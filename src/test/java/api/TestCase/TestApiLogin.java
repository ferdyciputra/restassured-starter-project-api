package api.TestCase;

import api.Custom.CustomTableRequestResponse;
import api.Custom.ResourcesPathAPI;
import api.PojoClass.ApiLogin.RequestBody.RequestApiLogin;
import api.PojoClass.ApiLogin.ResponseBody.ResponseApiLogin;
import api.Utilities.BaseTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class TestApiLogin extends BaseTest {

    @Test
    public void checkJsonSchemaResponse() throws FileNotFoundException {
        FileReader fileSchemaJson = new FileReader(ROOT_PATH_SCHEMA_JSON + "login-schema.json");

        RequestApiLogin req = new RequestApiLogin();

        // Hit API
        Response response =
                given().
                    spec(getSpecRequest()).
                    body(req).
                when().
                    post(ResourcesPathAPI.Login.toString()).
                then().
                    spec(getSpecResponse(200)).
                    extract().response();

        //Generate Markup Request and Response to Extent Report
        String jsonReq = gson.toJson(req);
        String jsonResponse = gson.toJson(response.asString());

        sendLogExtentTest(Status.INFO, MarkupHelper.toTable(new CustomTableRequestResponse(jsonReq, jsonResponse), "table-sm"));

        // Validate JSON Schema Response
        sendLogExtentTest(Status.INFO, "Verify JSON Schema Response");
        response.then().assertThat().body(matchesJsonSchema(fileSchemaJson));
    }

    @Test
    public void checkSuccessLogin(){
        RequestApiLogin req = new RequestApiLogin();

        // Hit API
        ResponseApiLogin response =
                given().
                    spec(getSpecRequest()).
                    body(req).
                when().
                    post(ResourcesPathAPI.Login.toString()).
                then().
                    spec(getSpecResponse(200)).
                    extract().response().as(ResponseApiLogin.class);

        //Generate Markup Request and Response to Extent Report
        String jsonReq = gson.toJson(req);
        String jsonResponse = gson.toJson(response);

        sendLogExtentTest(Status.INFO, MarkupHelper.toTable(new CustomTableRequestResponse(jsonReq, jsonResponse), "table-sm"));

        // Validate JSON Schema Response
        sendLogExtentTest(Status.INFO, "Verify Response should have field token");
        String responseToken = response.getToken();
        Assert.assertNotNull(responseToken, "Verify field token is not null");
    }

    @Test
    public void checkFailedLoginMissingPassword(){
        String expectedErrorMessage = "Missing password";
        RequestApiLogin req = new RequestApiLogin();
        req.setPassword(null);

        // Hit API
        ResponseApiLogin response =
                given().
                    spec(getSpecRequest()).
                    body(req).
                when().
                    post(ResourcesPathAPI.Login.toString()).
                then().
                    spec(getSpecResponse(400)).
                    extract().response().as(ResponseApiLogin.class);

        //Generate Markup Request and Response to Extent Report
        String jsonReq = gson.toJson(req);
        String jsonResponse = gson.toJson(response);

        sendLogExtentTest(Status.INFO, MarkupHelper.toTable(new CustomTableRequestResponse(jsonReq, jsonResponse), "table-sm"));

        sendLogExtentTest(Status.INFO, "Verify Response value for key error is " + createLabelPrimary(expectedErrorMessage));
        Assert.assertEquals(response.getError(), expectedErrorMessage, "Verify value field error");
    }
}
