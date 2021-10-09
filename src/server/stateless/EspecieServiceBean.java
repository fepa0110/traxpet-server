package stateless;

import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Especie;
import stateless.EspecieService;

@Stateless
public class EspecieServiceBean implements EspecieService {

  @PersistenceContext(unitName = "traxpet")
  protected EntityManager em;

  public EntityManager getEntityManager() {
    return em;
  }

  @Override
  public Especie create(Especie especie) {
    em.persist(especie);
    return especie;
  }

  @Override
  public Especie findByName(String nombre) {
    return em
      .createQuery(
        "select especie from Especie especie " +
        "where especie.nombre = :nombre",
        Especie.class
      )
      .setParameter("nombre", nombre)
      .getSingleResult();
  }

  @Override
  public List<Especie> findAll() {
    try {
      return getEntityManager()
        .createNamedQuery("Especie.findAll", Especie.class)
        .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }
}
