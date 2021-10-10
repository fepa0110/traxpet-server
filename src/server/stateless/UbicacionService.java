package stateless;

import java.util.Collection;
import java.util.List;
import model.Ubicacion;

public interface UbicacionService{
    public Ubicacion create(Ubicacion ubicacion);
    public long getMaxId();
}
