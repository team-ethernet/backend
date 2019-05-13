package teamethernet.senml_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

import java.io.IOException;

public class CborFormatter implements Formatter {

    private static final ObjectMapper MAPPER = new ObjectMapper(new CBORFactory());

    private final JsonNode RECORDS;

    public CborFormatter() {
        RECORDS = MAPPER.createArrayNode();
    }

    public CborFormatter(final byte[] cborData) throws IOException {
        RECORDS = MAPPER.readValue(cborData, JsonNode.class);
    }

    public ObjectMapper getMapper() {
        return MAPPER;
    }

    public JsonNode getRecords() {
        return RECORDS;
    }

    public String endSenML(final JsonNode rootNode) throws JsonProcessingException, IOException {
        final byte[] bytes = MAPPER.writeValueAsBytes(rootNode);

        final StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02X", b));
        }

        return stringBuilder.toString();
    }

}
