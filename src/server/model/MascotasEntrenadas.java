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

/* SELECT me.MASCOTA_ID
FROM MODELO model JOIN MASCOTASENTRENADAS me ON (me.MODELO_ID = model.MODELO_ID)
WHERE model.ACTIVO=1 AND model.ESPECIE_ID = 1
 */
@NamedQueries({        
    @NamedQuery(name="MascotasEntrenadas.getByModeloActivo",
        query="SELECT mascota.id "+
                "FROM Publicacion publicacion JOIN publicacion.mascota mascota "+
                "WHERE UPPER(publicacion.estado) = 'ACTIVA' "+
                    "AND UPPER(mascota.especie.nombre) = :especie_nombre "+
                    "AND publicacion.usuario.id != :usuario_id"),             
})

@Entity
public class MascotasEntrenadas {
    @Id
    @Column(name="mascota_entrenada_id")
    private long id;

    @ManyToOne
    @JoinColumn(name="MASCOTA_ID")
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name="MODELO_ID")
    private Modelo modelo;

    private long orden;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Mascota getMascota() {
        return this.mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Modelo getModelo() {
        return this.modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public long getOrden() {
        return this.orden;
    }

    public void setOrden(long orden) {
        this.orden = orden;
    }

}
