/* package model;

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

import java.util.Set;
import java.util.Calendar;

/* @NamedQueries({
    @NamedQuery(name="Usuario.findAll",
        query="SELECT usuario "+ 
                "FROM Usuario usuario")
}) 
@Entity
public class CaracteristicasMascotas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CaracteristicasMascotas_id")
    private int id;

    private String valor;

    @ManyToOne
    @JoinColumn(name="MASCOTA_ID")
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name="CARACTERISTICA_ID")
    private Caracteristica caracteristica;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Mascota getMascota() {
        return this.mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Caracteristica getCaracteristica() {
        return this.caracteristica;
    }

    public void setCaracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
    }
}
 */