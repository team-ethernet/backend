package teamethernet.web;

import javax.persistence.*;
import java.util.Date;

@Entity
public class NoiseData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String unit;

    private Integer value;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    public NoiseData() {
    }

    public NoiseData(final String name, final String unit, final int value) {
        setName(name);
        setUnit(unit);
        setValue(value);
        date.setTime((date.getTime() / 1000) * 1000);
    }

    public NoiseData(final String name, final String unit, final int value, final Date date) {
        setName(name);
        setUnit(unit);
        setValue(value);
        setDate(date);
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

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}