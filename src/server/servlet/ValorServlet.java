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
import model.Valor;

import stateless.CaracteristicaService;
import stateless.EspecieService;
import stateless.ValorService;

import servlet.ResponseMessage;


@Path("/valores")
public class ValorServlet {
    @EJB
    CaracteristicaService caracteristicaService;

    @EJB
    EspecieService especieService;

    @EJB
    ValorService valorService;

    private ObjectMapper mapper;

    public ValorServlet(){
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
        List<Valor> valores = valorService.findByEspecie(especie);

        // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
        String data;

        try {  
            data = mapper.writeValueAsString(valores);
        } 
        catch (IOException e) {
            return ResponseMessage
            .message(501, "Formato incorrecto en datos de entrada", e.getMessage());
        }

        return ResponseMessage.message(200,"Valores de la especie "+especieNombre+" recuperados con éxito",data);
    }

    @GET
    @Path("/byCaracteristica")
    @Produces(MediaType.APPLICATION_JSON)
    public String findByCaracteristica(@QueryParam("caracteristicaNombre") String caracteristicaNombre) throws IOException{
        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setNombre(caracteristicaNombre);

        // Se modifica este método para que utilice el servicio
        List<Valor> valores = valorService.findByCaracteristica(caracteristica);

        // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
        String data;

        try {  
            data = mapper.writeValueAsString(valores);
        } 
        catch (IOException e) {
            return ResponseMessage
            .message(501, "Formato incorrecto en datos de entrada", e.getMessage());
        }

        return ResponseMessage.message(200,"Valores de la caracteristica "+caracteristicaNombre+" recuperados con éxito",data);
    }
}