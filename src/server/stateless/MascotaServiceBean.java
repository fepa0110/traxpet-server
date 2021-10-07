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

import model.Mascota;
import stateless.MascotaService;

@Stateless
public class MascotaServiceBean implements MascotaService {
    @PersistenceContext(unitName = "traxpet")
    protected EntityManager em;

    public EntityManager getEntityManager(){
        return em;
    }

/*     @Override
    public Mascota create(Mascota mascota) {
        //mascota.setUsername(mascota.getUsername());
        em.persist(mascota);
        return mascota;
    } */

    @Override
    public Mascota findById(int mascotaId){
        try {
            return getEntityManager()
                .createNamedQuery("Mascota.findById", Mascota.class)
                .setParameter("mascota_id", mascotaId)
                .getSingleResult();
        } 
        catch (NoResultException e) {
            return null;
        }
    }
}