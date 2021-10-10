package stateless;

import java.util.Collection;
import java.util.List;
import model.Caracteristica;
import model.Especie;
import model.Valor;

public interface ValorService{
    public List<Valor> findByEspecie(Especie especie);
    public List<Valor> findByCaracteristica(Caracteristica caracteristica);
    public List<Valor> findByEspecieYCaracteristica(Especie especie, Caracteristica caracteristica);
    public String findCaracteristicasConValores(Especie especie);
    public Valor findByCaracteristicaEspecieValor(Caracteristica caracteristica, Especie especie, Valor valor);
    public List<Valor> findValoresByListAndEspecie(List<Valor> valores, Especie especie);
}
