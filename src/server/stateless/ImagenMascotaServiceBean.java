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
import model.ImagenMascota;

import stateless.ImagenMascotaService;
import stateless.MascotaService;

@Stateless
public class ImagenMascotaServiceBean implements ImagenMascotaService {
    @PersistenceContext(unitName = "traxpet")
    protected EntityManager em;

    @EJB
    private MascotaService mascotaService;

    public EntityManager getEntityManager(){
        return em;
    }

    @Override
    public ImagenMascota create(int mascotaId, String directorioImagen, String formatoImagen) {
        Mascota mascota = mascotaService.findById(mascotaId);

        ImagenMascota imagenMascota = new ImagenMascota();
        imagenMascota.setMascota(mascota);
        imagenMascota.setDirectory(directorioImagen);
        imagenMascota.setFormat(formatoImagen);

        em.persist(imagenMascota);
        return imagenMascota;
    }
}