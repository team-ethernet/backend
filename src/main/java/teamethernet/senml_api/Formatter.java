package teamethernet.senml_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

interface Formatter {

    ObjectMapper getMapper();

    JsonNode getRecords();

    String getStringValue(Label<String> label, JsonNode record);

    Integer getIntegerValue(Label<Integer> label, JsonNode record);

    Double getDoubleValue(Label<Double> label, JsonNode record);

    Boolean getBooleanValue(Label<Boolean> label, JsonNode record);

    String getSenML(final JsonNode rootNode) throws IOException;

}
