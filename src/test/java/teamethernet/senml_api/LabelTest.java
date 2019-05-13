package teamethernet.senml_api;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class LabelTest {

    @Test
    public void NAME_TO_VALUE_MAP_containsAllFields() {
        final List<Field> labelFields = Arrays.stream(Label.class.getDeclaredFields()).filter(
                field -> field.getType().equals(Label.class)).collect(Collectors.toList());

        assertEquals("You have probably added a new Label without updating the NAME_TO_VALUE_MAP with this value",
                labelFields.size(), Label.NAME_TO_VALUE_MAP.size());
    }

}