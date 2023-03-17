Upackage servlet;

import model.Publicacion;
import model.Ubicacion;

public class PublicacionNueva{
    Publicacion publicacion;
    Ubicacion ubicacion;
    Boolean notificateSimilar;
    int idMascotaSimilar;

    public Publicacion getPublicacion() {
        return this.publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Ubicacion getUbicacion() {
        return this.ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }


    public Boolean isNotificateSimilar() {
        return this.notificateSimilar;
    }

    public Boolean getNotificateSimilar() {
        return this.notificateSimilar;
    }

    public void setNotificateSimilar(Boolean notificateSimilar) {
        this.notificateSimilar = notificateSimilar;
    }

    public int getIdMascotaSimilar() {
        return this.idMascotaSimilar;
    }

    public void setIdMascotaSimilar(int idMascotaSimilar) {
        this.idMascotaSimilar = idMascotaSimilar;
    }

}
