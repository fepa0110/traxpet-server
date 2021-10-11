package stateless;

import java.util.Collection;
import java.util.List;
import model.ImagenMascota;
import model.Mascota;

public interface MascotaService{
    public Mascota findById(int mascotaId);
    public Mascota create(Mascota mascota);
    public long getMaxIdMascotas();
}