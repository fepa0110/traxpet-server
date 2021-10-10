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
import java.util.Collection;
import java.util.List;
import java.util.Calendar;
import java.util.Objects;

@NamedQueries({
    @NamedQuery(name="Mascota.findById",
        query="SELECT mascota "+ 
                "FROM Mascota mascota "+
                "WHERE mascota.id = :mascota_id"),
    @NamedQuery(name="Mascota.getMaxIdMascotas",
        query="SELECT MAX(mascota.id) "+ 
                "FROM Mascota mascota ")
})

@Entity
public class Mascota {

    @Id
    @Column(name="mascota_id")
    private long id;

    private String nombre;

    @ManyToMany(cascade=CascadeType.ALL)
    private List<Valor> valores;

    @ManyToOne
    @JoinColumn(name="ESPECIE_ID")
    private Especie especie;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Valor> getValores() {
        return this.valores;
    }

    public void setValores(List<Valor> valores) {
        this.valores = valores;
    }
    
    public Especie getEspecie() {
        return this.especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Mascota)) {
            return false;
        }
        Mascota mascota = (Mascota) o;
        return id == mascota.id && Objects.equals(nombre, mascota.nombre) && Objects.equals(valores, mascota.valores) && Objects.equals(especie, mascota.especie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, valores, especie);
    }
}
