package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
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
  @Path("/usuario/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllPublicacionUsuario(@PathParam("username") String username) throws IOException {
    Collection<Publicacion> publicaciones = publicacionService.findAllPublicacionUsuario(username);

    String data;

    if (publicaciones == null) {
      return ResponseMessage.message(500, "publicacion no existen");
    }
    try {
      data = mapper.writeValueAsString(publicaciones);
    } catch (JsonProcessingException e) {
      return ResponseMessage.message(
          500,
          "error al formatear las publicaciones",
          e.getMessage());
    }

    return ResponseMessage.message(
        200,
        "publicaciones del usuario " + username + " recuperada exitosamente",
        data);
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findById(@PathParam("id") long id) throws IOException {
    Publicacion publicacion = publicacionService.findById(id);

    String data;

    if (publicacion == null) {
      return ResponseMessage.message(500, "la publicacion no existe");
    }
    try {
      data = mapper.writeValueAsString(publicacion);
    } catch (JsonProcessingException e) {
      return ResponseMessage.message(
          500,
          "error al formatear la publicacion",
          e.getMessage());
    }

    return ResponseMessage.message(
        200,
        "la publicacion "+id+" recuperada exitosamente",
        data);
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
          e.getMessage());
    }

    return ResponseMessage.message(
        200,
        "Publicaciones recuperadas con éxito",
        data);
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
      // Extraigo la publicacion del json
      String publicacionJson = json.replaceAll(
          "\"ubication\":\\{\"latitude\":.*,\"longitude\":.*\\d\\},",
          "");

      // Extraigo la ubicacion del json
      String ubicacionJson = json.replaceAll(
          "(.*)(\\{\"latitude\":.*,\"longitude\":.*\\d\\})(.*)",
          "$2");
      // logger.info("create publicacion"+publicacionJson+"asd123");

      publicacion = mapper.readValue(publicacionJson, Publicacion.class);

      ubicacion = mapper.readValue(ubicacionJson, Ubicacion.class);

      publicacion = publicacionService.create(publicacion, ubicacion);

      dataPublicacion = mapper.writeValueAsString(publicacion);
      // dataPublicacion = ubicacionJson;
    } catch (JsonProcessingException e) {
      return ResponseMessage.message(
          502,
          "No se pudo dar formato a la salida",
          e.getMessage());
    } catch (IOException e) {
      return ResponseMessage.message(
          501,
          "Formato incorrecto en datos de entrada",
          e.getMessage());
    }
    return ResponseMessage.message(
        200,
        "Publicacion GENERADO correctamente",
        dataPublicacion);
  }

  @PUT
  @Path("/update/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String update(String json, @PathParam("id") int id) throws IOException {
    Publicacion publicacion;
    Ubicacion ubicacion;
    String dataPublicacion;
    logger.info("" + json);
    try {
      // Extraigo la publicacion del json
      /*
       * String publicacionJson = json.replaceAll(
       * "\"ubication\":\\{\"latitude\":.*,\"longitude\":.*\\d\\},",
       * ""
       * );
       */
      String publicacionJson = json.replaceAll(
          ",\"ubication\":\\{\"id\":\\d+,\"latitude\":.*,\"longitude\":.*\\d\\}",
          "");
      // Extraigo la ubicacion del json
      String ubicacionJson = json.replaceAll(
          "(.*)(\\{\"id\":\\d+,\"latitude\":.*,\"longitude\":.*\\d\\})(.*)",
          "$2");

      logger.info("edit publicacion :" + publicacionJson + "despues del replace");

      publicacion = mapper.readValue(publicacionJson, Publicacion.class);

      ubicacion = mapper.readValue(ubicacionJson, Ubicacion.class);

      publicacion = publicacionService.update(publicacion, ubicacion);

      dataPublicacion = mapper.writeValueAsString(publicacion);
      // dataPublicacion = ubicacionJson;
    } catch (JsonProcessingException e) {
      return ResponseMessage.message(
          502,
          "No se pudo dar formato a la salida",
          e.getMessage());
    } catch (IOException e) {
      return ResponseMessage.message(
          501,
          "Formato incorrecto en datos de entrada",
          e.getMessage());
    }

    return ResponseMessage.message(
        200,
        "Publicacion editada correctamente",
        dataPublicacion);
  }

  @PUT
  @Path("/addUbicacion")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String addUbicacionUsuario(String json, @QueryParam("mascotaId") int mascotaId)
      throws IOException {

    Ubicacion ubicacion;
    String dataUbicacion = "";
    logger.info("" + json);

    try {
      ubicacion = mapper.readValue(json, Ubicacion.class);

      ubicacion = publicacionService.addUbicacionMascota(ubicacion, mascotaId);

      dataUbicacion = mapper.writeValueAsString(ubicacion);
    }
    catch (JsonProcessingException e) {
      return ResponseMessage.message(
          502,
          "No se pudo dar formato a la salida",
          e.getMessage());
    }
    catch (IOException e) {
      return ResponseMessage.message(
          501,
          "Formato incorrecto en datos de entrada",
          e.getMessage());
    }

    return ResponseMessage.message(
        200,
        "Ubicacion de la publicacion " + ubicacion.getPublicacion().getId() + " actualizada correctamente",
        dataUbicacion);
  }

  @PUT
  @Path("/markAsFound/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String markAsFound(@PathParam("id") int id) throws IOException {
    Publicacion publicacion;
    String dataPublicacion;
    try {

      publicacion = publicacionService.find(id);
      if (publicacion == null) {
        return ResponseMessage.message(
            501,
            "No se encontro la publicacion: " + id);
      }

      publicacion = publicacionService.markAsFound(publicacion);

      dataPublicacion = mapper.writeValueAsString(publicacion);
    }

    catch (IOException e) {
      return ResponseMessage.message(
          501,
          "Formato incorrecto en datos de entrada",
          e.getMessage());
    }

    return ResponseMessage.message(
        200,
        "Publicacion editada correctamente",
        dataPublicacion);
  }

  @GET
  @Path("/mascota/{mascotaId}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findByMascotaId(@PathParam("mascotaId") int mascotaId) throws IOException {
    Publicacion publicacion = new Publicacion();
    publicacion.setMascota(new Mascota());
    publicacion.getMascota().setId(mascotaId);

    publicacion = publicacionService.findByMascotaId(publicacion);

    String data;

    try {
      data = mapper.writeValueAsString(publicacion);
    } 
    catch (IOException e) {
      return ResponseMessage.message(
          501,
          "Formato incorrecto en datos de entrada",
          e.getMessage());
    }

    return ResponseMessage.message(
        200,
        "Publicacion recuperada con éxito",
        data);
  }
}
