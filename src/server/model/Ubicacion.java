package model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


@Entity
public class Ubicacion {

   private long id
   @Id
   @Column(name="id")
   private float latitude;
   private float longitude;
    @Temporal(TemporalType.DATE)
    private Date fecha;


    public float getLatitude() {
        return this.latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
   


}
