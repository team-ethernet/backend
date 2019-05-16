package teamethernet.database;

import teamethernet.rest_api.NoiseDataDTO;
import teamethernet.rest_api.TimelessNoiseDataDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
@SqlResultSetMapping(name = "NoiseDataDTO", classes = {
        @ConstructorResult(targetClass = NoiseDataDTO.class,
                columns = {
                        @ColumnResult(name = "bn"),
                        @ColumnResult(name = "u"),
                        @ColumnResult(name = "v", type = Float.class),
                        @ColumnResult(name = "t", type = Long.class),
                })
})
@SqlResultSetMapping(name = "TimelessNoiseDataDTO", classes = {
        @ConstructorResult(targetClass = TimelessNoiseDataDTO.class,
                columns = {
                        @ColumnResult(name = "bn"),
                        @ColumnResult(name = "u"),
                        @ColumnResult(name = "v", type = Double.class),
                })
})
@Table(name = "noise_data")
public class NoiseData {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "bn")
    private String bn;

    @Column(name = "u")
    private String u;

    @Column(name = "v")
    private double v;

    @Column(name = "t")
    private long t = new Date().getTime();

    public NoiseData() {
    }

    public NoiseData(final String name, final String unit, final double value) {
        setName(name);
        setUnit(unit);
        setValue(value);
        setUnixTime((t / 1000) * 1000);
    }

    public NoiseData(final String name, final String unit, final double value, final long unixTime) {
        setName(name);
        setUnit(unit);
        setValue(value);
        setUnixTime(unixTime);
    }

    public String getName() {
        return bn;
    }

    public void setName(String name) {
        this.bn = name;
    }

    public String getUnit() {
        return u;
    }

    public void setUnit(final String unit) {
        this.u = unit;
    }

    public double getValue() {
        return v;
    }

    public void setValue(final double value) {
        this.v = value;
    }

    public long getUnixTime() {
        return t;
    }

    public void setUnixTime(final long unixTime) {
        this.t = unixTime;
    }

}