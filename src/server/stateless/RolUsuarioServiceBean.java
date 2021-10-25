package stateless;

import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.RolUsuario;
import model.Caracteristica;
import model.Especie;

import stateless.RolUsuarioService;

@Stateless
public class RolUsuarioServiceBean implements RolUsuarioService {

    @PersistenceContext(unitName = "traxpet")
    protected EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public RolUsuario findByNombre(String nombre) {
        return em
        .createQuery(
            "SELECT rol "+ 
            "FROM RolUsuario rol " +
            "WHERE UPPER(rol.nombre) = UPPER(:nombre)",
            RolUsuario.class
        )
        .setParameter("nombre", nombre)
        .getSingleResult();
    }

}