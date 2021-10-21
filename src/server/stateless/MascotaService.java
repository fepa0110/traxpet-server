package stateless;

import java.util.Collection;
import java.util.List;
import model.ImagenMascota;
import model.Mascota;

public interface MascotaService {
  public Mascota findById(long mascotaId);

  public Mascota create(Mascota mascota);

  public long getMaxIdMascotas();

  public Mascota update(Mascota mascota);

  public Mascota find(long id);
}
