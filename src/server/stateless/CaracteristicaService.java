package stateless;

import java.util.Collection;
import java.util.List;
import model.Caracteristica;
import model.Especie;

public interface CaracteristicaService {
  
  public Caracteristica findByName(String nombre);

  public List<Caracteristica> findByEspecie(Especie especie);

  public List<Caracteristica> findAll();

  public Caracteristica create(Caracteristica caracteristica);
}