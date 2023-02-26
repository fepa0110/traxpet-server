package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import model.Notificacion;
import model.Publicacion;
import model.Usuario;
import servlet.ResponseMessage;
import stateless.NotificacionService;
import stateless.PublicacionService;
import stateless.UsuarioService;

@Path("/notificaciones")
public class NotificacionServlet {

  private ObjectMapper mapper;

  @EJB
  private NotificacionService service;

  @EJB
  private UsuarioService usuarioService;

  @EJB
  private PublicacionService publicacionService;

  public NotificacionServlet() {
    mapper = new ObjectMapper();
    // Le provee el formateador de fechas.
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    mapper.setDateFormat(df);
  }

  @GET
  @Path("/usuario/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findByUser(@PathParam("id") long id) throws IOException {
    Usuario usuario = new Usuario();
    usuario.setId(id);
    Collection<Notificacion> notificaciones = service.findByUsuario(usuario);
    String data;

    if (notificaciones == null) return ResponseMessage.message(
      500,
      "El usuario: " + id + " no existe"
    );

    try {
      data = mapper.writeValueAsString(notificaciones);
    } catch (JsonProcessingException e) {
      return ResponseMessage.message(
        502,
        "No se pudo dar formato a la salida",
        e.getMessage()
      );
    }

    return ResponseMessage.message(
      200,
      "Se encontraron las notificaciones correctamente",
      data
    );
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String marcarVista(String json) {
    Notificacion notificacion;
    String data;

    try {
      notificacion = mapper.readValue(json, Notificacion.class);
      notificacion.setVista(true);
      service.marcarVista(notificacion);
      data = mapper.writeValueAsString(notificacion);
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
      "Se modificó la notificacion correctamente",
      data
    );
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String create(String json) {
    Notificacion notificacion;
    String data;

    try {
      notificacion = mapper.readValue(json, Notificacion.class);
      notificacion = service.create(notificacion);
      data = mapper.writeValueAsString(notificacion);
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
      "Se creo la notificacion correctamente",
      data
    );
  }
}
