package stateless;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

import model.Usuario;
import stateless.UsuarioService;
import stateless.RolUsuarioService;
import stateless.LogroService;

@Stateless
public class UsuarioServiceBean implements UsuarioService {
    @PersistenceContext(unitName = "traxpet")
    protected EntityManager em;

    @EJB
    RolUsuarioService rolUsuarioService;

    @EJB
    LogroService logroService;


    public EntityManager getEntityManager(){
        return em;
    }

    @Override
    public Usuario create(Usuario usuario) {
        usuario.setId(this.getMaxId()+1);
        usuario.setRol(this.rolUsuarioService.findByNombre("Usuario"));
        usuario.setLogro(this.logroService.findById(0));
        em.persist(usuario);
        return usuario;
    }

    @Override
    public List<Usuario> findAll() {
        try {
            return getEntityManager()
                .createNamedQuery("Usuario.findAll", Usuario.class)
                .getResultList();
        } 
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Usuario update(Usuario usuario) {
        //Traigo el usuario
        Usuario usuarioBuscado = this.findByUsername(usuario);

        //Si el usuario no existe 
        if(usuarioBuscado == null) return null;

        //Actualizo el usuario con los datos
        if(usuario.getCorreoElectronico() != null && usuario.getCorreoElectronico() != usuarioBuscado.getCorreoElectronico() ) {
            usuarioBuscado.setCorreoElectronico(usuario.getCorreoElectronico());
        }

        if(usuario.getPassword() != null && usuario.getPassword() != usuarioBuscado.getPassword()){
            usuarioBuscado.setPassword(usuario.getPassword());
        } 

        //Actualizo el usuario en la base de datos
        em.merge(usuarioBuscado);
        return usuarioBuscado;
    }

    @Override
    public Usuario findByUsername(Usuario usuario){
        try {
            return getEntityManager()
                .createNamedQuery("Usuario.findByUsername", Usuario.class)
                .setParameter("username", usuario.getUsername())
                .getSingleResult();
        } 
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Usuario findByEmail(Usuario usuario){
        try {
            return getEntityManager()
                .createNamedQuery("Usuario.findByEmail", Usuario.class)
                .setParameter("email", usuario.getCorreoElectronico())
                .setParameter("username", usuario.getUsername())
                .getSingleResult();
        } 
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Usuario findEmailExists(Usuario usuario){
        try {
            return getEntityManager()
                .createNamedQuery("Usuario.findEmailExists", Usuario.class)
                .setParameter("email", usuario.getCorreoElectronico())
                .getSingleResult();
        } 
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Usuario findByLogin(Usuario usuario){
        try {
            return getEntityManager()
                .createNamedQuery("Usuario.findByLogin", Usuario.class)
                .setParameter("username", usuario.getUsername())
                .setParameter("password", usuario.getPassword())
                .getSingleResult();
        } 
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Usuario autenticarUsuario(Usuario usuario){
        Usuario usuarioPersistido = this.findByLogin(usuario);
        return usuarioPersistido;
    }

    @Override
    public Collection<Usuario> search(String name) {
        return em.createQuery("SELECT usuario from Usuario usuario "+
                                "WHERE UPPER(usuario.username) "+ 
                                "LIKE CONCAT('%',UPPER(:usuario_username),'%')", Usuario.class)
            .setParameter("usuario_username", name).getResultList();
    }

    public long getMaxId() {
        try {
        return getEntityManager()
            .createNamedQuery("Usuario.getMaxId", Long.class)
            .getSingleResult();
        } catch (NoResultException e) {
        return 0;
        }
    }



    @Override
    public Usuario updateScore(Usuario usuario,int puntaje) {
        //Traigo el usuario
        Usuario usuarioBuscado = this.findByUsername(usuario);

        //Si el usuario no existe 
        if(usuarioBuscado == null) return null;

        //Actualizo el usuario con los datos
        usuarioBuscado.setPuntaje(usuarioBuscado.getPuntaje()+puntaje);
        if((usuarioBuscado.getPuntaje()>usuarioBuscado.getLogro().getMaximo() )&& (usuarioBuscado.getLogro().getNivel()<4))

        
        {
            usuarioBuscado.setLogro(this.logroService.findById(usuarioBuscado.getLogro().getId()+1));
        }
        ;

        //Actualizo el usuario en la base de datos
        em.merge(usuarioBuscado);
        return usuarioBuscado;
    }
}