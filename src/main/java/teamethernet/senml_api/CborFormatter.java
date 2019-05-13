package teamethernet.senml_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

import java.io.IOException;

public class CborFormatter implements Formatter {

    private static final ObjectMapper MAPPER = new ObjectMapper(new CBORFactory());

    public ObjectMapper getMapper() {
        return MAPPER;
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
