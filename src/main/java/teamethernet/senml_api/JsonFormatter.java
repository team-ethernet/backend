package teamethernet.senml_api;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonFormatter implements Formatter {

    private static final ObjectMapper MAPPER = new ObjectMapper(new JsonFactory());

    private final JsonNode RECORDS;

    public JsonFormatter() {
        RECORDS = MAPPER.createArrayNode();
    }

    public JsonFormatter(final String jsonString) throws IOException {
        RECORDS = MAPPER.readTree(jsonString);
    }

    public ObjectMapper getMapper() {
        return MAPPER;
    }

    public JsonNode getRecords() {
        return RECORDS;
    }

    public String endSenML(final JsonNode rootNode) throws JsonProcessingException {
        return MAPPER.writeValueAsString(rootNode);
    }

}
