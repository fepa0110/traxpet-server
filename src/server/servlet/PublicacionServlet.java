package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import model.Mascota;
import model.Publicacion;
import model.Ubicacion;
import servlet.ResponseMessage;
import stateless.PublicacionService;

@Path("/publicaciones")
public class PublicacionServlet {

  @EJB
  PublicacionService publicacionService;

  Logger logger = Logger.getLogger(getClass().getName());
  private ObjectMapper mapper;

  public PublicacionServlet() {
    mapper = new ObjectMapper();
    mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
    // Le provee el formateador de fechas.
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    mapper.setDateFormat(df);
  }

  @GET
  @Path("/usuario/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllPublicacionUsuario(@PathParam("id") String id) throws IOException {
    Collection<Publicacion> publicaciones = publicacionService.findAllPublicacionUsuario(id);
   //Collection<PublicacionPojo> publicaciones = publicacionService.findPublicacionesUbicacion(id);
   
    String data;

    if (publicaciones == null) {
      return ResponseMessage.message(500, "publicacion no existen");
    }
    try {
      data = mapper.writeValueAsString(publicaciones);
      return ResponseMessage.message(
        200,
        "publicaciones del usuario id " + id + " recuperada exitosamente",
        data
      );
    } catch (JsonProcessingException e) {
      return ResponseMessage.message(
        500,
        "error al  formatear los domicilios",
        e.getMessage()
      );
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String findAll() throws IOException {
    List<Publicacion> publicaciones = publicacionService.findAll();

    // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
    String data;

    try {
      data = mapper.writeValueAsString(publicaciones);
    } catch (IOException e) {
      return ResponseMessage.message(
        501,
        "Formato incorrecto en datos de entrada",
        e.getMessage()
      );
    }

    return ResponseMessage.message(
      200,
      "Publicaciones recuperadas con éxito",
      data
    );
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String create(String json) {
    Publicacion publicacion;
    Ubicacion ubicacion;
    String caracteristicasMascotasData;
    String dataPublicacion;
    try {
      //Extraigo la publicacion del json
      String publicacionJson = json.replaceAll(
        "\"ubication\":\\{\"latitude\":.*,\"longitude\":.*\\d\\},",
        ""
      );

      //Extraigo la ubicacion del json
      String ubicacionJson = json.replaceAll(
        "(.*)(\\{\"latitude\":.*,\"longitude\":.*\\d\\})(.*)",
        "$2"
      );
    //logger.info("create publicacion"+publicacionJson+"asd123");

      publicacion = mapper.readValue(publicacionJson, Publicacion.class);

      ubicacion = mapper.readValue(ubicacionJson, Ubicacion.class);

      publicacion = publicacionService.create(publicacion, ubicacion);

      dataPublicacion = mapper.writeValueAsString(publicacion);
      // dataPublicacion = ubicacionJson;
    } catch (JsonProcessingException e) {
      return ResponseMessage.message(
        502,
        "No se pudo dar formato a la salida",
        e.getMessage()
      );
    } catch (IOException e) {
      return ResponseMessage.message(
        501,
        "Formato incorrecto en datos de entrada",
        e.getMessage()
      );
    }
    return ResponseMessage.message(
      200,
      "Publicacion GENERADO correctamente",
      dataPublicacion
    );
  }

  @PUT
  @Path("/update/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String update(String json, @PathParam("id") int id)throws IOException {
    Publicacion publicacion;
    Ubicacion ubicacion;
    String dataPublicacion;
     logger.info(""+json);
      try {
      //Extraigo la publicacion del json
     /* String publicacionJson = json.replaceAll(
        "\"ubication\":\\{\"latitude\":.*,\"longitude\":.*\\d\\},",
        ""
      );*/
     String publicacionJson = json.replaceAll(
        ",\"ubication\":\\{\"latitude\":.*,\"longitude\":.*\\d\\}",
        ""
      );
      //Extraigo la ubicacion del json
      String ubicacionJson = json.replaceAll(
        "(.*)(\\{\"latitude\":.*,\"longitude\":.*\\d\\})(.*)",
        "$2"
      );
    
     logger.info("edit publicacion :"+publicacionJson+"despues del replace");

      publicacion = mapper.readValue(publicacionJson, Publicacion.class);

      ubicacion = mapper.readValue(ubicacionJson, Ubicacion.class);

      publicacion = publicacionService.update(publicacion, ubicacion);

      dataPublicacion = mapper.writeValueAsString(publicacion);
      // dataPublicacion = ubicacionJson;
    } catch (JsonProcessingException e) {
      return ResponseMessage.message(
        502,
        "No se pudo dar formato a la salida",
        e.getMessage()
      );
    } catch (IOException e) {
      return ResponseMessage.message(
        501,
        "Formato incorrecto en datos de entrada",
        e.getMessage()
      );
    }
    
   
    return ResponseMessage.message(
      200,
      "Publicacion editada correctamente",
      dataPublicacion
    );
  }
}
