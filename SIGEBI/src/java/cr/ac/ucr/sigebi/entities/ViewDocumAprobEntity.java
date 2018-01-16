/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jxl.write.DateTime;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "ViewDocumAprobEntity")
@Table(name = "SIGEBI_OAF.V_SGB_DOCUMENTO")
public class ViewDocumAprobEntity extends ObjetoBase implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @Basic(optional = true)
    @Column(name = "ID_DOCUMENTO") // INTEGER 
    private Integer  idDocumento;
    
    @Id 
    @Column(name = "ID_REFERENCIA") // NUMBER consecutivo o id de cada documento
    private Integer  idReferencia;
    
    @Column(name = "DOCUMENTO_NOMBRE") // VARCHAR2 (200 Byte) 
    private String documNombre;
    
    @Id
    @Basic(optional = false)
    @Column(name = "ROL_ID") // NUMBER 
    private Integer  rolId;
    @Column(name = "ROL_NOMBRE") // VARCHAR2 (200 Byte) 
    private String rolNombre;
    
    
    @JoinColumn(name = "ESTADO_DOCUMENTO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private EstadoEntity idEstado;
    
    @Column(name = "FECHA_CAMB_ESTADO") // TIMESTAMP(6) 
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @Column(name = "USUARIO") // VARCHAR2 (30 Byte) 
    private String usuario;

    //</editor-fold> 
    
    
    
    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    public String getDocumNombre() {
        return documNombre;
    }

    public void setDocumNombre(String documNombre) {
        this.documNombre = documNombre;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public EstadoEntity getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(EstadoEntity idEstado) {
        this.idEstado = idEstado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    //</editor-fold> 

}
