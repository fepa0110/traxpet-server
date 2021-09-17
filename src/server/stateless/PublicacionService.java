package stateless;

import java.util.Collection;
import java.util.List;

import model.Publicacion;

public interface PublicacionService{
    public Publicacion create(Publicacion publicacion);
    public List<Publicacion> findAll();
}