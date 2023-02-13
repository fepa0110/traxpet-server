package model;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@NamedQueries({
    @NamedQuery(name="Nivel.findAll",
        query="SELECT nivel FROM Nivel nivel"
                ),
    
    @NamedQuery(name="Nivel.findById",
        query="SELECT nivel "+
                "FROM Nivel nivel "+
                "WHERE nivel.id = :id"
                ),             
    @NamedQuery(name="Nivel.getNivelByPuntaje",
        query="SELECT nivel "+
                "FROM Nivel nivel "+
                "WHERE :puntaje >= nivel.puntajeMinimo "+
                "AND :puntaje <= nivel.puntajeMaximo"
                ),             
})

@Entity
public class Nivel {
    @Id
    @Column(name="id")
    private long id;

    public Nivel() { }

    private int nivel;

    private int puntajeMinimo;

    private int puntajeMaximo;

    private String premio; 

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNivel() {
        return this.nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getPremio() {
        return this.premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public int getPuntajeMinimo() {
        return this.puntajeMinimo;
    }

    public void setPuntajeMinimo(int puntajeMinimo) {
        this.puntajeMinimo = puntajeMinimo;
    }

    public int getPuntajeMaximo() {
        return this.puntajeMaximo;
    }

    public void setPuntajeMaximo(int puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
    }
}
