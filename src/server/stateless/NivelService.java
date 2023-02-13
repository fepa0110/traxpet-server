package stateless;

import java.util.Collection;
import model.Nivel;

public interface NivelService {
    public Collection<Nivel> findAll();
    public Nivel findById(long id);
    public Nivel calcularNivel(int puntaje);
}