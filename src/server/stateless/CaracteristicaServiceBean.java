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

import stateless.CaracteristicaService;

@Stateless
public class CaracteristicaServiceBean implements CaracteristicaService {
    @PersistenceContext(unitName = "traxpet")
    protected EntityManager em;

    public EntityManager getEntityManager(){
        return em;
    }

    @Override
    public List<Caracteristica> findByEspecie(Especie especie){
        try {
            return getEntityManager()
                .createNamedQuery("Caracteristica.findByEspecie", Caracteristica.class)
                .setParameter("especie_nombre", especie.getNombre())
                .getResultList();
        } 
        catch (NoResultException e) {
            return null;
        }
    }
}