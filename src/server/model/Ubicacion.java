package model;

import java.util.Calendar;
import java.util.Objects;
import javax.persistence.*;


@NamedQueries({
    @NamedQuery(name="Ubicacion.getMaxId",
        query="SELECT MAX(ubicacion.id) "+ 
                "FROM Ubicacion ubicacion ")

})

@Entity
public class Ubicacion {
  @Id
  @Column(name="ID") 
  private long id;

  private float latitude;
  private float longitude;

  @Temporal(TemporalType.DATE)
  private Calendar fecha;

  @ManyToOne(cascade=CascadeType.PERSIST)
  @JoinColumn(name="PUBLICACION_ID")
  private Publicacion publicacion;

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

  public Calendar getFecha() {
    return this.fecha;
  }

  public void setFecha(Calendar fecha) {
    this.fecha = fecha;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Publicacion getPublicacion() {
    return this.publicacion;
  }

  public void setPublicacion(Publicacion publicacion) {
    this.publicacion = publicacion;
  }
}
