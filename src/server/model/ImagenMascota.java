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
import java.util.Calendar;

@NamedQueries({

        @NamedQuery(name = "findAllbyId", query = "SELECT i " +
                "FROM ImagenMascota i " +
                "WHERE i.mascota.id = :id"),
        @NamedQuery(name = "findById", query = "SELECT i " +
                "FROM ImagenMascota i " +
                "WHERE i.mascota.id = :id"),

        @NamedQuery(name = "findFirstForAll", query = "SELECT i " +
                "FROM ImagenMascota i, Publicacion p " +
                "WHERE (i.mascota.id=p.mascota.id) AND i.mascota.id IN :idList AND UPPER(p.estado)='ACTIVA'"),

        @NamedQuery(name = "Imagen.getMaxId", query = "SELECT MAX(imagen.id) " +
                "FROM ImagenMascota imagen ")
})

@Entity
public class ImagenMascota {

    @Id
    @Column(name = "imagen_mascota_id")
    private long id;

    private String directory;

    private String format;

    @ManyToOne
    @JoinColumn(name = "MASCOTA_ID")
    private Mascota mascota;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public Mascota getMascota() {
        return this.mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
