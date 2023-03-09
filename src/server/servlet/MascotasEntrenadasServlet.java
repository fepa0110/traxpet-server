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

import model.MascotasEntrenadas;
import model.Especie;

import servlet.ResponseMessage;

import stateless.MascotasEntrenadasService;
import stateless.MascotasEntrenadasServiceBean;


@Path("/mascotasEntrenadas")
public class MascotasEntrenadasServlet {

  @EJB
  MascotasEntrenadasService mascotasEntrenadasService;

  private ObjectMapper mapper;

  public MascotasEntrenadasServlet() {
    mapper = new ObjectMapper();
    mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    mapper.setDateFormat(df);
  }

  @GET
  @Path("/predict/{especieNombre}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findMascotasEntrenadas(String json, @PathParam("especieNombre") String especieNombre)
    throws IOException {
    String data;

    try {
      Collection<Long> mascotasEntrenadas = 
        mascotasEntrenadasService.predictByEspecieAndModeloActivo(especieNombre);

      data = mapper.writeValueAsString(mascotasEntrenadas);
    } catch (IOException e) {
      return ResponseMessage.message(
        501,
        "Formato incorrecto en datos de entrada",
        e.getMessage()
      );
    }

    return ResponseMessage.message(
      200,
      "Caracteristicas de la especie " +
      especieNombre +
      " recuperadas con éxito",
      data
    );
  }
  
}