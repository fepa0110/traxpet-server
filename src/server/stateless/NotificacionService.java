package stateless;

import java.util.Collection;
import java.util.List;
import model.Notificacion;
import model.Usuario;

public interface NotificacionService {
  public Notificacion create(Notificacion notificacion);
  public void marcarVista(Notificacion notificacion);
  public Collection<Notificacion> findByUsuario(Usuario usuario);
}
