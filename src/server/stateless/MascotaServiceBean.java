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
import model.ImagenMascota;
import model.Mascota;
import stateless.MascotaService;

@Stateless
public class MascotaServiceBean implements MascotaService {

  @PersistenceContext(unitName = "traxpet")
  protected EntityManager em;

  @EJB
  ValorService valorService;

  @EJB
  EspecieService especieService;

  public EntityManager getEntityManager() {
    return em;
  }

  @Override
  public Mascota create(Mascota mascota) {
    Mascota mascotaNueva = new Mascota();
    mascotaNueva.setId(this.getMaxIdMascotas() + 1);
    mascotaNueva.setNombre(mascota.getNombre());

    //Busca y completa la especie de la mascota
    mascotaNueva.setEspecie(especieService.findByNombre(mascota.getEspecie()));

    mascotaNueva.setValores(
      valorService.findValoresByListAndEspecie(
        mascota.getValores(),
        mascotaNueva.getEspecie()
      )
    );

    em.persist(mascotaNueva);
    return mascotaNueva;
  }

  @Override  
  public Mascota findById(long mascotaId) {
    try {
      return getEntityManager()
        .createNamedQuery("Mascota.findById", Mascota.class)
        .setParameter("mascota_id", mascotaId)
        .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  public long getMaxIdMascotas() {
    try {
      return getEntityManager()
        .createNamedQuery("Mascota.getMaxIdMascotas", Long.class)
        .getSingleResult();
    } catch (NoResultException e) {
      return 0;
    }
  }

  @Override
  public Mascota update(Mascota mascota) {
   
    em.merge(mascota);
    return mascota;
  }

  @Override
  public Mascota find(long id) {
    return getEntityManager().find(Mascota.class, id);
  }
}
