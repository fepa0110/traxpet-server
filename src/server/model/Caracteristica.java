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

@NamedQueries({
    @NamedQuery(name="Caracteristica.findByEspecie",
        query="SELECT DISTINCT valor.caracteristica "+ 
                "FROM Valor valor "+
                "WHERE valor.especie.nombre = :especie_nombre")
})

@Entity
public class Caracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="caracteristica_id")
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

}
