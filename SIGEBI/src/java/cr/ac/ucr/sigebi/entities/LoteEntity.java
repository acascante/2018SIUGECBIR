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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "LoteEntity")
@Table(name = "SF_CATEGORIA_LOTE")
public class LoteEntity extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Basic(optional = false)
    @Column(name = "COD_CATEGORIA_LOTE")
    private String codCategLote;//
    
    @Id
    @Basic(optional = false)
    @Column(name = "NUM_EMPRESA")
    private int numEmpresa;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "USUARIO_CREACION")
    private String usrCrea;
    
    @Column(name = "USUARIO_MODIFICACION")
    private String usrModifica;
    
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecCrea;
    
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecModifica;
    
    //</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Constructor">
    
    public LoteEntity() {
    }
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Gets & Sets">
    
    public String getCodCategLote() {
        return codCategLote;
    }

    public void setCodCategLote(String codCategLote) {
        this.codCategLote = codCategLote;
    }

    public int getNumEmpresa() {
        return numEmpresa;
    }

    public void setNumEmpresa(int numEmpresa) {
        this.numEmpresa = numEmpresa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsrCrea() {
        return usrCrea;
    }

    public void setUsrCrea(String usrCrea) {
        this.usrCrea = usrCrea;
    }

    public String getUsrModifica() {
        return usrModifica;
    }

    public void setUsrModifica(String usrModifica) {
        this.usrModifica = usrModifica;
    }

    public Date getFecCrea() {
        return fecCrea;
    }

    public void setFecCrea(Date fecCrea) {
        this.fecCrea = fecCrea;
    }

    public Date getFecModifica() {
        return fecModifica;
    }
    public void setFecModifica(Date fecModifica) {
        this.fecModifica = fecModifica;
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCategLote != null ? codCategLote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoteEntity)) {
            return false;
        }
        LoteEntity other = (LoteEntity) object;
        if ((this.codCategLote == null && other.codCategLote != null) || (this.codCategLote != null && !this.codCategLote.equals(other.codCategLote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.LoteEntity[Lote=" + descripcion + "]";
    }
    
    //</editor-fold>
    
}
