package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
  public String uploadFile(InputStream uploadedInputStream,@QueryParam("mascotaId") int mascotaId,
  @QueryParam("formatoImagen") String formatoImagen
  ) 
  {
    if (mascotaService.findById(mascotaId) == null) {
      return ResponseMessage.message(501, "No existe la mascota " + mascotaId);
    }

    if (mascotaId < 0 || formatoImagen == null || formatoImagen.isEmpty()) {
      return ResponseMessage.message(501, "Parametros incorrectos");
    }

  // String output =;
   String data;

    try {
      data = mapper.writeValueAsString(imagenMascotaService.create(mascotaId,uploadedInputStream, formatoImagen.trim()));
    } catch (IOException e) {
      return ResponseMessage.message(
        501,
        "Formato incorrecto en datos de entrada",
        e.getMessage()
      );
    }

    return ResponseMessage.message(200, "Se cargó la imagen exitosamente");
  }

  @GET
  @Path("/mascota/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllMascota(@PathParam("id") int id) throws IOException {
    // Se modifica este método para que utilice el servicio
    Collection<ImagenMascota> Imagenes = imagenMascotaService.findAllbyId(id);

    if (Imagenes == null) {
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
    );
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findImagesById(@PathParam("id") int id) throws IOException {
    Collection<ImagenMascota> imagenes = imagenMascotaService.findById(id);
    Stream<String> lines;
    String data = "[ ";
    if (imagenes.isEmpty() || imagenes == null) {
      return ResponseMessage.message(501, "No existe la imagen " + id);
    }

    for (ImagenMascota imagen : imagenes) {
      data += "{\"id\": " + imagen.getId() + ",";
      lines = Files.lines(Paths.get(imagen.getDirectory()));
      data += "\"ImagenData\":";
      data +=
        "\"" +
        lines.collect(Collectors.joining(System.lineSeparator())) +
        "\"},";
    }
    data = data.substring(0, data.length() - 1);
    data += "]";

    return ResponseMessage.message(200, "imagen obtenida correctamente", data);
  }

  @DELETE
  @Path("delete/{id}")
  public String remove(@PathParam("id") int id) {
    imagenMascotaService.remove(id);
    return ResponseMessage.message(200, "Se eliminó correctamente la imagen correctamente");
  }


  @PUT
  @Path("/update")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public String update(
  InputStream uploadedInputStream,
  @QueryParam("mascotaId") int mascotaId,
  @QueryParam("formatoImagen") String formatoImagen,
  @QueryParam("imagenId") int imagenId
  ) 
  {
    if (mascotaService.findById(mascotaId) == null) {
      return ResponseMessage.message(501, "No existe la mascota " + mascotaId);
    }

    if (mascotaId < 0 || formatoImagen == null || formatoImagen.isEmpty()) {
      return ResponseMessage.message(501, "Parametros incorrectos");
    }


  // String output =;
   String data;

    try {

    if(imagenId==0){
    data = mapper.writeValueAsString(imagenMascotaService.create(mascotaId,uploadedInputStream, formatoImagen.trim()));
    }else{
     data =mapper.writeValueAsString(imagenMascotaService.update(mascotaId,uploadedInputStream,formatoImagen.trim(),imagenId));
    } 


    } catch (IOException e) {
      return ResponseMessage.message(
        501,
        "Formato incorrecto en datos de entrada",
        e.getMessage()
      );
    }

    return ResponseMessage.message(200, "Se cargó la imagen exitosamente");
  }

  
}
