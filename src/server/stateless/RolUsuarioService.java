package stateless;

import java.util.Collection;
import java.util.List;

import model.RolUsuario;

public interface RolUsuarioService{
    public RolUsuario findByNombre(String nombre);
}