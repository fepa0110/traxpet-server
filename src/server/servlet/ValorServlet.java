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
import model.Caracteristica;
import model.Especie;
import model.Valor;
import servlet.ResponseMessage;
import stateless.CaracteristicaService;
import stateless.EspecieService;
import stateless.ValorService;

@Path("/valores")
public class ValorServlet {

  @EJB
  CaracteristicaService caracteristicaService;

  @EJB
  EspecieService especieService;

  @EJB
  ValorService valorService;

  private ObjectMapper mapper;

  public ValorServlet() {
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
    Valor valor;
    String data;
    Especie especie;
    Caracteristica caracteristica;

    try {
      valor = mapper.readValue(json, Valor.class);

      especie = especieService.findByName(valor.getEspecie().getNombre());
      caracteristica = caracteristicaService.findByName(valor.getCaracteristica().getNombre());
      
      valor.setEspecie(especie);
      valor.setCaracteristica(caracteristica);
      
      valor = valorService.create(valor);
      data = mapper.writeValueAsString(valor);
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

    return ResponseMessage.message(200, "Se creó el valor correctamente", data);
  }

  @GET
  @Path("/byEspecie")
  @Produces(MediaType.APPLICATION_JSON)
  public String findByEspecie(
    @QueryParam("especieNombre") String especieNombre
  )
    throws IOException {
    Especie especie = new Especie();
    especie.setNombre(especieNombre);

    // Se modifica este método para que utilice el servicio
    List<Valor> valores = valorService.findByEspecie(especie);

    // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
    String data;

    try {
      data = mapper.writeValueAsString(valores);
    } catch (IOException e) {
      return ResponseMessage.message(
        501,
        "Formato incorrecto en datos de entrada",
        e.getMessage()
      );
    }

    return ResponseMessage.message(
      200,
      "Valores de la especie " + especieNombre + " recuperados con éxito",
      data
    );
  }

  @GET
  @Path("/byCaracteristica")
  @Produces(MediaType.APPLICATION_JSON)
  public String findByCaracteristica(
    @QueryParam("caracteristicaNombre") String caracteristicaNombre
  )
    throws IOException {
    Caracteristica caracteristica = new Caracteristica();
    caracteristica.setNombre(caracteristicaNombre);

    // Se modifica este método para que utilice el servicio
    List<Valor> valores = valorService.findByCaracteristica(caracteristica);

    // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
    String data;

    try {
      data = mapper.writeValueAsString(valores);
    } catch (IOException e) {
      return ResponseMessage.message(
        501,
        "Formato incorrecto en datos de entrada",
        e.getMessage()
      );
    }

    return ResponseMessage.message(
      200,
      "Valores de la caracteristica " +
      caracteristicaNombre +
      " recuperados con éxito",
      data
    );
  }

  @GET
  @Path("/byEspecieYCaracteristica")
  @Produces(MediaType.APPLICATION_JSON)
  public String findByEspecieYCaracteristica(
    @QueryParam("especieNombre") String especieNombre,
    @QueryParam("caracteristicaNombre") String caracteristicaNombre
  )
    throws IOException {
    Especie especie = new Especie();
    especie.setNombre(especieNombre);

    Caracteristica caracteristica = new Caracteristica();
    caracteristica.setNombre(caracteristicaNombre);

    // Se modifica este método para que utilice el servicio
    List<Valor> valores = valorService.findByEspecieYCaracteristica(
      especie,
      caracteristica
    );

    // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
    String data;

    try {
      data = mapper.writeValueAsString(valores);
    } catch (IOException e) {
      return ResponseMessage.message(
        501,
        "Formato incorrecto en datos de entrada",
        e.getMessage()
      );
    }

    return ResponseMessage.message(
      200,
      "Valores de la especie " +
      especieNombre +
      " y la caracteristica " +
      caracteristicaNombre +
      " recuperados con éxito",
      data
    );
  }

  @GET
  @Path("/caracteristicasByEspecie")
  @Produces(MediaType.APPLICATION_JSON)
  public String caracteristicasByEspecie(
    @QueryParam("especieNombre") String especieNombre
  )
    throws IOException {
    Especie especie = new Especie();
    especie.setNombre(especieNombre);

    // Se modifica este método para que utilice el servicio

    // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
    return valorService.findCaracteristicasConValores(especie);
  }
}
