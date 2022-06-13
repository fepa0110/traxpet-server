package model;

import java.util.Calendar;
import java.util.Objects;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries(
  {
    @NamedQuery(
      name = "Ubicacion.getMaxId",
      query = "SELECT MAX(ubicacion.id) " + "FROM Ubicacion ubicacion "
    ),
    @NamedQuery(
      name = "Ubicacion.findByPublicacion",
      query = "SELECT ubicacion "+ 
                "FROM Ubicacion ubicacion "+
                "WHERE ubicacion.publicacion.id = :id"),
    
  })

@Entity
public class Ubicacion {

  @Id
  @Column(name = "ID")
  private long id;

  private float latitude;
  private float longitude;

  @Temporal(TemporalType.DATE)
  private Calendar fecha;

  @JsonIgnore
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "PUBLICACION_ID")
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


  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Ubicacion)) {
            return false;
        }
        Ubicacion ubicacion = (Ubicacion) o;
        return id == ubicacion.id && latitude == ubicacion.latitude && longitude == ubicacion.longitude && Objects.equals(fecha, ubicacion.fecha) && Objects.equals(publicacion, ubicacion.publicacion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, latitude, longitude, fecha, publicacion);
  }


}
