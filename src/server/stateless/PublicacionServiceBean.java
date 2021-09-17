/* package stateless;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

import model.Publicacion;
import stateless.PublicacionService;

@Stateless
public class PublicacionServiceBean implements PublicacionService {
    @PersistenceContext(unitName = "traxpet")
    protected EntityManager em;

    public EntityManager getEntityManager(){
        return em;
    }

    @Override
    public Publicacion create(Publicacion publicacion) {
        publicacion.setUsuario("Hardcodeado2");
        em.persist(publicacion);
        return publicacion;
    }

    @Override
    public List<Publicacion> findAll() {
        try {
            return getEntityManager()
                .createNamedQuery("Publicacion.findAll", Publicacion.class)
                .getResultList();
        } 
        catch (NoResultException e) {
            return null;
        }
    }

} */