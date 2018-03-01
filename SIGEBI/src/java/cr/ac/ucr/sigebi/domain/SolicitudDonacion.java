/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author jairo.cisneros
 */

@Entity(name = "SolicitudDonacion")
@Table(name = "SIGEBI_OAF.SIGB_SOLICITUD_DONACION")
@PrimaryKeyJoinColumn(name = "ID_SOLICITUD", referencedColumnName = "ID_SOLICITUD")
@DiscriminatorValue("2")
public class SolicitudDonacion extends Solicitud {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @ManyToOne
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    private Tipo tipoDonacion;

    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora unidadReceptora;

    @Column(name = "DONANTE")
    private String donante;

    @ManyToOne
    @JoinColumn(name = "ID_PAIS", referencedColumnName = "ID")
    private Pais pais;

    @Column(name = "CIUDAD")
    private String ciudad;

    @Transient
    private List<Adjunto> adjuntos;

    @Transient
    private List<Factura> facturas;

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">

    public SolicitudDonacion() {
        super();
    }

    public SolicitudDonacion(UnidadEjecutora  unidadEjecutora) {
        super(Constantes.DISCRIMINATOR_SOLICITUD_DONACION, unidadEjecutora);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Tipo getTipoDonacion() {
        return tipoDonacion;
    }

    public void setTipoDonacion(Tipo tipoDonacion) {
        this.tipoDonacion = tipoDonacion;
    }

    public UnidadEjecutora getUnidadReceptora() {
        return unidadReceptora;
    }

    public void setUnidadReceptora(UnidadEjecutora unidadReceptora) {
        this.unidadReceptora = unidadReceptora;
    }

    public String getDonante() {
        return donante;
    }

    public void setDonante(String donante) {
        this.donante = donante;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<Adjunto> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<Adjunto> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    //</editor-fold> 
    
}
