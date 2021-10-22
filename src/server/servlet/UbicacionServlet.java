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
import model.Ubicacion;
import servlet.ResponseMessage;
import stateless.UbicacionService;

@Path("/ubicaciones")
public class UbicacionServlet {


  private ObjectMapper mapper;

  @EJB
  private UbicacionService ubicacionService;

  public UbicacionServlet() {
    mapper = new ObjectMapper();
    mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

    // Le provee el formateador de fechas.
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    mapper.setDateFormat(df);
  }

  @GET
  @Path("publicacion/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findById(@PathParam("id") int id) throws IOException {
  Ubicacion ubicacion = ubicacionService.findByPublicacion(id);
    String data;

          if (ubicacion != null) {
            return "{\"StatusCode\":200,\"StatusText\":\"ubicacion recuperado exitosamente\",\"data\":"
                    + "{\"ubicacion\": {"
                    + "\"id\": " + ubicacion.getId() + ","
                    + "\"latitude\": " + ubicacion.getLongitude() + ","
                    + "\"longitude\":" + ubicacion.getLatitude()
                    + "}}}";
        } else
            return ResponseMessage.message(500, "La ubicacion  NO existe");

  }
  
}
