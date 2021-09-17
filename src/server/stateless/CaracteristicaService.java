package stateless;

import java.util.Collection;
import java.util.List;

import model.Caracteristica;

public interface CaracteristicaService{
    public Caracteristica findByNombre(Caracteristica caracteristica);
}