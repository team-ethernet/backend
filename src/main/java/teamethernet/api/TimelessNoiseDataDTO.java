package teamethernet.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("t")
public class TimelessNoiseDataDTO extends NoiseDataDTO {

    public TimelessNoiseDataDTO(final String name, final String unit, final double value) {
        setName(name);
        setUnit(unit);
        setValue((float) value);
    }

}
