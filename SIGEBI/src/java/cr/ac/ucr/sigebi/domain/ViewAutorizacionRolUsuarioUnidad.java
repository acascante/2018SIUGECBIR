/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

/**
 *
 * @author alvaro.cascante
 */
import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity(name = "ViewAutorizacionRolUsuarioUnidad")
@Table(name = "SIGEBI_OAF.V_SIGB_UNID_USUA_AUTO_ROL_PERS")
public class ViewAutorizacionRolUsuarioUnidad extends ObjetoBase implements Serializable {    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "ID_AUTORIZACION_ROL", referencedColumnName = "ID_AUTORIZACION_ROL")
    private AutorizacionRol autorizacionRol;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Usuario usuarioSeguridad;

    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora unidadEjecutora;

    @Column(name = "TIENE_AUTO_PERSONA")
    private Boolean tieneAutorizacionRol;
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AutorizacionRol getAutorizacionRol() {
        return autorizacionRol;
    }

    public void setAutorizacionRol(AutorizacionRol autorizacionRol) {
        this.autorizacionRol = autorizacionRol;
    }

    public Usuario getUsuarioSeguridad() {
        return usuarioSeguridad;
    }

    public void setUsuarioSeguridad(Usuario usuarioSeguridad) {
        this.usuarioSeguridad = usuarioSeguridad;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public Boolean getTieneAutorizacionRol() {
        return tieneAutorizacionRol;
    }

    public void setTieneAutorizacionRol(Boolean tieneAutorizacionRol) {
        this.tieneAutorizacionRol = tieneAutorizacionRol;
    }

    //</editor-fold>
}
