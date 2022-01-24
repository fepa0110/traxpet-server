package servlet.bulk;

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
import stateless.MascotaService;

@Path("/bulk/mascotas")
public class MascotaBulkServlet {

    @EJB
    MascotaService mascotaService;

    Logger logger = Logger.getLogger(getClass().getName());
    private ObjectMapper mapper;

    public MascotaBulkServlet() {
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
        List<Mascota> mascotas;
        int cantidadMascotasCreadas = 0;
        String data;

        try {
            mascotas = mapper.readValue(
                json, new TypeReference<List<Mascota>>() {});

            for(Mascota mascota : mascotas){
                if(this.mascotaService.create(mascota) != null){
                    cantidadMascotasCreadas++;
                }
            }

        data = mapper.writeValueAsString(cantidadMascotasCreadas);
        } 
        catch (JsonProcessingException e) {
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
        data+" mascotas GENERADAS correctamente"
        );
    }
}
