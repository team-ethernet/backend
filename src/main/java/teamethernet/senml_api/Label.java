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

}
