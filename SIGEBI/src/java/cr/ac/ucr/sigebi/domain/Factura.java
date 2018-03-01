/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "Factura")
@Table(name = "SIGEBI_OAF.SIGB_FACTURA")
@SequenceGenerator(name = "SGB_SQ_FACTURAS", sequenceName = "SIGEBI_OAF.SGB_SQ_FACTURAS", initialValue = 1, allocationSize = 1)
public class Factura extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_FACTURAS")
    @Column(name = "ID_FACTURA")
    private Long id;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "FECHA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "ID_SOLICITUD_DONACION", referencedColumnName = "ID_SOLICITUD")
    private SolicitudDonacion solicitudDonacion;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Factura() {
    }

    public Factura(String numero, Date fecha, SolicitudDonacion solicitudDonacion) {
        this.numero = numero;
        this.fecha = fecha;
        this.solicitudDonacion = solicitudDonacion;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public SolicitudDonacion getSolicitudDonacion() {
        return solicitudDonacion;
    }

    public void setSolicitudDonacion(SolicitudDonacion solicitudDonacion) {
        this.solicitudDonacion = solicitudDonacion;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Factura other = (Factura) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    //</editor-fold>
}
