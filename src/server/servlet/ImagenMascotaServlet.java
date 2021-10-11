package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import model.ImagenMascota;
import servlet.ResponseMessage;
import stateless.ImagenMascotaService;
import stateless.MascotaService;
import java.nio.charset.StandardCharsets;

@Path("/imagenesMascota")
public class ImagenMascotaServlet {

  private static final int MAX_SIZE_IN_MB = 1;

  private static final java.nio.file.Path BASE_DIR = Paths.get("/home/heavy/");

  public Logger logger = Logger.getLogger(getClass().getName());

  @EJB
  ImagenMascotaService imagenMascotaService;

  @EJB
  MascotaService mascotaService;

  private ObjectMapper mapper;

  public ImagenMascotaServlet() {
    mapper = new ObjectMapper();
    mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

    // Le provee el formateador de fechas.
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    mapper.setDateFormat(df);
  }

  @POST
  @Path("/upload")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public String uploadFile(
    InputStream uploadedInputStream,
    @QueryParam("mascotaId") int mascotaId,
    @QueryParam("formatoImagen") String formatoImagen
  ) {
    if (mascotaService.findById(mascotaId) == null) {
      return ResponseMessage.message(501, "No existe la mascota " + mascotaId);
    }

    if (mascotaId < 0 || formatoImagen == null || formatoImagen.isEmpty()) {
      return ResponseMessage.message(501, "Parametros incorrectos");
    }

    String fileLocation =
      "/root/app/etc/images/m" + mascotaId + "_" + System.currentTimeMillis();

    logger.info("Al menos entre!!");

    try {
      File file = new File(fileLocation);
      file.createNewFile();

      FileOutputStream out = new FileOutputStream(file);
      int read = 0;
      byte[] bytes = new byte[1024];

      out = new FileOutputStream(new File(fileLocation));

      while ((read = uploadedInputStream.read(bytes)) != -1) {
        out.write(bytes, 0, read);
      }
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String output = "File successfully uploaded to : " + fileLocation;
    imagenMascotaService.create(mascotaId, fileLocation, formatoImagen.trim());

    return ResponseMessage.message(200, "Se cargó la imagen exitosamente");
  }

  @GET
  @Path("/mascota/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllMascota(@PathParam("id") int id) throws IOException {
    // Se modifica este método para que utilice el servicio
    Collection<ImagenMascota> Imagenes = imagenMascotaService.findAllbyId(id);

     if (Imagenes == null){
            return ResponseMessage.message(500, " no hay imagenes");
        }
    // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
    String data;


    try {
     data = mapper.writeValueAsString(Imagenes);
    } catch (IOException e) {
      return ResponseMessage.message(
        501,
        "Formato incorrecto en datos de entrada",
        e.getMessage()
      );
    }

    return ResponseMessage.message(
      200,
      "imagenes obtenidas correctamente",
      data
    );}

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findImagesById(@PathParam("id") int id) throws IOException {


      if (imagenMascotaService.findById(id) == null) {
      return ResponseMessage.message(501, "No existe la imagen " + id);
    }
    // Se modifica este método para que utilice el servicio
    ImagenMascota imagen = imagenMascotaService.findById(id);
    byte[] bytes =Files.readAllBytes(Paths.get(imagen.getDirectory())), StandardCharsets.UTF_8);
    String data=new String(bytes);

    return ResponseMessage.message(
      200,
      "imagen obtenida correctamente",
      data
    );

  }





















  
  /* @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/upload/")
    public String create(InputStream inputStream, 
                        @HeaderParam("Content-Type") String fileType, 
                        @HeaderParam("Content-Length") long fileSize) throws IOException{
        
        String data;

         if(fileSize > 1024 * 1024 * MAX_SIZE_IN_MB) {
            return ResponseMessage.message(501, "La images es más grande que "+this.MAX_SIZE_IN_MB+" MB");
        } 
        logger.info("Al menos entre!!");

        String fileName = "" + System.currentTimeMillis();

        if (fileType.equals("image/jpeg")) {
            fileName += ".jpg";
        } else {
            fileName += ".png";
        } 

        // Copy the file to its location.
        Files.copy(inputStream, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        return ResponseMessage.message(200, "Se cargó la imagen exitosamente");
    } */
}
