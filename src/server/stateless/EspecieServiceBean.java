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

  @EJB
  private PublicacionService publicacionService;

  public EntityManager getEntityManager() {
    return em;
  }

  @Override
  public Especie create(Especie especie) {
    especie.setDeshabilitado(false);
    em.persist(especie);
    return especie;
  }

  @Override
  public Especie findByName(String nombre) {
    return em
        .createQuery(
            "select especie from Especie especie " +
                "where especie.nombre = :nombre",
            Especie.class)
        .setParameter("nombre", nombre)
        .getSingleResult();
  }

  @Override
  public List<Especie> findAllEnable() {
    try {
      return getEntityManager()
          .createNamedQuery("Especie.findAllEnable", Especie.class)
          .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Especie> findAllUsable() {
    try {
      return getEntityManager()
          .createNamedQuery("Especie.findAllUsable", Especie.class)
          .getResultList();
    } catch (NoResultException e) {
      return null;
    }
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

  public Especie findByNombre(Especie especie) {
    try {
      return getEntityManager()
          .createNamedQuery("Especie.findByNombre", Especie.class)
          .setParameter("nombre_especie", especie.getNombre().toUpperCase())
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public void darBaja(Especie especie) {
    especie.setDeshabilitado(true);

    this.publicacionService.marcarInactivaByEspecie(especie.getNombre());

    em.merge(especie);
  }

  public long getMaxId() {
    try {
      return getEntityManager()
          .createNamedQuery("Especie.getMaxId", Long.class)
          .getSingleResult();
    } catch (NoResultException e) {
      return 0;
    }
  }

}
