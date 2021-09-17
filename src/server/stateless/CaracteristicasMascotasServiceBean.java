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

import model.CaracteristicasMascotas;
import stateless.CaracteristicasMascotasService;

@Stateless
public class CaracteristicasMascotasServiceBean implements CaracteristicasMascotasService {
    @PersistenceContext(unitName = "traxpet")
    protected EntityManager em;

    public EntityManager getEntityManager(){
        return em;
    }

    @Override
    public CaracteristicasMascotas create(CaracteristicasMascotas caracteristicasMascotas) {
        em.persist(caracteristicasMascotas);
        return caracteristicasMascotas;
    }
}