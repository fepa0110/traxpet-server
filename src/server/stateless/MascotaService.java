package stateless;

import java.util.Collection;
import java.util.List;

import model.Mascota;

public interface MascotaService{
    public Mascota findById(int mascotaId);
    // public Mascota create();
}