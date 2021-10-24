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

import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries({
    @NamedQuery(name="Usuario.findAll",
        query="SELECT usuario "+ 
                "FROM Usuario usuario"),
    @NamedQuery(name="Usuario.findByUsername",
        query="SELECT usuario "+ 
            "FROM Usuario usuario "+
            "WHERE UPPER(usuario.username) LIKE UPPER(:username)"),
    @NamedQuery(name="Usuario.findByLogin",
        query="SELECT usuario "+ 
            "FROM Usuario usuario "+
            "WHERE UPPER(usuario.username) LIKE UPPER(:username) "+
            "AND usuario.password LIKE :password")
})

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="usuario_id")
    private int id;

    @Column(name="username",unique=true)
    private String username;

    @Column(name="correoElectronico",unique=true)
    private String correoElectronico;
    
    // @JsonIgnore
    private String password;

    @Temporal(TemporalType.DATE)
    private Calendar fechaNacimiento;

    @ManyToOne
    @JoinColumn(name="ROLUSUARIO_ID")
    private RolUsuario rol;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Calendar getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public RolUsuario getRol() {
        return this.rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }
}
