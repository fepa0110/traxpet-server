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


import model.Usuario;

import stateless.UsuarioService;

import servlet.ResponseMessage;


@Path("/usuarios")
public class UsuarioServlet {
    @EJB
    UsuarioService usuarioService;

    private ObjectMapper mapper;

    Logger logger = Logger.getLogger(getClass().getName());

    public UsuarioServlet(){
        mapper = new ObjectMapper();
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

        // Le provee el formateador de fechas.
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(df);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findAll() throws IOException{
        // Se modifica este método para que utilice el servicio
        List<Usuario> usuarios = usuarioService.findAll();

        // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
        String data;

        try {  
            data = mapper.writeValueAsString(usuarios);
        } 
        catch (IOException e) {
            return ResponseMessage
            .message(501, "Formato incorrecto en datos de entrada", e.getMessage());
        }

        return ResponseMessage.message(200,"Usuarios recuperados con éxito",data);
    }

    @GET
    @Path("/username/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public String findByUsername(@PathParam("username") String username) throws IOException{
        Usuario usuario = new Usuario();
        usuario.setUsername(username);

        // Se modifica este método para que utilice el servicio
        Usuario usuarioEncontrado = usuarioService.findByUsername(usuario);

        // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
        String data;

        if(usuarioEncontrado == null){
            return ResponseMessage.message(502,"Usuario no encontrado");
        }

        try {  
            usuarioEncontrado.setPassword(null);
            data = mapper.writeValueAsString(usuarioEncontrado);
        } 
        catch (IOException e) {
            return ResponseMessage
            .message(501, "Formato incorrecto en datos de entrada", e.getMessage());
        }

        return ResponseMessage.message(200,"Usuarios recuperados con éxito",data);
    }

    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String findByCorreoElectronico(@PathParam("email") String email) throws IOException{
        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico(email);

        // Se modifica este método para que utilice el servicio
        Usuario usuarioEncontrado = usuarioService.findEmailExists(usuario);

        // Se contruye el resultado en base a lo recuperado desde la capa de negocio.
        String data;

        if(usuarioEncontrado == null){
            return ResponseMessage.message(502,"Usuario no encontrado");
        }

        try {  
            usuarioEncontrado.setPassword(null);
            data = mapper.writeValueAsString(usuarioEncontrado);
        } 
        catch (IOException e) {
            return ResponseMessage
            .message(501, "Formato incorrecto en datos de entrada", e.getMessage());
        }

        return ResponseMessage.message(200,"Usuarios recuperados con éxito",data);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(String json) {
        Usuario usuario;
        String dataUsuario;

        try {
            usuario = mapper.readValue(json, Usuario.class);
            usuario = usuarioService.create(usuario);
            
            if(usuario == null){
                return ResponseMessage
                    .message(502, "El usuario ya existe");
            }
            
            dataUsuario = mapper.writeValueAsString(usuario);
        } 
        catch (JsonProcessingException e) {
            return ResponseMessage
                .message(502, "No se pudo dar formato a la salida", e.getMessage());
        } 
        catch (IOException e) {
            return ResponseMessage
                .message(501, "Formato incorrecto en datos de entrada", e.getMessage());
        }
        return ResponseMessage.message(200,"Usuario GENERADO correctamente",dataUsuario);
    }

    @POST
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String update(String json) {
        Usuario usuario;
        String data;

        try {
            usuario = mapper.readValue(json, Usuario.class);
            usuario = usuarioService.update(usuario);
            data = mapper.writeValueAsString(usuario);
        } 
        catch (JsonProcessingException e) {
            return ResponseMessage
                .message(502, "No se pudo dar formato a la salida", e.getMessage());
        } 
        catch (IOException e) {
            return ResponseMessage
                .message(501, "Formato incorrecto en datos de entrada", e.getMessage());
        }
        return ResponseMessage.message(200,"Usuario MODIFICADO correctamente",data);
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String autentificarUsuario(String json) throws IOException{
        Usuario usuario;
        String data;

        try {
            usuario = mapper.readValue(json, Usuario.class);
            Usuario usuarioAutentificado = usuarioService.autenticarUsuario(usuario);
            
            if(usuarioAutentificado == null){
                return ResponseMessage
                    .message(502, "Usuario o contraseña no validos.");
            }

            usuarioAutentificado.setPassword(null);

            data = mapper.writeValueAsString(usuarioAutentificado);
        } 
        catch (JsonProcessingException e) {
            return ResponseMessage
                .message(501, "No se pudo dar formato a la salida", e.getMessage());
        } 
        catch (IOException e) {
            return ResponseMessage
                .message(501, "Formato incorrecto en datos de entrada", e.getMessage());
        }
        return ResponseMessage.message(200,"Usuario AUTENTIFICADO correctamente",data);
    }

    @GET
    @Path("/search/{search}")
    @Produces(MediaType.APPLICATION_JSON)
    public String search(@PathParam("search") String search) {

        // Se modifica este método para que utilice el servicio
        Collection<Usuario> usuarios = usuarioService.search(search);

        if (usuarios == null || usuarios.isEmpty()){
            return ResponseMessage.message(505, "No existe usuario para la búsqueda indicada: " + search);
        }

        String data;
        try {

        data = mapper.writeValueAsString(usuarios);

        } catch (IOException e) {
        return ResponseMessage.message(501, "Formato incorrecto en datos de entrada", e.getMessage());
        }

        return ResponseMessage.message(200, "Se recuperaron los usuarios buscados", data);
    }

    @PUT
    @Path("/addScore/{puntaje}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addUbicacionUsuario(String json, @PathParam("puntaje") int puntaje)
        throws IOException {
        Usuario usuario;
        String data;
    
        try {
            usuario = mapper.readValue(json, Usuario.class);
    
            usuario = usuarioService.updateScore(usuario,puntaje);
    
            data = mapper.writeValueAsString(usuario);
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
            "Usuario " + data);
    }
}
