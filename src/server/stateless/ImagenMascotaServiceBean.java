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
import java.io.InputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

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
  public ImagenMascota create(long mascotaId, InputStream uploadedInputStream, String formatoImagen) {

    String fileLocation = "/root/app/etc/images/m" + mascotaId + "_" + System.currentTimeMillis();

    try {
      File file = new File(fileLocation);
      file.createNewFile();

      FileOutputStream out = new FileOutputStream(file);
      int read = 0;
      byte[] bytes = new byte[1024];

      out = new FileOutputStream(new File(fileLocation));

      while ((read = uploadedInputStream.read(bytes)) != -1) {
        out.write(bytes, 0, read);
      }
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Mascota mascota = mascotaService.findById(mascotaId);
   
    ImagenMascota imagenMascota = new ImagenMascota();
    imagenMascota.setId(this.getMaxId() + 1);
    imagenMascota.setMascota(mascota);
    imagenMascota.setDirectory(fileLocation);
    imagenMascota.setFormat(formatoImagen);

    em.persist(imagenMascota);
    return imagenMascota;
  }

  @Override
  public ImagenMascota update(long mascotaId, InputStream uploadedInputStream, String formatoImagen, long imagenId) {
    this.remove(imagenId);
    ImagenMascota imagenMascota = this.create(mascotaId, uploadedInputStream, formatoImagen);
    return imagenMascota;
  }

  @Override
  public Collection<ImagenMascota> findAllbyId(long id) {
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
  public Collection<ImagenMascota> findFirstForAll(int[] idList) {
    try {
      return getEntityManager()
          .createNamedQuery("findFirstForAll", ImagenMascota.class)
          .setParameter("idList", idList)
          .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public Collection<ImagenMascota> findById(long id) {
    try {
      return getEntityManager()
          .createNamedQuery("findById", ImagenMascota.class)
          .setParameter("id", id)
          .getResultList();
    } catch (NoResultException e) {
      return null;
    }

  }

  public ImagenMascota find(long id) {
    return getEntityManager().find(ImagenMascota.class, id);
  }

  @Override
  public void remove(long id) {
    ImagenMascota imagen = find(id);
    // Files.delete(Paths.get(imagen.getDirectory());
    try {
      Files.delete(Paths.get(imagen.getDirectory()));
    } catch (IOException e) {
      return;
    }
    // Files.deleteIfExists(Paths.get(imagen.getDirectory()));
    em.remove(imagen);
  }

  public long getMaxId() {
    try {
      return getEntityManager()
          .createNamedQuery("Imagen.getMaxId", Long.class)
          .getSingleResult();
    } catch (NoResultException e) {
      return 0;
    }
  }
}
