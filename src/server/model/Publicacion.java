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
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;

import java.util.Set;
import java.util.Calendar;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = "Publicacion.findAll", query = "SELECT publicacion " +
                "FROM Publicacion publicacion"),

        @NamedQuery(name = "findAllPublicacionUsuario", query = "SELECT p " +
                "FROM Publicacion p " +
                "WHERE p.usuario.username =:username " +
                "ORDER BY p.fechaPublicacion DESC"),
        @NamedQuery(name = "Publicacion.getMaxId", query = "SELECT MAX(publicacion.id) " +
                "FROM Publicacion publicacion ")
})

@Entity
public class Publicacion {
    @Id
    @Column(name = "publicacion_id")
    private long id;

    @Convert(converter = EstadoConverter.class)
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Convert(converter = TipoPublicacionConverter.class)
    @Enumerated(EnumType.STRING)
    private TipoPublicacion tipoPublicacion;

    @Temporal(TemporalType.DATE)
    private Calendar fechaPublicacion;

    @Temporal(TemporalType.DATE)
    private Calendar fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MASCOTA_ID")
    private Mascota mascota;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
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

    public Calendar getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Calendar fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Mascota getMascota() {
        return this.mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Publicacion)) {
            return false;
        }
        Publicacion publicacion = (Publicacion) o;
        return id == publicacion.id && Objects.equals(estado, publicacion.estado)
                && Objects.equals(tipoPublicacion, publicacion.tipoPublicacion)
                && Objects.equals(fechaPublicacion, publicacion.fechaPublicacion)
                && Objects.equals(usuario, publicacion.usuario) && Objects.equals(mascota, publicacion.mascota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, estado, tipoPublicacion, fechaPublicacion, usuario, mascota);
    }
}
