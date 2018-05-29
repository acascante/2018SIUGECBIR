/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jairo.cisneros
 */

@Entity(name = "SolicitudSalida")
@Table(name = "SIGEBI_OAF.SIGB_SOLICITUD_SALIDA")
@PrimaryKeyJoinColumn(name = "ID_SOLICITUD", referencedColumnName = "ID_SOLICITUD")
@DiscriminatorValue("5")
public class SolicitudSalida extends Solicitud {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO_SOLICITUD", referencedColumnName = "ID_TIPO")
    private Tipo tipo;    

    @ManyToOne
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "NUM_PERSONA")
    private Persona persona;    

    @Column(name = "FECHA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">

    public SolicitudSalida() {
        super();
    }

    public SolicitudSalida(UnidadEjecutora  unidadEjecutora, Estado estado) {
        super(Constantes.DISCRIMINATOR_SOLICITUD_SALIDA, unidadEjecutora, estado);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
   
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    //</editor-fold> 

}
