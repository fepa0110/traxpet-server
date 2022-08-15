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

@Stateless
public class NotificacionServiceBean implements NotificacionService {

  @PersistenceContext(unitName = "traxpet")
  protected EntityManager em;

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
