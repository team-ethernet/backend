package teamethernet.senml_api;

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

}
