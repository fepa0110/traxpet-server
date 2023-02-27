package servlet;

import model.Publicacion;
import model.Ubicacion;

public class PublicacionNueva{
    Publicacion publicacion;
    Ubicacion ubicacion;

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

}
