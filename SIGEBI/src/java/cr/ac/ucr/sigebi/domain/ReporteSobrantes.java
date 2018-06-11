/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.io.Serializable;

/**
 *
 * @author alvaro.cascante
 */
public class ReporteSobrantes implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private String identificacion;
    private String funcionario;
    private String descripcion;
    private String marca;
    private String modelo;
    private String serie;
    private String subClasificacion; //*
    private String subCategoria;
    private String unidadEjecutora;
    private String ubicacion;
    private String estado;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteSobrantes() {
        super();
    }

    public ReporteSobrantes(TomaFisicaSobrante bienSobrante) {
        this.identificacion = bienSobrante.getIdentificacion();
        this.funcionario = "Pendiente Definir"; 
        this.descripcion = bienSobrante.getDescripcion();
        this.marca = bienSobrante.getMarca();
        this.modelo = bienSobrante.getModelo();
        this.serie = bienSobrante.getSerie();
        this.subClasificacion = getDescSubClasificacion(bienSobrante.getSubClasificacion());
        this.subCategoria = getDescSubCategoria(bienSobrante.getSubCategoria());
            this.unidadEjecutora = getDescUnidad(bienSobrante.getTomaFisica());
        this.ubicacion = bienSobrante.getUbicacion();
    }
    
    public ReporteSobrantes(TomaFisicaSobrante bienSobrante, Estado estado) {
        this(bienSobrante);
        this.estado = estado.getNombre();
    }
    //</editor-fold>
    
    private String getDescUnidad(TomaFisica tomaFisica) {
        if (tomaFisica != null && tomaFisica.getUnidadEjecutora() != null) {
            return tomaFisica.getUnidadEjecutora().getDescripcion();
        }
        return null;
    }
    
    private String getDescSubClasificacion(SubClasificacion sc) {
        if(sc != null && sc.getClasificacion() != null)
            return sc.getClasificacion().getNombre();
        return null;
    }
    
    private String getDescSubCategoria(SubCategoria sc) {
        if(sc != null && sc.getCategoria()!= null)
            return sc.getCategoria().getDescripcion();
        return null;
    }
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getSubClasificacion() {
        return subClasificacion;
    }

    public void setSubClasificacion(String subClasificacion) {
        this.subClasificacion = subClasificacion;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }

    public String getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(String unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    //</editor-fold>
}