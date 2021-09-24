package stateless;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

import model.Caracteristica;
import model.Especie;
import model.Valor;

import stateless.CaracteristicaService;
import stateless.ValorService;

@Stateless
public class ValorServiceBean implements ValorService {
    @PersistenceContext(unitName = "traxpet")
    protected EntityManager em;

    public EntityManager getEntityManager(){
        return em;
    }

    @Override
    public List<Valor> findByEspecie(Especie especie){
        try {
            return getEntityManager()
                .createNamedQuery("Valor.findByEspecie", Valor.class)
                .setParameter("especie_nombre", especie.getNombre())
                .getResultList();
        } 
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Valor> findByCaracteristica(Caracteristica caracteristica){
        try {
            return getEntityManager()
                .createNamedQuery("Valor.findByCaracteristica", Valor.class)
                .setParameter("caracteristica_nombre", caracteristica.getNombre())
                .getResultList();
        } 
        catch (NoResultException e) {
            return null;
        }
    }
}