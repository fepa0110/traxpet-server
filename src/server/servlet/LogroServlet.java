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
import model.Logro;
import javax.ws.rs.GET;
import stateless.LogroService;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;

@Path("/logros")
public class LogroServlet {
    @EJB
    LogroService logroService;

    private ObjectMapper mapper;

  public LogroServlet() {
    mapper = new ObjectMapper();
    mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

    // Le provee el formateador de fechas.
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    mapper.setDateFormat(df);
  }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findAll() throws IOException {
        Collection<Logro> logros = logroService.findAll();
        String data;
        try {
            data = mapper.writeValueAsString(logros);
        } catch (JsonProcessingException e) {
            return ResponseMessage.message(
                    502,
                    "No se pudo dar formato a la salida",
                    e.getMessage());
        }
        if (logros == null)
            return ResponseMessage.message(
                    503,
                    "No se encontraron logros");

        return ResponseMessage.message(
                200,
                "logros recuperadas con éxito",
                data);
    }
}
