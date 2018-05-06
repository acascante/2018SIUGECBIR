/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Tipo;

/**
 *
 * @author alvaro.cascante
 */
public class TipoCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long id;
    private String nombre;
    private String dominio;
    private Integer valor;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public TipoCommand() { 
        super();
    }
    
    public TipoCommand(Tipo tipo) {
        this();
        this.id = tipo.getId();
        this.nombre = tipo.getNombre();
        this.dominio = tipo.getDominio();
        this.valor = tipo.getValor();
    }    
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public Tipo getTipo() { 
        Tipo tipo = new Tipo();
        tipo.setNombre(this.nombre);
        tipo.setDominio(this.dominio);
        tipo.setValor(this.valor);
        return tipo;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
    //</editor-fold>
}