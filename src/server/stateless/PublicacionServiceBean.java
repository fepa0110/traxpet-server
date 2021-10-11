package stateless;

import java.util.Collection;
import java.util.List;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

import model.Publicacion;
import model.Ubicacion;
import model.Mascota;
import model.Valor;
import model.TipoPublicacion;
import model.Estado;

import stateless.PublicacionService;
import stateless.ValorService;
import stateless.MascotaService;
import stateless.UbicacionService;

@Stateless
public class PublicacionServiceBean implements PublicacionService {
    @PersistenceContext(unitName = "traxpet")
    protected EntityManager em;

    @EJB
    ValorService valorService;

    @EJB
    EspecieService especieService;

    @EJB
    MascotaService mascotaService;

    @EJB
    UbicacionService ubicacionService;

    public EntityManager getEntityManager(){
        return em;
    }

    @Override
    public Publicacion create(Publicacion publicacion, Ubicacion ubicacion) {
        publicacion.setId(this.getMaxId()+1);
        // publicacion.setUsuario("Hardcodeado2");
        publicacion.setEstado(Estado.ACTIVA);

        publicacion.setMascota(mascotaService.create(publicacion.getMascota()));

        //Busca el enum corresponiente al ingresado
        publicacion.setTipoPublicacion(TipoPublicacion.from(publicacion.getTipoPublicacion().toDbValue()));

        publicacion.setFechaPublicacion(Calendar.getInstance());

        ubicacion.setFecha(Calendar.getInstance());

        em.persist(publicacion);

        ubicacion.setPublicacion(publicacion);
        if(ubicacion.getLatitude() != 0 && ubicacion.getLongitude() != 0){
            this.ubicacionService.create(ubicacion);
        }

        return publicacion;
    }

    @Override
    public List<Publicacion> findAll() {
        try {
            return getEntityManager()
                .createNamedQuery("Publicacion.findAll", Publicacion.class)
                .getResultList();
        } 
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Collection<Publicacion> findAllPublicacionUsuario(String id) {
        try {
            return getEntityManager()
                .createNamedQuery("findAllPublicacionUsuario", Publicacion.class)
                .setParameter("id",id)
                .getResultList();
        } 
        catch (NoResultException e) {
            return null;
        }
    }

    public long getMaxId(){
        try {
            return getEntityManager()
                .createNamedQuery("Publicacion.getMaxId", Long.class)
                .getSingleResult();
        } 
        catch (NoResultException e) {
            return 0;
        }
    }
}