package api.TestCase;

import api.Custom.CustomTableRequestResponse;
import api.Custom.ResourcesPathAPI;
import api.PojoClass.ApiCreateUsers.RequestBody.RequestApiCreateUsers;
import api.PojoClass.ApiCreateUsers.ResponseBody.ResponseApiCreateUsers;
import api.Utilities.BaseTest;
import api.Utilities.DataReader;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class TestApiCreateUsers extends BaseTest {

    @Test
    public void checkJsonSchemaResponse() throws FileNotFoundException {
        FileReader fileSchemaJson = new FileReader(ROOT_PATH_SCHEMA_JSON + "create-users-schema.json");

        RequestApiCreateUsers req = new RequestApiCreateUsers();

        // Hit API
        Response response =
                given().
                    spec(getSpecRequest()).
                    body(req).
                when().
                    post(ResourcesPathAPI.CreateUsers.toString()).
                then().
                    spec(getSpecResponse(201)).
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
    public void checkSuccessCreateUsers(){
        String nameFileJson = "dataset-create-users.json";
        String fileLocation = ROOT_PATH_DATASET + nameFileJson;

        try {
            JSONArray fileJsonArray = DataReader.getDataJsonArray(fileLocation);
            int totalDataset = fileJsonArray.size();

            SoftAssert softAssert = new SoftAssert();

            for (int i = 0; i < totalDataset; i++) {
                JSONObject fileJsonObject = (JSONObject) fileJsonArray.get(i);
                String currentIteration = "Iteration " + (i+1) + ": ";

                String job = fileJsonObject.get("job").toString();
                String name = fileJsonObject.get("name").toString();

                // Create Request Body
                RequestApiCreateUsers req = new RequestApiCreateUsers();
                req.setJob(job);
                req.setName(name);

                // Hit API
                ResponseApiCreateUsers response =
                        given().
                            spec(getSpecRequest()).
                            body(req).
                        when().
                            post(ResourcesPathAPI.CreateUsers.toString()).
                        then().
                            spec(getSpecResponse(201)).
                            extract().response().as(ResponseApiCreateUsers.class);

                //Generate Markup Request and Response to Extent Report
                String jsonReq = gson.toJson(req);
                String jsonResponse = gson.toJson(response);

                sendLogExtentTest(Status.INFO, createLabelPrimary(currentIteration));
                sendLogExtentTest(Status.INFO, MarkupHelper.toTable(new CustomTableRequestResponse(jsonReq, jsonResponse), "table-sm"));

                sendLogExtentTest(Status.INFO, "Verify Response value for key name is " + createLabelPrimary(name));
                softAssert.assertEquals(response.getName(), name, currentIteration + "Verify Name");

                sendLogExtentTest(Status.INFO, "Verify Response value for key job is " + createLabelPrimary(job));
                softAssert.assertEquals(response.getJob(), job, currentIteration + "Verify Job");
            }

            softAssert.assertAll();

        } catch (Exception e) {
            Assert.fail(e.toString());
        }

    }
}
