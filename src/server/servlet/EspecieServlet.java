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
import model.Especie;
import servlet.ResponseMessage;
import stateless.EspecieService;

@Path("/especies")
public class EspecieServlet {

  @EJB
  EspecieService especieService;

  private ObjectMapper mapper;

  public EspecieServlet() {
    mapper = new ObjectMapper();
    mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

    // Le provee el formateador de fechas.
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    mapper.setDateFormat(df);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String create(String json) {
    Especie especie;
    String data;

    try {
      especie = mapper.readValue(json, Especie.class);
      especie = especieService.create(especie);
      data = mapper.writeValueAsString(especie);
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
      "Se modificó correctamente la especie",
      data
    );
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String findAll() throws IOException {
    // Se modifica este método para que utilice el servicio
    List<Especie> especies = especieService.findAll();

    // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
    String data;

    try {
      data = mapper.writeValueAsString(especies);
    } catch (IOException e) {
      return ResponseMessage.message(
        501,
        "Formato incorrecto en datos de entrada",
        e.getMessage()
      );
    }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String sendEspecie(String json) {

    Especie especie;
    String data;

    try {
      especie = mapper.readValue(json, Especie.class);

      especie = especieService.create(especie);

      data = mapper.writeValueAsString(especie);

    } catch (JsonProcessingException e) {
      return ResponseMessage
        .message(502, "No se pudo dar formato a la salida", e.getMessage());
    } catch (IOException e) {
      return ResponseMessage
        .message(501, "Formato incorrecto en datos de entrada", e.getMessage());
    }

    return ResponseMessage.message(
      200,
      "Se cargó  especie exitosamente",
      data
    );
  
}
}
