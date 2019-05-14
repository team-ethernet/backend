package teamethernet.senml_api;

import java.util.HashMap;
import java.util.Map;

abstract class Label<T> {

    private final Class<T> type;
    private final String stringRepresentation;

    private Label(final Class<T> type, final String stringRepresentation) {
        this.type = type;
        this.stringRepresentation = stringRepresentation;
    }

    Class<T> getClassType() {
        return type;
    }

    Pair attachValue(final T value) {
        return new Pair(value);
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }

    final static Label<String> BASE_NAME = new Label<String>(String.class, "bn") {
    };
    final static Label<Double> BASE_TIME = new Label<Double>(Double.class, "bt") {
    };
    final static Label<String> BASE_UNIT = new Label<String>(String.class, "bu") {
    };
    final static Label<Double> BASE_VALUE = new Label<Double>(Double.class, "bv") {
    };
    final static Label<Double> BASE_SUM = new Label<Double>(Double.class, "bs") {
    };
    final static Label<Integer> BASE_VERSION = new Label<Integer>(Integer.class, "bver") {
    };
    final static Label<String> NAME = new Label<String>(String.class, "n") {
    };
    final static Label<String> UNIT = new Label<String>(String.class, "u") {
    };
    final static Label<Double> VALUE = new Label<Double>(Double.class, "v") {
    };
    final static Label<String> STRING_VALUE = new Label<String>(String.class, "vs") {
    };
    final static Label<Boolean> BOOLEAN_VALUE = new Label<Boolean>(Boolean.class, "vb") {
    };
    final static Label<String> DATA_VALUE = new Label<String>(String.class, "vd") {
    };
    final static Label<Double> SUM = new Label<Double>(Double.class, "s") {
    };
    final static Label<Double> TIME = new Label<Double>(Double.class, "t") {
    };
    final static Label<Double> UPDATE_TIME = new Label<Double>(Double.class, "ut") {
    };

    final static Map<String, Label> NAME_TO_VALUE_MAP = new HashMap<String, Label>() {{
        put(BASE_NAME.toString(), BASE_NAME);
        put(BASE_TIME.toString(), BASE_TIME);
        put(BASE_UNIT.toString(), BASE_UNIT);
        put(BASE_VALUE.toString(), BASE_VALUE);
        put(BASE_SUM.toString(), BASE_SUM);
        put(BASE_VERSION.toString(), BASE_NAME);
        put(NAME.toString(), NAME);
        put(UNIT.toString(), UNIT);
        put(VALUE.toString(), VALUE);
        put(STRING_VALUE.toString(), STRING_VALUE);
        put(BOOLEAN_VALUE.toString(), BOOLEAN_VALUE);
        put(DATA_VALUE.toString(), DATA_VALUE);
        put(SUM.toString(), SUM);
        put(TIME.toString(), TIME);
        put(UPDATE_TIME.toString(), UPDATE_TIME);
    }};

    public class Pair {

        private T value;

        private Pair(final T value) {
            this.value = value;
        }

        Label<T> getLabel() {
            return Label.this;
        }

        public T getValue() {
            return value;
        }

    }

}
