package stateless;

import java.util.Collection;
import java.util.List;
import model.Caracteristica;
import model.Especie;
import model.Valor;

public interface ValorService {
  public Valor create(Valor valor);

  public List<Valor> findByEspecie(Especie especie);

  public List<Valor> findByCaracteristica(Caracteristica caracteristica);

  public List<Valor> findByEspecieYCaracteristica(
    Especie especie,
    Caracteristica caracteristica
  );

  public String findCaracteristicasConValores(Especie especie);
}
