package model;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Enumerated;
import javax.persistence.Convert;
import javax.persistence.EnumType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;

import java.util.Set;
import java.util.Collection;
import java.util.Calendar;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries({
    @NamedQuery(name="Valor.findByCaracteristica",
        query="SELECT valor "+ 
                "FROM Valor valor "+
                "WHERE valor.caracteristica.nombre = :caracteristica_nombre"),
        @NamedQuery(name="Valor.findByCaracteristicaEspecieValor",
        query="SELECT valor "+ 
                "FROM Valor valor "+
                "WHERE LOWER(valor.caracteristica.nombre) = :caracteristica_nombre "+
                "AND LOWER(valor.especie.nombre) = :especie_nombre "+
                "AND LOWER(valor.nombre) = :valor_nombre"),
        @NamedQuery(name="Valor.getMaxId",
        query="SELECT MAX(valor.id) "+ 
                "FROM Valor valor ")
})

@Entity
public class Valor {

    @Id
    @Column(name="valor_id")
    private long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name="ESPECIE_ID")
    private Especie especie;

    @ManyToOne
    @JoinColumn(name="CARACTERISTICA_ID")
    private Caracteristica caracteristica;

    // @JsonIgnore
    @ManyToMany(mappedBy="valores")
    private Collection<Mascota> mascotas;

    private Boolean deshabilitado;

    public Valor() {}

    public Valor(int id, String nombre, Caracteristica caracteristica) {
        this.id = id;
        this.nombre = nombre;
        this.caracteristica = caracteristica;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean isDeshabilitado() {
        return this.deshabilitado;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Especie getEspecie() {
        return this.especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Collection<Mascota> getMascotas() {
        return this.mascotas;
    }

    public void setMascotas(Collection<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public Caracteristica getCaracteristica() {
        return this.caracteristica;
    }

    public void setCaracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
    }

    public Boolean getDeshabilitado() {
        return deshabilitado;
    }

    public void setDeshabilitado(Boolean deshabilitado) {
        this.deshabilitado = deshabilitado;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Valor)) {
            return false;
        }
        Valor valor = (Valor) o;
        return id == valor.id && Objects.equals(nombre, valor.nombre) && Objects.equals(especie, valor.especie) && Objects.equals(caracteristica, valor.caracteristica) && Objects.equals(mascotas, valor.mascotas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, especie, caracteristica, mascotas);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", especie='" + getEspecie() + "'" +
            ", caracteristica='" + getCaracteristica() + "'" +
            ", mascotas='" + getMascotas() + "'" +
            ", deshabilitado='" + isDeshabilitado() + "'" +
            "}";
    }

}
