package stateless;

import java.util.Collection;
import java.util.List;
import model.Especie;

public interface EspecieService{
    public List<Especie> findAll();
    public Especie findByNombre(Especie especie);
    public Especie create(Especie especie);
}
