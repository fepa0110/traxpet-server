package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import model.Mascota;
import servlet.ResponseMessage;
import stateless.CaracteristicaService;
import stateless.EspecieService;
import stateless.ValorService;
import stateless.MascotaService;

@Path("/valores")
public class ValorServlet {

  @EJB
  CaracteristicaService caracteristicaService;

  @EJB
  EspecieService especieService;

  @EJB
  ValorService valorService;

  @EJB
  MascotaService mascotaService;

  private ObjectMapper mapper;

  public ValorServlet() {
    mapper = new ObjectMapper();
    mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

    // Le provee el formateador de fechas.
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    mapper.setDateFormat(df);
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String delete(String json) {
    Valor valor;
    String data;

    try {
      valor = mapper.readValue(json, Valor.class);
      valor = valorService.find(valor);
      valor.setDeshabilitado(true);
      valorService.darBaja(valor);
      data = mapper.writeValueAsString(valor);
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
        "Se modificó el valor correctamente",
        data);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String create(String json) {
    Valor valor;
    String data;
    Especie especie;
    Caracteristica caracteristica;

    String name;

    try {
      valor = mapper.readValue(json, Valor.class);
      name = valor.getCaracteristica().getNombre();
      especie = especieService.findByName(valor.getEspecie().getNombre());
      caracteristica = caracteristicaService.findByName(name);

      valor.setEspecie(especie);
      valor.setCaracteristica(caracteristica);
      valor.setDeshabilitado(false);

      valor = valorService.create(valor);

      data = mapper.writeValueAsString(valor);
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

    return ResponseMessage.message(200, "Se creó el valor correctamente", data);
  }

  @GET
  @Path("/byEspecie")
  @Produces(MediaType.APPLICATION_JSON)
  public String findByEspecie(
      @QueryParam("especieNombre") String especieNombre)
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
          e.getMessage());
    }

    return ResponseMessage.message(
        200,
        "Valores de la especie " + especieNombre + " recuperados con éxito",
        data);
  }

  // @GET
  // @Path("/byMascota")
  // @Produces(MediaType.APPLICATION_JSON)
  // public String findByMascota(
  // @QueryParam("idMascota") long mascotaId)
  // throws IOException {
  // Mascota mascota = mascotaService.findById(mascotaId);
  // if (mascota == null) {
  // return ResponseMessage.message(500, mascotaId + " no encontrada.");
  // }
  // String data;
  // try {
  // data = mapper.writeValueAsString(mascota.getValores());
  // } catch (IOException e) {
  // return ResponseMessage.message(
  // 501,
  // "Formato incorrecto en datos de entrada",
  // e.getMessage());
  // }
  // return ResponseMessage.message(
  // 200,
  // "Valores de la mascota " + mascotaId + " recuperados con éxito",
  // data);
  // }

  @GET
  @Path("/byMascota")
  @Produces(MediaType.APPLICATION_JSON)
  public String findByMascota(
      @QueryParam("idMascota") String json)
      throws IOException {
    int[] ids;
    String data;
    List<List<Valor>> valores = new ArrayList<List<Valor>>();

    try {
      ids = mapper.readValue(json, int[].class);

      for (int i = 0; i < ids.length; i++) {
        valores.add(mascotaService.findById(ids[i]).getValores());
      }

      data = mapper.writeValueAsString(valores);
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
        "Valores recuperados con éxito",
        data);
  }

  @GET
  @Path("/byCaracteristica")
  @Produces(MediaType.APPLICATION_JSON)
  public String findByCaracteristica(
      @QueryParam("caracteristicaNombre") String caracteristicaNombre)
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
          e.getMessage());
    }

    return ResponseMessage.message(
        200,
        "Valores de la caracteristica " +
            caracteristicaNombre +
            " recuperados con éxito",
        data);
  }

  @GET
  @Path("/byEspecieYCaracteristica")
  @Produces(MediaType.APPLICATION_JSON)
  public String findByEspecieYCaracteristica(
      @QueryParam("especieNombre") String especieNombre,
      @QueryParam("caracteristicaNombre") String caracteristicaNombre)
      throws IOException {
    Especie especie = new Especie();
    especie.setNombre(especieNombre);

    Caracteristica caracteristica = new Caracteristica();
    caracteristica.setNombre(caracteristicaNombre);

    // Se modifica este método para que utilice el servicio
    List<Valor> valores = valorService.findEnabled(especie, caracteristica);

    // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
    String data;

    try {
      data = mapper.writeValueAsString(valores);
    } catch (IOException e) {
      return ResponseMessage.message(
          501,
          "Formato incorrecto en datos de entrada",
          e.getMessage());
    }

    return ResponseMessage.message(
        200,
        "Valores de la especie " +
            especieNombre +
            " y la caracteristica " +
            caracteristicaNombre +
            " recuperados con éxito",
        data);
  }

  @GET
  @Path("/allByEspecieYCaracteristica")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllByEspecieYCaracteristica(
      @QueryParam("especieNombre") String especieNombre,
      @QueryParam("caracteristicaNombre") String caracteristicaNombre)
      throws IOException {
    Especie especie = new Especie();
    especie.setNombre(especieNombre);

    Caracteristica caracteristica = new Caracteristica();
    caracteristica.setNombre(caracteristicaNombre);

    // Se modifica este método para que utilice el servicio
    List<Valor> valores = valorService.findByEspecieYCaracteristica(
        especie,
        caracteristica);

    // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
    String data;

    try {
      data = mapper.writeValueAsString(valores);
    } catch (IOException e) {
      return ResponseMessage.message(
          501,
          "Formato incorrecto en datos de entrada",
          e.getMessage());
    }

    return ResponseMessage.message(
        200,
        "Valores de la especie " +
            especieNombre +
            " y la caracteristica " +
            caracteristicaNombre +
            " recuperados con éxito",
        data);
  }

  @GET
  @Path("/allByEspecieYCaracteristicaEnabled")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllByEspecieYCaracteristicaEnabled(
      @QueryParam("especieNombre") String especieNombre,
      @QueryParam("caracteristicaNombre") String caracteristicaNombre)
      throws IOException {
    Especie especie = new Especie();
    especie.setNombre(especieNombre);

    Caracteristica caracteristica = new Caracteristica();
    caracteristica.setNombre(caracteristicaNombre);

    // Se modifica este método para que utilice el servicio
    List<Valor> valores = valorService.findByEspecieYCaracteristicaEnabled(
        especie,
        caracteristica);

    // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
    String data;

    try {
      data = mapper.writeValueAsString(valores);
    } catch (IOException e) {
      return ResponseMessage.message(
          501,
          "Formato incorrecto en datos de entrada",
          e.getMessage());
    }

    return ResponseMessage.message(
        200,
        "Valores de la especie " +
            especieNombre +
            " y la caracteristica " +
            caracteristicaNombre +
            " recuperados con éxito",
        data);
  }

  @GET
  @Path("/caracteristicasByEspecie")
  @Produces(MediaType.APPLICATION_JSON)
  public String caracteristicasByEspecie(
      @QueryParam("especieNombre") String especieNombre) {
    Especie especie = new Especie();
    especie.setNombre(especieNombre);
    return valorService.findCaracteristicasConValores(especie);
  }
}
