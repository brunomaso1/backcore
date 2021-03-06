package ucu.deliverit.backcore.entidades.servicios;

import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import ucu.deliverit.backcore.entidades.Delivery;
import ucu.deliverit.backcore.entidades.Ubicacion;
import ucu.deliverit.backcore.entidades.Usuario;
import ucu.deliverit.backcore.respuestas.RespuestaGeneral;

@Stateless
@Path("delivery")
public class DeliveryFacadeREST extends AbstractFacade<Delivery> {
    
    @EJB
    private UsuarioFacadeREST usuarioFacade;

    @PersistenceContext(unitName = "ucu.deliverit_BackCore_war_1.0PU")
    private EntityManager em;

    public DeliveryFacadeREST() {
        super(Delivery.class);
    }

    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaGeneral create(Delivery entity) {
        RespuestaGeneral r = new RespuestaGeneral();

        if (entity == null) {
                r.setCodigo(RespuestaGeneral.CODIGO_ERROR_VALOR_NULO);
                r.setMensaje("Delivery" + RespuestaGeneral.MENSAJE_VALOR_NULO);
                r.setObjeto(null);
        } else {
                r = super.create(entity);
        }
        return r;
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Integer id, Delivery entity) {
        super.edit(entity);
    }
    
    @PUT
    @Path("actualizarToken/{id}/{token}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void actualizarToken(@PathParam("id") Integer id, @PathParam("token") String token) {
        Delivery d = find(id);
        d.setToken(token);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Delivery find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Delivery> findAll() {
        return super.findAll();
    }

    @GET
    @Path("findBySucursal/{idSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Delivery> findBySucursal(@PathParam("idSucursal") Integer idSucursal) {
        List<Delivery> results = em.createNamedQuery("Delivery.findBySucursal")
                .setParameter("idSucursal", idSucursal)
                .getResultList();
        return results;
    }

    @GET
    @Path("findAllSinViajesEnProceso")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Delivery> findAllSinViajesEnProceso() {
        List<Delivery> results = em.createNamedQuery("Delivery.findAllSinViajesEnProceso")
                .getResultList();
        return results;
    }

    @GET
    @Path("getUbicacion/{idDelivery}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ubicacion getUbicacion(@PathParam("idDelivery") Integer idSucursal) {
        String consulta = "SELECT u FROM Ubicacion u "
                        + " JOIN u.delivery d"
                        + " WHERE d.id = :idDelivery";
        Ubicacion result = em.createQuery(consulta, Ubicacion.class)
                        .setParameter("idDelivery", idSucursal)
                        .getSingleResult();
        return result;
    }
    
    @GET
    @Path("findByUsuario/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Delivery findByUsuario(@PathParam("idUsuario") Integer idUsuario) {
        Delivery result = (Delivery)em.createNamedQuery("Delviery.findByUsuario")
                        .setParameter("idUsuario", idUsuario)
                        .getSingleResult();
        return result;
    }
    
    @GET
    @Path("login/{nombreUsuario}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaGeneral login(@PathParam("nombreUsuario") String nombreUsuario,
                    @PathParam("password") String password) {
        RespuestaGeneral result = new RespuestaGeneral();
        
        Usuario usuario = usuarioFacade.findUserByName(nombreUsuario);
        
        if (usuario != null) {
            if (usuario.getPassword().equals(password)) {
                result.setCodigo(RespuestaGeneral.CODIGO_OK);
                result.setMensaje(RespuestaGeneral.MENSAJE_OK);
                
                Delivery d = findByUsuario(usuario.getId());
                Gson gson = new Gson();
                String jsonObject = gson.toJson(d.getId());
                result.setObjeto(jsonObject);
            } else {
                result.setCodigo(RespuestaGeneral.CODIGO_ERROR_VALOR_INCORRECTO);
                result.setMensaje(RespuestaGeneral.MENSAJE_USUARIO_INCORRECTO);
            }
        } else {
            result.setCodigo(RespuestaGeneral.CODIGO_ERROR_VALOR_INCORRECTO);
            result.setMensaje(RespuestaGeneral.MENSAJE_USUARIO_INCORRECTO);
        }

        return result;
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Delivery> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void actualizarCalificacion(Integer id, Short calificacion) {
        Delivery d = find(id);
        d.setCalificacion(calificacion);
    }
}
