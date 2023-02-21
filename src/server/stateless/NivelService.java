package stateless;

import java.util.Collection;
import java.util.List;
import model.Nivel;
import model.Usuario;

public interface NivelService {
    public Collection<Nivel> findAll();
    public Nivel findById(long id);
    public Nivel calcularNivel(int puntaje);
    public List<Nivel> getNivelesObtenidos(int puntaje);
}