package teamethernet.senml_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.util.Pair;

import java.io.IOException;

class SenMLAPI<T extends Formatter> {

    private static final String STRING_INSTANCE = "";
    private static final Double DOUBLE_INSTANCE = 0.0;
    private static final Integer INTEGER_INSTANCE = 0;
    private static final Boolean BOOLEAN_INSTANCE = false;

    private final T formatter;
    private final JsonNode rootNode;

    private SenMLAPI(T formatter) {
        this.formatter = formatter;

        rootNode = formatter.getMapper().createArrayNode();
    }

    static SenMLAPI<JsonFormatter> initJsonEncode() {
        return new SenMLAPI<>(new JsonFormatter());
    }

    static SenMLAPI<CborFormatter> initCborEncode() {
        return new SenMLAPI<>(new CborFormatter());
    }

    @SafeVarargs
    final <S> void addRecord(final Pair<Label<S>, S>... pairs) {
        final JsonNode record = formatter.getMapper().createObjectNode();

        for (final Pair<Label<S>, S> pair : pairs) {
            Class<S> type = pair.getKey().getClassType();

            if (type.isInstance(STRING_INSTANCE)) {
                ((ObjectNode) record).put(pair.getKey().toString(), (String) pair.getValue());
            } else if (type.isInstance(DOUBLE_INSTANCE)) {
                ((ObjectNode) record).put(pair.getKey().toString(), (Double) pair.getValue());
            } else if (type.isInstance(INTEGER_INSTANCE)) {
                ((ObjectNode) record).put(pair.getKey().toString(), (Integer) pair.getValue());
            } else if (type.isInstance(BOOLEAN_INSTANCE)) {
                ((ObjectNode) record).put(pair.getKey().toString(), (Boolean) pair.getValue());
            } else {
                throw new UnsupportedOperationException(
                        type + " is not supported. Use String, Double, Integer or Boolean");
            }
        }

        ((ArrayNode) rootNode).add(record);
    }

    String endSenML() throws JsonProcessingException, IOException {
        return formatter.endSenML(rootNode);
    }

}
