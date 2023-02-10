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
    @NamedQuery(name="Logro.findAll",
        query="SELECT logro FROM Logro logro"
                ),
    
    @NamedQuery(name="Logro.findById",
        query="SELECT logro "+
                "FROM Logro logro "+
                "WHERE logro.id = :id"
                )              
})

@Entity
public class Logro {

    @Id
    @Column(name="id")
    private long id;


    public Logro() {
    }
    
    private int nivel;

    private int maximo;

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

    public int getMaximo() {
        return this.maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public String getPremio() {
        return this.premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

}
