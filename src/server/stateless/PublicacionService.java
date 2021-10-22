package stateless;

import java.util.Collection;
import java.util.List;

import model.Publicacion;
import model.Ubicacion;

public interface PublicacionService{
    public Publicacion create(Publicacion publicacion, Ubicacion ubicacion);
    public Publicacion update(Publicacion publicacion, Ubicacion ubicacion);

    public List<Publicacion> findAll();
    public Collection <Publicacion> findAllPublicacionUsuario(String id);
    public long getMaxId();
    public Publicacion find(long id);

}