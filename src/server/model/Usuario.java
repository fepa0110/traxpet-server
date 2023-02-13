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
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;



import java.util.Collection;
import java.util.Set;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries({
    @NamedQuery(name="Usuario.findAll",
        query="SELECT usuario "+ 
                "FROM Usuario usuario"),
    @NamedQuery(name="Usuario.findByUsername",
        query="SELECT usuario "+ 
            "FROM Usuario usuario "+
            "WHERE UPPER(usuario.username) = UPPER(:username)"),
    @NamedQuery(name="Usuario.findEmailExists",
        query="SELECT usuario "+ 
            "FROM Usuario usuario "+
            "WHERE UPPER(usuario.correoElectronico) = UPPER(:email)"),
    @NamedQuery(name="Usuario.findByLogin",
        query="SELECT usuario "+ 
            "FROM Usuario usuario "+
            "WHERE UPPER(usuario.username) = UPPER(:username) "+
            "AND usuario.password = :password"),
    @NamedQuery(name="Usuario.getMaxId",
        query="SELECT MAX(usuario.id) "+ 
                "FROM Usuario usuario ")
})

@Entity
public class Usuario {

    @Id
    @Column(name="usuario_id")
    private long id;

    @Column(name="username",unique=true)
    private String username;

    @Column(name="correoElectronico",unique=true)
    private String correoElectronico;

    @OneToMany(mappedBy="usuario")
    private Collection<Notificacion> notificaciones;
    
    private String password;

    @ManyToOne
    @JoinColumn(name="ROLUSUARIO_ID")
    private RolUsuario rol;

    private int puntaje;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Collection<Notificacion> getNotificaciones() {
        return this.notificaciones;
    }

    public void setNotificaciones(Collection<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolUsuario getRol() {
        return this.rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public int getPuntaje() {
        return this.puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

}
