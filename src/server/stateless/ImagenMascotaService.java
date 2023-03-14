package stateless;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;
import model.ImagenMascota;

public interface ImagenMascotaService {
    // public List<ImagenMascota> findAll();
    public ImagenMascota create(long mascotaId, InputStream uploadedInputStream, String formatoImagen);

    public ImagenMascota update(long mascotaId, InputStream uploadedInputStream, String formatoImagen, long imagenId);

    public Collection<ImagenMascota> findAllbyId(int id);

    public Collection<ImagenMascota> findById(int id);

    public void remove(int id);

    public Collection<ImagenMascota> findFirstForAll(int[] idList);

    public long getMaxId();

}