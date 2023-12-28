package api.Utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class DataReader extends BaseTest{
    private static final JSONParser parser = new JSONParser();

    public static JSONArray getDataJsonArray(String jsonFileLocation) throws IOException, ParseException {
        Object fileJson = parser.parse(new FileReader(jsonFileLocation));

        return (JSONArray) fileJson;
    }

    public static String getDataJsonString(String jsonFileLocation) throws IOException {
        return FileUtils.readFileToString(new File(jsonFileLocation), StandardCharsets.UTF_8);
    }

    public static List<HashMap<Object, Object>> getJsonDataListHashMap(String jsonFileLocation) throws IOException {
        String jsonString = DataReader.getDataJsonString(jsonFileLocation);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(jsonString, new TypeReference<List<HashMap<Object, Object>>>(){});
    }
}
