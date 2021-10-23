package model;

import java.util.Calendar;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries(
  {
    @NamedQuery(
      name = "Especie.findAllEnable",
      query = "SELECT especie " +
      "FROM Especie especie Where especie.deshabilitado=false"
    ),
    @NamedQuery(
      name = "Especie.findAll",
      query = "SELECT especie " + "FROM Especie especie "
    ),
    @NamedQuery(
      name = "Especie.findByNombre",
      query = "SELECT especie " +
      "FROM Especie especie " +
      "WHERE especie.nombre = :nombre_especie"
    ),
  }
)
@Entity
public class Especie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "especie_id")
  private int id;

  private Boolean deshabilitado;
  private String nombre;

  public Especie() {}

  public Especie(int id, Boolean deshabilitado, String nombre) {
    this.id = id;
    this.deshabilitado = deshabilitado;
    this.nombre = nombre;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Boolean isDeshabilitado() {
    return this.deshabilitado;
  }

  public Boolean getDeshabilitado() {
    return this.deshabilitado;
  }

  public void setDeshabilitado(Boolean deshabilitado) {
    this.deshabilitado = deshabilitado;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Especie id(int id) {
    setId(id);
    return this;
  }

  public Especie deshabilitado(Boolean deshabilitado) {
    setDeshabilitado(deshabilitado);
    return this;
  }

  public Especie nombre(String nombre) {
    setNombre(nombre);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Especie)) {
      return false;
    }
    Especie especie = (Especie) o;
    return (
      id == especie.id &&
      Objects.equals(deshabilitado, especie.deshabilitado) &&
      Objects.equals(nombre, especie.nombre)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, deshabilitado, nombre);
  }

  @Override
  public String toString() {
    return (
      "{" +
      " id='" +
      getId() +
      "'" +
      ", deshabilitado='" +
      isDeshabilitado() +
      "'" +
      ", nombre='" +
      getNombre() +
      "'" +
      "}"
    );
  }
}
