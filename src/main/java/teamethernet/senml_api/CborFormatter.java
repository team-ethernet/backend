package teamethernet.senml_api;

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
        System.out.println();
    }

    public ObjectMapper getMapper() {
        return MAPPER;
    }

    public JsonNode getRecords() {
        return RECORDS;
    }

    public String getStringValue(Label<String> label, JsonNode record) {
        return record.get(label.toString()).asText();
    }

    public Integer getIntegerValue(Label<Integer> label, JsonNode record) {
        return record.get(label.toString()).intValue();
    }

    public Double getDoubleValue(Label<Double> label, JsonNode record) {
        return record.get(label.toString()).doubleValue();
    }

    public Boolean getBooleanValue(Label<Boolean> label, JsonNode record) {
        return record.get(label.toString()).booleanValue();
    }

    public String getSenML(final JsonNode rootNode) throws IOException {
        final byte[] bytes = MAPPER.writeValueAsBytes(rootNode);

        return byteArrayToHexString(bytes);
    }

    private String byteArrayToHexString (final byte[] bytes) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02X", b));
        }

        return stringBuilder.toString();
    }

}
