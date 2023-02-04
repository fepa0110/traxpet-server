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

import model.Logro;
@Stateless
public class LogroServiceBean implements LogroService {
  @PersistenceContext(unitName = "traxpet")
  protected EntityManager em;

    public EntityManager getEntityManager() {
        return em;
      }
    
    @Override
    public Collection<Logro> findAll() {
        try {
            return getEntityManager()
                .createNamedQuery("Logro.findAll", Logro.class)
                .getResultList();
          } catch (NoResultException e) {
            return null;
          }
    }
    
}
