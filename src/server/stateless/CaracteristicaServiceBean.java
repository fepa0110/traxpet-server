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
import model.Caracteristica;
import model.Especie;
import stateless.CaracteristicaService;

@Stateless
public class CaracteristicaServiceBean implements CaracteristicaService {

  @PersistenceContext(unitName = "traxpet")
  protected EntityManager em;

  public EntityManager getEntityManager() {
    return em;
  }

  @Override
  public List<Caracteristica> findByEspecie(Especie especie) {
    try {
      return getEntityManager()
        .createNamedQuery("Caracteristica.findByEspecie", Caracteristica.class)
        .setParameter("especie_nombre", especie.getNombre())
        .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Caracteristica> findAll() {
    try {
      return em
        .createQuery(
          "select e from Caracteristica e order by e.id",
          Caracteristica.class
        )
        .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }
}
