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
import java.util.Calendar;

@NamedQueries({
    @NamedQuery(name="Publicacion.findAll",
        query="SELECT publicacion "+ 
                "FROM Publicacion publicacion")
})

@Entity
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="publicacion_id")
    private int id;

    @Convert(converter = EstadoConverter.class)
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Convert(converter = TipoPublicacionConverter.class)
    @Enumerated(EnumType.STRING)
    private TipoPublicacion tipoPublicacion;

    @Temporal(TemporalType.DATE)
    private Calendar fechaPublicacion;

    private String usuario;
      
    @OneToMany(optional = true)
    private Ubicacion ubicacion;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="MASCOTA_ID")
    private Mascota mascota;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public TipoPublicacion getTipoPublicacion() {
        return this.tipoPublicacion;
    }

    public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
        this.tipoPublicacion = tipoPublicacion;
    }

    public Calendar getFechaPublicacion() {
        return this.fechaPublicacion;
    }

    public void setFechaPublicacion(Calendar fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Mascota getMascota() {
        return this.mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
}
