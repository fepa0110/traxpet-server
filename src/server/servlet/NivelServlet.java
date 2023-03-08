package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ejb.EJB;
import javax.persistence.EntityManager;

import java.util.List;
import java.util.logging.Logger;
import java.util.Collection;

import model.Nivel;
import model.Usuario;
import stateless.NivelService;
import stateless.UsuarioService;

@Path("/niveles")
public class NivelServlet {
    @EJB
    NivelService nivelService;

    @EJB
    UsuarioService usuarioService;

    private ObjectMapper mapper;

    public NivelServlet() {
    mapper = new ObjectMapper();
    mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

    // Le provee el formateador de fechas.
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    mapper.setDateFormat(df);
  }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findAll() throws IOException {
        Collection<Nivel> nivels = nivelService.findAll();
        String data;
        try {
            data = mapper.writeValueAsString(nivels);
        } catch (JsonProcessingException e) {
            return ResponseMessage.message(
                    502,
                    "No se pudo dar formato a la salida",
                    e.getMessage());
        }
        if (nivels == null)
            return ResponseMessage.message(
                    503,
                    "No se encontraron nivels");

        return ResponseMessage.message(
                200,
                "Niveles recuperadas con éxito",
                data);
    }

    
    @GET
    @Path("/calcularNivel")
    @Produces(MediaType.APPLICATION_JSON)
    public String calcularNivel(@QueryParam("username") String username) throws IOException {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario = usuarioService.findByUsername(usuario);

        if(usuario.getPuntaje() < 0){ 
            return ResponseMessage.message(
                503,
                "El puntaje es menor a cero");
        }

        Nivel nivel = nivelService.calcularNivel(usuario.getPuntaje());
        if(nivel == null){
            nivel = nivelService.getMaxNivel();
        }

        String data;
        try {
            data = mapper.writeValueAsString(nivel);
        } catch (JsonProcessingException e) {
            return ResponseMessage.message(
                    502,
                    "No se pudo dar formato a la salida",
                    e.getMessage());
        }
        if (nivel == null)
            return ResponseMessage.message(
                    503,
                    "No se encontro nivel");

        return ResponseMessage.message(
                200,
                "Nivel recuperado con éxito",
                data);
    }

    @GET
    @Path("/nivelesObtenidos")
    @Produces(MediaType.APPLICATION_JSON)
    public String nivelesObtenidos(@QueryParam("username") String username) throws IOException {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario = usuarioService.findByUsername(usuario);

        Nivel nivelMaximo = nivelService.getMaxNivel();

        List<Nivel> niveles;

        if(usuario.getPuntaje() > nivelMaximo.getPuntajeMaximo()){
            niveles = nivelService.getNivelesObtenidos(nivelMaximo.getPuntajeMaximo());
        }
        else niveles = nivelService.getNivelesObtenidos(usuario.getPuntaje());

        String data;
        try {
            data = mapper.writeValueAsString(niveles);
        } catch (JsonProcessingException e) {
            return ResponseMessage.message(
                    502,
                    "No se pudo dar formato a la salida",
                    e.getMessage());
        }
        if (niveles == null)
            return ResponseMessage.message(
                    503,
                    "No se encontraron niveles");

        return ResponseMessage.message(
                200,
                "Niveles recuperados con éxito",
                data);
    }
}
