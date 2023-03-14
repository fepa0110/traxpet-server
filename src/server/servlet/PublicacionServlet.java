package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Calendar;
import java.util.TimeZone;

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
import model.Usuario;
import model.Notificacion;

import servlet.PublicacionNueva;

import servlet.ResponseMessage;
import stateless.PublicacionService;
import stateless.NotificacionService;
import stateless.UsuarioService;

@Path("/publicaciones")
public class PublicacionServlet {

  private static final int PUNTAJE_NUEVA_PUBLICACION = 25;
  private static final int PUNTAJE_ACTUALIZAR_UBICACION = 30;
  private static final int PUNTAJE_MIGRAR_PUBLICACION = 10;

  @EJB
  PublicacionService publicacionService;

  @EJB
  NotificacionService notificacionService;

  @EJB
  UsuarioService usuarioService;

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
      return ResponseMessage.message(500, "no hay publicaciones");
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
  @Path("/vistasByUsername/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllPublicacionVistasUsuario(@PathParam("username") String username) throws IOException {
    Collection<Publicacion> publicaciones = publicacionService.findAllPublicacionVistasUsuario(username);

    String data;

    if (publicaciones == null) {
      return ResponseMessage.message(500, "no hay publicaciones");
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
  @Path("/buscadasByUsername/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllPublicacionBuscadasUsuario(@PathParam("username") String username) throws IOException {
    Collection<Publicacion> publicaciones = publicacionService.findAllPublicacionBuscadasUsuario(username);

    String data;

    if (publicaciones == null) {
      return ResponseMessage.message(500, "no hay publicaciones");
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
        "la publicacion " + id + " recuperada exitosamente",
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
  @Path("/publicar")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String create(String json) {

    PublicacionNueva publicacionRecibida;
    Publicacion publicacion;
    Ubicacion ubicacion;
    String caracteristicasMascotasData;
    String dataPublicacion;
    try {
      publicacionRecibida = mapper.readValue(json, PublicacionNueva.class);

      logger.info("valores: " + publicacionRecibida.getPublicacion().getMascota().getValores().toString());

      publicacion = publicacionService.create(
          publicacionRecibida.getPublicacion(),
          publicacionRecibida.getUbicacion());

      dataPublicacion = mapper.writeValueAsString(publicacion);

      if (publicacionRecibida.getNotificateSimilar()
          && publicacion != null
          && publicacionRecibida.getIdMascotaSimilar() != 0) {
        Notificacion notificacion = new Notificacion();

        notificacion.setPublicacion(publicacion);
        notificacion.setNotificante(publicacionRecibida.getPublicacion().getUsuario());

        Publicacion publicacionSimilar = new Publicacion();

        Mascota mascotaSimilar = new Mascota();
        mascotaSimilar.setId(publicacionRecibida.getIdMascotaSimilar());
        publicacionSimilar.setMascota(mascotaSimilar);
        publicacionSimilar = publicacionService.findByMascotaId(publicacionSimilar);
        Usuario usuarioSimilar = publicacionSimilar.getUsuario();

        notificacion.setFechaNotificacion(Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00")));

        notificacion.setUsuario(usuarioSimilar);
        notificacionService.create(notificacion);
        this.usuarioService.updateScore(usuarioSimilar, PUNTAJE_NUEVA_PUBLICACION);
      }

      if (publicacion != null) {
        this.usuarioService.updateScore(publicacionRecibida.getPublicacion().getUsuario(), PUNTAJE_NUEVA_PUBLICACION);
      }

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
      publicacion = mapper.readValue(json, Publicacion.class);

      publicacion = publicacionService.update(publicacion);

      dataPublicacion = mapper.writeValueAsString(publicacion);
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

      if (ubicacion != null) {
        this.usuarioService.updateScore(ubicacion.getUsuario(), PUNTAJE_ACTUALIZAR_UBICACION);
      }

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
    } catch (IOException e) {
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

  @PUT
  @Path("/migrarDueño")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String migrarDueño(@QueryParam("publicacionId") int publicacionId,
      @QueryParam("username") String username) throws IOException {
    Publicacion publicacion = publicacionService.findById(publicacionId);

    Usuario nuevoDueño = new Usuario();
    nuevoDueño.setUsername(username);

    String dataPublicacion;
    try {

      Publicacion publicacionMigrada = this.publicacionService.migrarDueño(publicacion, nuevoDueño);

      dataPublicacion = mapper.writeValueAsString(publicacionMigrada);

      this.usuarioService.updateScore(publicacion.getUsuario(), PUNTAJE_MIGRAR_PUBLICACION);
      this.usuarioService.updateScore(publicacionMigrada.getUsuario(), PUNTAJE_MIGRAR_PUBLICACION);

      // Notificar al antiguo dueño de la publicacion
      Notificacion notificacion = new Notificacion();

      notificacion.setPublicacion(publicacionMigrada);
      notificacion.setNotificante(publicacionMigrada.getUsuario());
      notificacion.setUsuario(publicacion.getUsuario());
      notificacion.setFechaNotificacion(Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00")));

      notificacionService.create(notificacion);
    } catch (JsonProcessingException e) {
      return ResponseMessage.message(
          502,
          "No se pudo dar formato a la salida",
          e.getMessage());
    } catch (NullPointerException e) {
      return ResponseMessage.message(
          500,
          "No se encontro alguno de los datos de entrada.",
          e.getMessage());
    }

    return ResponseMessage.message(
        200,
        "Publicacion migrada correctamente a " + username);
  }
}
