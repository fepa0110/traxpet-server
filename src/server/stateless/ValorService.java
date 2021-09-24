package stateless;

import java.util.Collection;
import java.util.List;

import model.Caracteristica;
import model.Especie;
import model.Valor;

public interface ValorService{
    public List<Valor> findByEspecie(Especie especie);
    public List<Valor> findByCaracteristica(Caracteristica caracteristica);
}