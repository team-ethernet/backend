package teamethernet.web;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class NoiseData {
    @Id
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    private Double value;

    public NoiseData() {

    }

    public NoiseData(final int id, final double value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}