package teamethernet.rest_api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NoiseDataDTO {
    @JsonProperty("bn")
    private String name;

    @JsonProperty("u")
    private String unit;

    @JsonProperty("v")
    private float value;

    @JsonProperty("t")
    private long unixTime;

    public NoiseDataDTO() {

    }

    public NoiseDataDTO(final String name, final String unit, final float value, final long unixTime) {
        setName(name);
        setUnit(unit);
        setValue(value);
        setUnixTime(unixTime);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }

    public float getValue() {
        return value;
    }

    public void setValue(final float value) {
        this.value = value;
    }

    public long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(final long unixTime) {
        this.unixTime = unixTime;
    }
}
