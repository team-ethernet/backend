package teamethernet.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class NoiseData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String unit;

    private float value;

    private long unixTime = new Date().getTime();

    public NoiseData() {
    }

    public NoiseData(final String name, final String unit, final float value) {
        setName(name);
        setUnit(unit);
        setValue(value);
        setUnixTime((unixTime / 1000) * 1000);
    }

    public NoiseData(final String name, final String unit, final float value, final long unixTime) {
        setName(name);
        setUnit(unit);
        setValue(value);
        setUnixTime(unixTime);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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