package stateless;

import java.util.Collection;
import java.util.List;
import model.Especie;

public interface EspecieService {
  public List<Especie> findAllEnable();

  public List<Especie> findAllUsable();

  public List<Especie> findAll();

  public Especie findByNombre(Especie especie);

  public Especie create(Especie especie);

  public Especie findByName(String nombre);

  public void darBaja(Especie especie);
  
  public long getMaxId();
}
