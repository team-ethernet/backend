package teamethernet.senml_api;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import javafx.util.Pair;

import java.io.IOException;

public class SenMLAPI<T extends Formatter> {

    private static final String STRING_INSTANCE = "";
    private static final Double DOUBLE_INSTANCE = 0.0;
    private static final Integer INTEGER_INSTANCE = 0;
    private static final Boolean BOOLEAN_INSTANCE = false;

    private final T formatter;
    private final ObjectMapper mapper;
    private final JsonNode rootNode;

    private SenMLAPI(T formatter, ObjectMapper mapper) {
        this.formatter = formatter;
        this.mapper = mapper;

        rootNode = mapper.createArrayNode();
    }

    public static SenMLAPI<JsonFormatter> initJsonEncode() {
        return new SenMLAPI<>(new JsonFormatter(), new ObjectMapper(new JsonFactory()));
    }

    public static SenMLAPI<CborFormatter> initCborEncode() {
        return new SenMLAPI<>(new CborFormatter(), new ObjectMapper(new CBORFactory()));
    }

    public <S> void addRecord(final Pair<Label<S>, S>... pairs) {
        final JsonNode record = mapper.createObjectNode();

        for (final Pair<Label<S>, S> pair : pairs) {
            Class<S> type = pair.getKey().getClassType();

            if (type.isInstance(STRING_INSTANCE)) {
                ((ObjectNode) record).put(pair.getKey().toString(), (String) pair.getValue());
            } else if (type.isInstance(DOUBLE_INSTANCE)) {
                ((ObjectNode) record).put(pair.getKey().toString(), (double) pair.getValue());
            } else if (type.isInstance(INTEGER_INSTANCE)) {
                ((ObjectNode) record).put(pair.getKey().toString(), (int) pair.getValue());
            } else if (type.isInstance(BOOLEAN_INSTANCE)) {
                ((ObjectNode) record).put(pair.getKey().toString(), (boolean) pair.getValue());
            } else {
                throw new UnsupportedOperationException(
                        type + " is not supported. Use String, Double, Integer or Boolean");
            }
        }

        ((ArrayNode) rootNode).add(record);
    }

    public String endSenML() {
        return rootNode.asText();
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
