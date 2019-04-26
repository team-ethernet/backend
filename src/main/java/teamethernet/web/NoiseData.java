package teamethernet.web;

import javax.persistence.*;
import java.util.Date;

@Entity
public class NoiseData {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String noiseSensorId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    private Integer value;

    public NoiseData() {}

    public NoiseData(final String noiseSensorId, final int value) {
        this.noiseSensorId = noiseSensorId;
        date.setTime((date.getTime() / 1000) * 1000);
        this.value = value;
    }

    public NoiseData(final String noiseSensorId, final Date date, final int value) {
        this.noiseSensorId = noiseSensorId;
        this.date = date;
        this.value = value;
    }

    public String getNoiseSensorId() {
        return noiseSensorId;
    }

    public void setNoiseSensorId(String noiseSensorId) {
        this.noiseSensorId = noiseSensorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}