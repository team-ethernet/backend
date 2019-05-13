package teamethernet.senml_api;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFormatter implements Formatter {

    private static final ObjectMapper MAPPER = new ObjectMapper(new JsonFactory());

    public ObjectMapper getMapper() {
        return MAPPER;
    }

    public String endSenML(final JsonNode rootNode) throws JsonProcessingException {
        return MAPPER.writeValueAsString(rootNode);
    }

}
