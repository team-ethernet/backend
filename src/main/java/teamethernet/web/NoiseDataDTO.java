package teamethernet.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import teamethernet.database.NoiseData;

public class NoiseDataDTO {
    @JsonProperty("bn")
    private String name;

    @JsonProperty("u")
    private String unit;

    @JsonProperty("v")
    private float value;

    @JsonProperty("t")
    private long unixTime;

    public static NoiseDataDTO fromEntity(NoiseData noiseData){
        final NoiseDataDTO noiseDataDTO = new NoiseDataDTO();

        noiseDataDTO.setName(noiseData.getName());
        noiseDataDTO.setUnit(noiseData.getUnit());
        noiseDataDTO.setValue(noiseData.getValue());
        noiseDataDTO.setUnixTime(noiseData.getUnixTime());

        return noiseDataDTO;
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
