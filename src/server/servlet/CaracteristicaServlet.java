package servlet;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import java.util.List;
import java.util.logging.Logger;
import java.util.Collection;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


import model.Caracteristica;
import model.Especie;

import stateless.CaracteristicaService;
import stateless.EspecieService;

import servlet.ResponseMessage;


@Path("/caracteristicas")
public class CaracteristicaServlet {
    @EJB
    CaracteristicaService caracteristicaService;

    @EJB
    EspecieService especieService;

    private ObjectMapper mapper;

    public CaracteristicaServlet(){
        mapper = new ObjectMapper();
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

        // Le provee el formateador de fechas.
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(df);
    }

    @GET
    @Path("/byEspecie")
    @Produces(MediaType.APPLICATION_JSON)
    public String findByEspecie(@QueryParam("especieNombre") String especieNombre) throws IOException{
        Especie especie = new Especie();
        especie.setNombre(especieNombre);

        // Se modifica este método para que utilice el servicio
        List<Caracteristica> caracteristicas = caracteristicaService.findByEspecie(especie);

        // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
        String data;

        try {  
            data = mapper.writeValueAsString(caracteristicas);
        } 
        catch (IOException e) {
            return ResponseMessage
            .message(501, "Formato incorrecto en datos de entrada", e.getMessage());
        }

        return ResponseMessage.message(200,"Caracteristicas de la especie "+especieNombre+" recuperadas con éxito",data);
    }
}