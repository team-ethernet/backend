package teamethernet.senml_api;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import javafx.util.Pair;

import java.io.IOException;

public class SenMlAPI<T extends Formatter> {

    private final T formatter;
    private final ObjectMapper mapper;
    private final JsonNode rootNode;

    private SenMlAPI(T formatter, ObjectMapper mapper){
        this.formatter = formatter;
        this.mapper = mapper;

        rootNode = mapper.createArrayNode();
    }

    public static SenMlAPI<JsonFormatter> initJsonEncode() {
        return new SenMlAPI<>(new JsonFormatter(), new ObjectMapper(new JsonFactory()));
    }

    public static SenMlAPI<CborFormatter> initCborEncode() {
        return new SenMlAPI<>(new CborFormatter(), new ObjectMapper(new CBORFactory()));
    }

    public <T> void addRecord(final Pair<Label<T>, T>... pairs) {
    }

    /*
    * Old code
    * ↓
    * */

    public JsonNode convertCBORToJSON(final String hex) throws IOException {
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
