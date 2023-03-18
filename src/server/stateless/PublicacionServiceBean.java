package stateless;

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
import model.Usuario;

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
    publicacion.setEstado(Estado.ACTIVA);

    publicacion.setMascota(mascotaService.create(publicacion.getMascota()));

    publicacion.setUsuario(this.usuarioService.findByUsername(publicacion.getUsuario()));

    // Busca el enum corresponiente al ingresado
    publicacion.setTipoPublicacion(
        TipoPublicacion.from(publicacion.getTipoPublicacion().toDbValue()));

    publicacion.setFechaPublicacion(Calendar.getInstance());

    publicacion.setFechaModificacion(Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00")));

    ubicacion.setUsuario(publicacion.getUsuario());
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

  @Override
  public Collection<Publicacion> findAllPublicacionVistasUsuario(String username) {
    try {
      return getEntityManager()
          .createNamedQuery("findAllPublicacionVistasUsuario", Publicacion.class)
          .setParameter("username", username)
          .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public Collection<Publicacion> findAllPublicacionBuscadasUsuario(String username) {
    try {
      return getEntityManager()
          .createNamedQuery("findAllPublicacionBuscadasUsuario", Publicacion.class)
          .setParameter("username", username)
          .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public Publicacion findById(long id) {
    try {
      return em
          .createQuery(
              "select publicacion from Publicacion publicacion " +
                  "where publicacion.id=:id ",
              Publicacion.class)
          .setParameter("id", id)
          .getSingleResult();
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
  public Publicacion update(Publicacion publicacion) {
    Publicacion publicacionEdit = find(publicacion.getId());
    publicacionEdit
        .getMascota()
        .setNombre(publicacion.getMascota().getNombre());

    publicacionEdit.setFechaModificacion(Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00")));

    em.merge(publicacionEdit);

    return publicacionEdit;
  }

  @Override
  public Ubicacion addUbicacionMascota(Ubicacion ubicacion, int mascotaId) {
    Publicacion publicacionMascota = new Publicacion();

    publicacionMascota.setMascota(new Mascota());
    publicacionMascota.getMascota().setId(mascotaId);

    Notificacion notificacion = new Notificacion();

    publicacionMascota = this.findByMascotaId(publicacionMascota);

    publicacionMascota.setFechaModificacion(Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00")));
    publicacionMascota = em.merge(publicacionMascota);

    ubicacion.setPublicacion(publicacionMascota);

    ubicacion.setFecha(Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00")));

    ubicacion.setUsuario(usuarioService.findByUsername(ubicacion.getUsuario()));

    if (!ubicacion.getUsuario().equals(publicacionMascota.getUsuario())) {
      notificacion.setNotificante(ubicacion.getUsuario());
      notificacion.setPublicacion(publicacionMascota);
      notificacion.setFechaNotificacion(Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00")));
      notificacion.setUsuario(publicacionMascota.getUsuario());
      notificacionService.create(notificacion);
    }

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

  @Override
  public Publicacion migrarDueño(Publicacion publicacion, Usuario nuevoDueño) {
    publicacion = find(publicacion.getId());

    nuevoDueño = usuarioService.findByUsername(nuevoDueño);
    publicacion.setUsuario(nuevoDueño);
    publicacion.setTipoPublicacion(TipoPublicacion.MASCOTA_BUSCADA);
    publicacion.setFechaModificacion(Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00")));

    em.merge(publicacion);
    return publicacion;
  }

  public Collection<Publicacion> findActivasBySpecie(String nombreEspecie) {
    try {
      return getEntityManager()
          .createNamedQuery("findActivasBySpecie", Publicacion.class)
          .setParameter("nombre_especie", nombreEspecie)
          .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  public Collection<Publicacion> marcarInactivaByEspecie(String nombreEspecie){
    Collection<Publicacion> publicacionesParaDesactivar = this.findActivasBySpecie(nombreEspecie);

    Collection<Publicacion> publicacionesDesactivadas = new ArrayList<>();
    
    for(Publicacion publicacion : publicacionesParaDesactivar){
      publicacion.setEstado(Estado.INACTIVA);
      em.merge(publicacion);
      publicacionesDesactivadas.add(publicacion);
    }

    return publicacionesDesactivadas;
  }




  @Override
  public long findCountByEspecie(String nombre){
    try {
      return getEntityManager()
          .createNamedQuery("getCantidadByEspecie", Long.class)
          .setParameter("nombre", nombre)
          .getSingleResult();
    } catch (NoResultException e) {
      return 0;
    }
  }
}
