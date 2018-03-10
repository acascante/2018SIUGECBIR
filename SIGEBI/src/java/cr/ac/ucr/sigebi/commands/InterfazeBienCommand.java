/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.InterfazAccesorio;
import cr.ac.ucr.sigebi.domain.InterfazAdjunto;
import cr.ac.ucr.sigebi.domain.InterfazBien;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jairo.cisneros
 */
public class InterfazeBienCommand {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    //---------------------------------------------------------
    //Datos de la pantalla
    //---------------------------------------------------------
    InterfazBien interfazBien;

    //Mapa para la lista de adjuntos para la solicitud
    HashMap<Long, InterfazAdjunto> adjuntos = null;

    //Mapa para la lista de adjuntos para la solicitud
    HashMap<Long, InterfazAccesorio> accesorios = null;

    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public InterfazeBienCommand() {
        super();
        this.initGeneral();
    }

    public InterfazeBienCommand(InterfazBien interfazBien) {
        super();
        this.initGeneral();

        this.interfazBien = interfazBien;
    }

    private void initGeneral() {

        //Para incluir nuevos registros en las listas
        this.interfazBien = new InterfazBien();

        //Se crean los mapas
        this.adjuntos = new HashMap<Long, InterfazAdjunto>();
        this.accesorios = new HashMap<Long, InterfazAccesorio>();
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public InterfazBien getInterfazBien() {
        return interfazBien;
    }

    public void setInterfazBien(InterfazBien interfazBien) {
        this.interfazBien = interfazBien;
    }

    public HashMap<Long, InterfazAdjunto> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(HashMap<Long, InterfazAdjunto> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public HashMap<Long, InterfazAccesorio> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(HashMap<Long, InterfazAccesorio> accesorios) {
        this.accesorios = accesorios;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
   
    public ArrayList<InterfazAdjunto> getAdjuntosList() {
        return new ArrayList<InterfazAdjunto>(this.adjuntos.values());
    }

    public ArrayList<InterfazAccesorio> getAccesoriosList() {
        return new ArrayList<InterfazAccesorio>(this.accesorios.values());
    }
    //</editor-fold>
}
