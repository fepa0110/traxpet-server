package stateless;

import java.util.Collection;
import java.util.List;

import model.Usuario;

public interface UsuarioService{
    public Usuario create(Usuario usuario);
    public List<Usuario> findAll();
    public Usuario findByUsername(Usuario usuario);
    public Usuario findByEmail(Usuario usuario);
    public Usuario findEmailExists(Usuario usuario);
    public Usuario findByDni(Usuario usuario);
    public Usuario findByLogin(Usuario usuario);
    public Usuario autenticarUsuario(Usuario usuario);
    public Usuario update(Usuario usuario);
    public Collection<Usuario> search(String name);
}