package stateless;

import java.util.Collection;
import java.util.List;

import model.ImagenMascota;

public interface ImagenMascotaService{
    // public List<ImagenMascota> findAll();
    public ImagenMascota create(int mascotaId, String directorioImagen, String formatoImagen);
    public Collection<ImagenMascota> findAllbyId(int id);
    public Collection <ImagenMascota> findById(int id) ;



}