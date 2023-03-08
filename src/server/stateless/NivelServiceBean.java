package stateless;

import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;

import model.Nivel;

@Stateless
public class NivelServiceBean implements NivelService {
  @PersistenceContext(unitName = "traxpet")
  protected EntityManager em;

    public EntityManager getEntityManager() {
        return em;
      }
    
    @Override
    public Collection<Nivel> findAll() {
        try {
            return getEntityManager()
                .createNamedQuery("Nivel.findAll", Nivel.class)
                .getResultList();
          } catch (NoResultException e) {
            return null;
          }
    }

    @Override
    public Nivel findById(long id){
      try {
        return getEntityManager()
          .createNamedQuery("Nivel.findById", Nivel.class)
          .setParameter("id", id)
          .getSingleResult();
      } catch (NoResultException e) {
        return null;
      }
    }

    @Override
    public Nivel calcularNivel(int puntaje){
      try {
        return getEntityManager()
          .createNamedQuery("Nivel.getNivelByPuntaje", Nivel.class)
          .setParameter("puntaje", puntaje)
          .getSingleResult();
      } catch (NoResultException e) {
        return null;
      }
    }
    
    @Override
    public Nivel getMaxNivel(){
      try {
        return getEntityManager()
          .createNamedQuery("Nivel.getMaxNivel", Nivel.class)
          .getSingleResult();
      } catch (NoResultException e) {
        return null;
      }
    }

    @Override
    public List<Nivel> getNivelesObtenidos(int puntaje){
      try {
        return getEntityManager()
          .createNamedQuery("Nivel.getNivelesObtenidosByPuntaje", Nivel.class)
          .setParameter("puntaje", puntaje)
          .getResultList();
      } catch (NoResultException e) {
        return null;
      }
    }

}
