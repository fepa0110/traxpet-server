package stateless;

import java.util.Collection;
import java.util.List;

import model.Publicacion;
import model.Usuario;
import model.Ubicacion;

public interface PublicacionService {
    public Publicacion create(Publicacion publicacion, Ubicacion ubicacion);

    public Publicacion update(Publicacion publicacion);

    public Publicacion migrarDueño(Publicacion publicacion, Usuario nuevoDueño);

    public List<Publicacion> findAll();

    public Collection<Publicacion> findAllPublicacionUsuario(String username);

    public long getMaxId();

    public Publicacion find(long id);

    public Publicacion findById(long id);

    public Ubicacion addUbicacionMascota(Ubicacion ubicacion, int mascotaId);

    public Publicacion findByMascotaId(Publicacion publicacion);

    public Publicacion markAsFound(Publicacion publicacion);
}