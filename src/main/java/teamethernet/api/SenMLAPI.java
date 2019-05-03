package teamethernet.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

import java.io.IOException;

public interface SenMLAPI {

    static JsonNode convertCBORToJSON(final String hex) throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new CBORFactory());

        final byte[] cborData = hexStringToByteArray(hex);
        final JsonNode jsonNode = mapper.readValue(cborData, JsonNode.class);

        return jsonNode;
    }

    static byte[] hexStringToByteArray(final String hex) {
        final int length = hex.length();
        final byte[] data = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }

        return data;
    }

}
