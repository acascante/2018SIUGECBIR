/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

/**
 *
 * @author alvaro.cascante
 */
public class ListarPersonasCommand {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private String fltId;
    private String fltPrimerApellido;
    private String fltSegundoApellido;
    private String fltNombre;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarPersonasCommand() {
        super();
        this.fltId =  new String();
        this.fltPrimerApellido =  new String();
        this.fltSegundoApellido =  new String();
        this.fltNombre =  new String();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public Long getFltIdCodigo() {
        if (this.fltId.isEmpty()) {
            return 0L; 
        }
        return Long.valueOf(fltId);
    }
    
    public String getFltId() {
        return fltId;
    }

    public void setFltId(String fltId) {
        this.fltId = fltId;
    }

    public String getFltPrimerApellido() {
        return fltPrimerApellido;
    }

    public void setFltPrimerApellido(String fltPrimerApellido) {
        this.fltPrimerApellido = fltPrimerApellido;
    }

    public String getFltSegundoApellido() {
        return fltSegundoApellido;
    }

    public void setFltSegundoApellido(String fltSegundoApellido) {
        this.fltSegundoApellido = fltSegundoApellido;
    }

    public String getFltNombre() {
        return fltNombre;
    }

    public void setFltNombre(String fltNombre) {
        this.fltNombre = fltNombre;
    }
    //</editor-fold>
}