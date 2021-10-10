package model;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;

import java.util.Set;
import java.util.Calendar;
import java.util.Objects;

@NamedQueries({
    @NamedQuery(name="Especie.findAll",
        query="SELECT especie "+ 
                "FROM Especie especie"),
    @NamedQuery(name="Especie.findByNombre",
        query="SELECT especie "+ 
                "FROM Especie especie "+
                "WHERE especie.nombre = :nombre_especie")
})

@Entity
public class Especie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="especie_id")
    private int id;

    private String nombre;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Especie)) {
            return false;
        }
        Especie especie = (Especie) o;
        return id == especie.id && Objects.equals(nombre, especie.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}