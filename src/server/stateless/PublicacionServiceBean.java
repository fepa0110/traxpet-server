package stateless;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Estado;
import model.Mascota;
import model.Publicacion;
import model.TipoPublicacion;
import model.Ubicacion;
import model.Valor;
import model.Notificacion;

import stateless.MascotaService;
import stateless.PublicacionService;
import stateless.UbicacionService;
import stateless.ValorService;
import stateless.UsuarioService;
import stateless.NotificacionService;

@Stateless
public class PublicacionServiceBean implements PublicacionService {

  @PersistenceContext(unitName = "traxpet")
  protected EntityManager em;

  @EJB
  ValorService valorService;

  @EJB
  EspecieService especieService;

  @EJB
  MascotaService mascotaService;

  @EJB
  UbicacionService ubicacionService;

  @EJB
  UsuarioService usuarioService;

  @EJB
  NotificacionService notificacionService;

  public EntityManager getEntityManager() {
    return em;
  }

  @Override
  public Publicacion create(Publicacion publicacion, Ubicacion ubicacion) {
    publicacion.setId(this.getMaxId() + 1);
    // publicacion.setUsuario("Hardcodeado2");
    publicacion.setEstado(Estado.ACTIVA);

    publicacion.setMascota(mascotaService.create(publicacion.getMascota()));

    publicacion.setUsuario(this.usuarioService.findByUsername(publicacion.getUsuario()));

    // Busca el enum corresponiente al ingresado
    publicacion.setTipoPublicacion(
        TipoPublicacion.from(publicacion.getTipoPublicacion().toDbValue()));

    publicacion.setFechaPublicacion(Calendar.getInstance());

    ubicacion.setFecha(Calendar.getInstance());

    em.persist(publicacion);

    ubicacion.setPublicacion(publicacion);
    if (ubicacion.getLatitude() != 0 && ubicacion.getLongitude() != 0) {
      this.ubicacionService.create(ubicacion);
    }

    return publicacion;
  }

  @Override
  public List<Publicacion> findAll() {
    try {
      return getEntityManager()
          .createNamedQuery("Publicacion.findAll", Publicacion.class)
          .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public Collection<Publicacion> findAllPublicacionUsuario(String username) {
    try {
      return getEntityManager()
          .createNamedQuery("findAllPublicacionUsuario", Publicacion.class)
          .setParameter("username", username)
          .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  public long getMaxId() {
    try {
      return getEntityManager()
          .createNamedQuery("Publicacion.getMaxId", Long.class)
          .getSingleResult();
    } catch (NoResultException e) {
      return 0;
    }
  }

  @Override
  public Publicacion find(long id) {
    return getEntityManager().find(Publicacion.class, id);
  }

  @Override
  public Publicacion update(Publicacion publicacion, Ubicacion ubicacion) {
    Publicacion publicacionEdit = find(publicacion.getId());
    publicacionEdit
        .getMascota()
        .setNombre(publicacion.getMascota().getNombre());
    em.merge(publicacionEdit);

    ubicacion.setFecha(Calendar.getInstance());
    ubicacion.setPublicacion(publicacionEdit);
    // validar que no existe la ubicacion en la publicion

    if (ubicacionService.findByPublicacion(publicacionEdit.getId()) != null) {
      if (ubicacion.getLatitude() != 0 && ubicacion.getLongitude() != 0) {
        this.ubicacionService.update(ubicacion, publicacionEdit.getId());
      }
    } else if (ubicacion.getLatitude() != 0 && ubicacion.getLongitude() != 0) {
      this.ubicacionService.create(ubicacion);
    }

    return publicacionEdit;
  }

  @Override
  public Ubicacion addUbicacionMascota(Ubicacion ubicacion, int mascotaId) {
    Publicacion publicacionMascota = new Publicacion();

    publicacionMascota.setMascota(new Mascota());
    publicacionMascota.getMascota().setId(mascotaId);

    Notificacion notificacion = new Notificacion();
    
    ubicacion.setPublicacion(this.findByMascotaId(publicacionMascota));

    ubicacion.setFecha(Calendar.getInstance());

    ubicacion.setUsuario(usuarioService.findByUsername(ubicacion.getUsuario()));
    
    notificacion.setNotificante(ubicacion.getUsuario());
    notificacion.setPublicacion(publicacionMascota);
    notificacionService.create(notificacion);

    return this.ubicacionService.create(ubicacion);
  }

  @Override
  public Publicacion findByMascotaId(Publicacion publicacion) {
    try {
      return em
          .createQuery(
              "select publicacion from Publicacion publicacion " +
                  "where publicacion.mascota.id=:idMascota ",
              Publicacion.class)
          .setParameter("idMascota", publicacion.getMascota().getId())
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public Publicacion markAsFound(Publicacion publicacion) {
    publicacion.setEstado(Estado.ENCONTRADA);
    em.merge(publicacion);
    return publicacion;
  }

}
