package teamethernet.senml_api;

import java.util.HashMap;
import java.util.Map;

public interface Label<T> {

    Class<T> getClassType();

    Label<String> BASE_NAME = new Label<String>() {
        @Override
        public Class<String> getClassType() {
            return String.class;
        }

        @Override
        public String toString() {
            return "bn";
        }
    };
    Label<Double> BASE_TIME = new Label<Double>() {
        @Override
        public Class<Double> getClassType() {
            return Double.class;
        }

        @Override
        public String toString() {
            return "bt";
        }
    };
    Label<String> BASE_UNIT = new Label<String>() {
        @Override
        public Class<String> getClassType() {
            return String.class;
        }

        @Override
        public String toString() {
            return "bu";
        }
    };
    Label<Double> BASE_VALUE = new Label<Double>() {
        @Override
        public Class<Double> getClassType() {
            return Double.class;
        }

        @Override
        public String toString() {
            return "bv";
        }
    };
    Label<Double> BASE_SUM = new Label<Double>() {
        @Override
        public Class<Double> getClassType() {
            return Double.class;
        }

        @Override
        public String toString() {
            return "bs";
        }
    };
    Label<Integer> BASE_VERSION = new Label<Integer>() {
        @Override
        public Class<Integer> getClassType() {
            return Integer.class;
        }

        @Override
        public String toString() {
            return "bver";
        }
    };
    Label<String> NAME = new Label<String>() {
        @Override
        public Class<String> getClassType() {
            return String.class;
        }

        @Override
        public String toString() {
            return "n";
        }
    };
    Label<String> UNIT = new Label<String>() {
        @Override
        public Class<String> getClassType() {
            return String.class;
        }

        @Override
        public String toString() {
            return "u";
        }
    };
    Label<Double> VALUE = new Label<Double>() {
        @Override
        public Class<Double> getClassType() {
            return Double.class;
        }

        @Override
        public String toString() {
            return "v";
        }
    };
    Label<String> STRING_VALUE = new Label<String>() {
        @Override
        public Class<String> getClassType() {
            return String.class;
        }

        @Override
        public String toString() {
            return "vs";
        }
    };
    Label<Boolean> BOOLEAN_VALUE = new Label<Boolean>() {
        @Override
        public Class<Boolean> getClassType() {
            return Boolean.class;
        }

        @Override
        public String toString() {
            return "vb";
        }
    };
    Label<String> DATA_VALUE = new Label<String>() {
        @Override
        public Class<String> getClassType() {
            return String.class;
        }

        @Override
        public String toString() {
            return "vd";
        }
    };
    Label<Double> SUM = new Label<Double>() {
        @Override
        public Class<Double> getClassType() {
            return Double.class;
        }

        @Override
        public String toString() {
            return "s";
        }
    };
    Label<Double> TIME = new Label<Double>() {
        @Override
        public Class<Double> getClassType() {
            return Double.class;
        }

        @Override
        public String toString() {
            return "t";
        }
    };
    Label<Double> UPDATE_TIME = new Label<Double>() {
        @Override
        public Class<Double> getClassType() {
            return Double.class;
        }

        @Override
        public String toString() {
            return "ut";
        }
    };

    Map<String, Label> NAME_TO_VALUE_MAP = new HashMap<String, Label>() {{
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

}
