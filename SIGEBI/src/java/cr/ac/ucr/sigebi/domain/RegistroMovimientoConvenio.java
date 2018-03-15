/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.sigebi.utils.Constantes;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "RegistroMovimientoConvenio")
@Table(name = "SIGEBI_OAF.SIGB_REGISTRO_MOVI_CONVENIO")
@DiscriminatorValue("4")
@PrimaryKeyJoinColumn(name = "ID_REGISTRO_MOVIMIENTO", referencedColumnName = "ID_REGISTRO_MOVIMIENTO")
public class RegistroMovimientoConvenio extends RegistroMovimiento implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @ManyToOne
    @JoinColumn(name = "ID_CONVENIO", referencedColumnName = "ID_CONVENIO")
    private Convenio convenio;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public RegistroMovimientoConvenio() {
    }

    public RegistroMovimientoConvenio(Tipo tipo, String observacion, Integer telefono, Date fecha, Usuario usuario, Estado estado, Convenio convenio) {
        super(tipo, observacion, telefono, fecha, usuario, estado, Constantes.DISCRIMINATOR_REGISTRO_MOVIMIENTO_CONVENIO);
        this.convenio = convenio;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }
    //</editor-fold>
}