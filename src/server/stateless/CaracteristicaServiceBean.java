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
import stateless.CaracteristicaService;

@Stateless
public class CaracteristicaServiceBean implements CaracteristicaService {
    @PersistenceContext(unitName = "traxpet")
    protected EntityManager em;

    public EntityManager getEntityManager(){
        return em;
    }

    @Override
    public Caracteristica findByNombre(Caracteristica caracteristica) {
        try {
            return getEntityManager()
                .createNamedQuery("Caracteristica.findByNombre", Caracteristica.class)
                .setParameter("caracteristica_nombre", caracteristica.getNombre())
                .getSingleResult();
        } 
        catch (NoResultException e) {
            return null;
        }
    }
}