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

    @NamedQuery(name="findAll",
        query="SELECT * "+
                "FROM logro i "
                 ),
    
             
            
    @NamedQuery(name="findById",
        query="SELECT i "+
                "FROM Logro i "+
                "WHERE i.id = :id"
                 )              
})

@Entity
public class Logro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    
    private int nivel;

    private int max;

    private String premio; 


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNivel() {
        return this.nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getPremio() {
        return this.premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }


 
}
