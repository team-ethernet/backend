package teamethernet.senml_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

interface Formatter {

    ObjectMapper getMapper();

    String endSenML(final JsonNode rootNode) throws JsonProcessingException, IOException;

}
