package ucu.deliverit.backcore.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "delivery")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Delivery.findAll", query = "SELECT d FROM Delivery d"),
    @NamedQuery(name = "Delivery.findById", query = "SELECT d FROM Delivery d WHERE d.id = :id"),
    @NamedQuery(name = "Delivery.findBySucursal", query = "SELECT DISTINCT d FROM Delivery d JOIN d.viajeCollection v WHERE v.sucursal.id = :idSucursal"),
    @NamedQuery(name = "Delivery.findAllSinViajesEnProceso", query = "SELECT d FROM Delivery d WHERE d.id NOT IN (SELECT v.delivery.id FROM Viaje v WHERE v.estado.id = 3)"),
    @NamedQuery(name = "Delivery.findByCalificacion", query = "SELECT d FROM Delivery d WHERE d.calificacion = :calificacion"),
    @NamedQuery(name = "Delviery.findAllSinViajesEnProceso", query = "SELECT d FROM Delivery d WHERE d.id NOT IN (SELECT v.delivery.id FROM Viaje v WHERE v.estado.id = 3)"),
    @NamedQuery(name = "Delviery.findByUsuario", query = "SELECT d FROM Delivery d WHERE d.usuario.id = :idUsuario")})
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "calificacion")
    private Short calificacion;
    
    @Size(max = 200)
    @Column(name = "token")
    private String token;
    
    @JoinColumn(name = "vehiculo", referencedColumnName = "id")
    @ManyToOne
    private Vehiculo vehiculo;
    
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuario;
    
    private String nombre;
    
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "ubicacion")
    private Ubicacion ubicacion;
    
    @OneToMany(mappedBy = "delivery")
    private Collection<Viaje> viajeCollection;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Short calificacion) {
        this.calificacion = calificacion;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    @XmlTransient
    public Collection<Viaje> getViajeCollection() {
        return viajeCollection;
    }

    public void setViajeCollection(Collection<Viaje> viajeCollection) {
        this.viajeCollection = viajeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Delivery)) {
            return false;
        }
        Delivery other = (Delivery) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucu.deliverit.backcore.entidades.Delivery[ id=" + id + " ]";
    }    
}