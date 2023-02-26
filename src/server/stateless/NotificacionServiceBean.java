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
import model.Notificacion;
import model.Usuario;
import stateless.NotificacionService;
import stateless.PublicacionService;
import stateless.UsuarioService;

@Stateless
public class NotificacionServiceBean implements NotificacionService {

  @PersistenceContext(unitName = "traxpet")
  protected EntityManager em;

  @EJB
  UsuarioService usuarioService;

  @EJB
  PublicacionService publicacionService;

  public EntityManager getEntityManager() {
    return em;
  }

  @Override
  public Collection<Notificacion> findByUsuario(Usuario usuario) {
    try {
      return em
        .createQuery(
          "select notificacion from Notificacion notificacion " +
          "where notificacion.usuario.id=:idUsuario "+
          "and notificacion.vista=FALSE "+
          "order by notificacion.fechaNotificacion desc",
          Notificacion.class
        )
        .setParameter("idUsuario", usuario.getId())
        .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public Notificacion create(Notificacion notificacion) {
    notificacion.setId(this.getMaxId() + 1);
    
    // Busca el usuario notificante
    notificacion.setNotificante(usuarioService.findByUsername(notificacion.getNotificante()));
    notificacion.setPublicacion(publicacionService.findByMascotaId(notificacion.getPublicacion()));

    notificacion.setUsuario(notificacion.getPublicacion().getUsuario());
    notificacion.setVista(false);
    em.persist(notificacion);
    return notificacion;
  }

  @Override
  public void marcarVista(Notificacion notificacion) {
    em.merge(notificacion);
  }

  public long getMaxId() {
    try {
      return getEntityManager()
        .createNamedQuery("Notificacion.getMaxId", Long.class)
        .getSingleResult();
    } catch (NoResultException e) {
      return 0;
    }
  }
}
