package teamethernet.senml_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

import java.io.IOException;

public abstract class SenMLAPI {

    private static final ObjectMapper mapper = new ObjectMapper(new CBORFactory());

    public static JsonNode convertCBORToJSON(final String hex) throws IOException {
        final byte[] cborData = hexStringToByteArray(hex);

        return mapper.readValue(cborData, JsonNode.class);
    }

    private static byte[] hexStringToByteArray(final String hex) {
        final byte[] data = new byte[hex.length() / 2];

        for (int i = 0; i < data.length; i++) {
            final int index = i * 2;

            final int hexAtIndex = Integer.parseInt(hex.substring(index, index + 2), 16);
            data[i] = (byte) hexAtIndex;
        }

        return data;
    }

}
