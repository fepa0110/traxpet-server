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

import model.Especie;
import stateless.MascotasEntrenadasService;

@Stateless
public class MascotasEntrenadasServiceBean implements MascotasEntrenadasService {

    @PersistenceContext(unitName = "traxpet")
    protected EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Collection<Long> predictByEspecieAndModeloActivo(String nombreEspecie){
        try {
            return getEntityManager()
                .createNamedQuery("MascotasEntrenadas.getByModeloActivo", Long.class)
                .setParameter("especie_nombre", nombreEspecie.toUpperCase())
                .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
