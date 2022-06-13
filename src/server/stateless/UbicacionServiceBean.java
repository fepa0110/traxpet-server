package stateless;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Caracteristica;
import model.Especie;
import model.Ubicacion;

import servlet.ResponseMessage;

import stateless.CaracteristicaService;
import stateless.UbicacionService;

@Stateless
public class UbicacionServiceBean implements UbicacionService {

    @PersistenceContext(unitName = "traxpet")
    protected EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Ubicacion create(Ubicacion ubicacion) {
        ubicacion.setId(this.getMaxId()+1);

        em.persist(ubicacion);
        return ubicacion;
    }

    public long getMaxId(){
        try {
            return getEntityManager()
                .createNamedQuery("Ubicacion.getMaxId", Long.class)
                .getSingleResult();
        } 
        catch (NoResultException e) {
            return 0;
        }
    }

    @Override
    public Collection<Ubicacion> findByPublicacion(long id) {
        try {
            return getEntityManager()
            .createNamedQuery("Ubicacion.findByPublicacion", Ubicacion.class)
            .setParameter("id", id)
            .getResultList();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Ubicacion update(Ubicacion ubicacion,long id ) {
        /* Ubicacion ubicacionEdit = this.findByPublicacion(id); 
        ubicacionEdit.setLatitude(ubicacion.getLatitude());
        ubicacionEdit.setLongitude(ubicacion.getLongitude());
        em.merge(ubicacionEdit);*/
        return new Ubicacion(); 
    }


}
