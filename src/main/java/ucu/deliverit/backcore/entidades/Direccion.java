package ucu.deliverit.backcore.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "direccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direccion.findAll", query = "SELECT d FROM Direccion d"),
    @NamedQuery(name = "Direccion.findById", query = "SELECT d FROM Direccion d WHERE d.id = :id"),
    @NamedQuery(name = "Direccion.findByCalle", query = "SELECT d FROM Direccion d WHERE d.calle = :calle"),
    @NamedQuery(name = "Direccion.findByNroPuerta", query = "SELECT d FROM Direccion d WHERE d.nroPuerta = :nroPuerta"),
    @NamedQuery(name = "Direccion.findByEsquina", query = "SELECT d FROM Direccion d WHERE d.esquina = :esquina"),
    @NamedQuery(name = "Direccion.findByLatitud", query = "SELECT d FROM Direccion d WHERE d.latitud = :latitud"),
    @NamedQuery(name = "Direccion.findByLongitud", query = "SELECT d FROM Direccion d WHERE d.longitud = :longitud")})
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "calle")
    private String calle;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "nro_puerta")
    private Short nroPuerta;
    
    @Column(name = "apartamento")
    private Short apartamento;
    
    @Size(max = 20)
    @Column(name = "esquina")
    private String esquina;
    
    @Column(name = "latitud")
    private Double latitud;
    
    @Column(name = "longitud")
    private Double longitud;
    
//    @OneToMany(mappedBy = "direccion")
//    private Collection<Sucursal> sucursalCollection;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Short getNroPuerta() {
        return nroPuerta;
    }

    public void setNroPuerta(Short nroPuerta) {
        this.nroPuerta = nroPuerta;
    }

    public String getEsquina() {
        return esquina;
    }

    public void setEsquina(String esquina) {
        this.esquina = esquina;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Short getApartamento() {
        return apartamento;
    }

    public void setApartamento(Short apartamento) {
        this.apartamento = apartamento;
    }

//    @XmlTransient
//    public Collection<Sucursal> getSucursalCollection() {
//        return sucursalCollection;
//    }
//
//    public void setSucursalCollection(Collection<Sucursal> sucursalCollection) {
//        this.sucursalCollection = sucursalCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Direccion)) {
            return false;
        }
        Direccion other = (Direccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucu.deliverit.backcore.entidades.Direccion[ id=" + id + " ]";
    }
    
}
