package stateless;

import java.util.Collection;
import java.util.List;
import model.MascotasEntrenadas;

public interface MascotasEntrenadasService {
    public Collection<Long> predictByEspecieAndModeloActivo(String nombreEspecie);
}