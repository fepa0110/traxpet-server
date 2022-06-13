package model;

import java.util.Calendar;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries(
  {
    @NamedQuery(
      name = "Notificacion.getMaxId",
      query = "SELECT MAX(notificacion.id) " + "FROM Notificacion notificacion "
    ),
  }
)
@Entity
public class Notificacion {

  @Id
  @Column(name = "notificacion_id")
  private long id;

  private boolean vista;

  @Temporal(TemporalType.TIMESTAMP)
  private Calendar fechaNotificacion;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  private Usuario notificante;

  private Publicacion publicacion;

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public boolean isVista() {
    return this.vista;
  }

  public boolean getVista() {
    return this.vista;
  }

  public void setVista(boolean vista) {
    this.vista = vista;
  }

  public Calendar getFechaNotificacion() {
    return this.fechaNotificacion;
  }

  public void setFechaNotificacion(Calendar fechaNotificacion) {
    this.fechaNotificacion = fechaNotificacion;
  }

  public Usuario getUsuario() {
    return this.usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public Usuario getNotificante() {
    return this.notificante;
  }

  public void setNotificante(Usuario notificante) {
    this.notificante = notificante;
  }

  public Publicacion getPublicacion() {
    return this.publicacion;
  }

  public void setPublicacion(Publicacion publicacion) {
    this.publicacion = publicacion;
  }
}
