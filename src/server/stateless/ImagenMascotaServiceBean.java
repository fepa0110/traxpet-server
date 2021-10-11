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
import stateless.ImagenMascotaService;
import stateless.MascotaService;

@Stateless
public class ImagenMascotaServiceBean implements ImagenMascotaService {

  @PersistenceContext(unitName = "traxpet")
  protected EntityManager em;

  @EJB
  private MascotaService mascotaService;

  public EntityManager getEntityManager() {
    return em;
  }

  @Override
  public ImagenMascota create(
    int mascotaId,
    String directorioImagen,
    String formatoImagen
  ) {
    Mascota mascota = mascotaService.findById(mascotaId);

    ImagenMascota imagenMascota = new ImagenMascota();
    imagenMascota.setMascota(mascota);
    imagenMascota.setDirectory(directorioImagen);
    imagenMascota.setFormat(formatoImagen);

    em.persist(imagenMascota);
    return imagenMascota;
  }

  @Override
  public Collection<ImagenMascota> findAllbyId(int id) {
    try {
      return getEntityManager()
        .createNamedQuery("findAllbyId", ImagenMascota.class)
        .setParameter("id", id)
        .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public ImagenMascota findById(int id) {
       try {
      return getEntityManager()
        .createNamedQuery("findById", ImagenMascota.class)
        .setParameter("id", id)
        .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }

  }
}
