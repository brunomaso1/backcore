package ucu.deliverit.backcore.entidades.servicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import ucu.deliverit.backcore.entidades.Transaccion;
import ucu.deliverit.backcore.respuestas.RespuestaGeneral;

public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public RespuestaGeneral create(T entity) {
       RespuestaGeneral r = new RespuestaGeneral();
       try {   
            getEntityManager().persist(entity);    
            
            // Se utiliza flush para obtener el Id del nuevo objeto en la base de datos.
            getEntityManager().flush();
            
            Gson gson = null;
            if (entity instanceof Transaccion) {
                gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            } else {
                gson = new Gson();
            }
            
            String jsonObject = gson.toJson(entity);
           
            r.setCodigo(RespuestaGeneral.CODIGO_OK);
            r.setMensaje(RespuestaGeneral.MENSAJE_OK);
            r.setObjeto(jsonObject);
            
        } catch (PersistenceException e) {
            e.printStackTrace();
            r.setCodigo(RespuestaGeneral.CODIGO_ERROR);
            r.setMensaje(e.getMessage());
            r.setObjeto(null);
        } catch (Exception e) {
            e.printStackTrace();
            r.setCodigo(RespuestaGeneral.CODIGO_ERROR);
            r.setMensaje(e.getMessage());
            r.setObjeto(null);
        }   
        return r;
    }

    public void edit(T entity) {        
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().flush();
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
