/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.framework.vista.util.PaginacionOracle;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Persona;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudSalida;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author jairo.cisneros
 */
public class SolicitudSalidaCommand {

    //<editor-fold defaultstate="collapsed" desc="BienCommand">

    public class BienCommandSolicitudSalida extends PaginacionOracle {

        //---------------------------------------------------------
        //Filtros bien
        //---------------------------------------------------------
        private String fltBienIdentificacion = "";
        private String fltBienDescripcion = "";
        private String fltBienMarca = "";
        private String fltBienModelo = "";
        private String fltBienSerie = "";

        private List<Bien> bienes;

        private BienCommandSolicitudSalida() {
            super();
            ArrayList<SelectItem> cantPorPaginas = new ArrayList<SelectItem>();
            cantPorPaginas.add(new SelectItem(5, "5"));
            cantPorPaginas.add(new SelectItem(10, "10"));
            this.setListaRegistrosPagina(cantPorPaginas);
        }

        //<editor-fold defaultstate="collapsed" desc="GET's y SET's">

        public List<Bien> getBienes() {
            return bienes;
        }

        public void setBienes(List<Bien> bienes) {
            this.bienes = bienes;
        }

        public String getFltBienIdentificacion() {
            return fltBienIdentificacion;
        }

        public void setFltBienIdentificacion(String fltBienIdentificacion) {
            this.fltBienIdentificacion = fltBienIdentificacion;
        }

        public String getFltBienDescripcion() {
            return fltBienDescripcion;
        }

        public void setFltBienDescripcion(String fltBienDescripcion) {
            this.fltBienDescripcion = fltBienDescripcion;
        }

        public String getFltBienMarca() {
            return fltBienMarca;
        }

        public void setFltBienMarca(String fltBienMarca) {
            this.fltBienMarca = fltBienMarca;
        }

        public String getFltBienModelo() {
            return fltBienModelo;
        }

        public void setFltBienModelo(String fltBienModelo) {
            this.fltBienModelo = fltBienModelo;
        }

        public String getFltBienSerie() {
            return fltBienSerie;
        }

        public void setFltBienSerie(String fltBienSerie) {
            this.fltBienSerie = fltBienSerie;
        }
        //</editor-fold>
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PersonaCommand">

    public class PersonaCommandSolicitudSalida extends PaginacionOracle {

        //---------------------------------------------------------
        //Filtros bien
        //---------------------------------------------------------
        private String fltPersonaIdentificacion = "";
        private String fltPersonaNombre = "";

        private List<Persona> personas;

        private PersonaCommandSolicitudSalida() {
            super();
            ArrayList<SelectItem> cantPorPaginas = new ArrayList<SelectItem>();
            cantPorPaginas.add(new SelectItem(5, "5"));
            cantPorPaginas.add(new SelectItem(10, "10"));
            this.setListaRegistrosPagina(cantPorPaginas);
        }

        //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
        public String getFltPersonaIdentificacion() {
            return fltPersonaIdentificacion;
        }

        public void setFltPersonaIdentificacion(String fltPersonaIdentificacion) {
            this.fltPersonaIdentificacion = fltPersonaIdentificacion;
        }

        public String getFltPersonaNombre() {
            return fltPersonaNombre;
        }

        public void setFltPersonaNombre(String fltPersonaNombre) {
            this.fltPersonaNombre = fltPersonaNombre;
        }

        public List<Persona> getPersonas() {
            return personas;
        }

        public void setPersonas(List<Persona> personas) {
            this.personas = personas;
        }
        
        //</editor-fold>
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    //---------------------------------------------------------
    //Datos de la pantalla
    //---------------------------------------------------------
    private SolicitudSalida solicitudSalida = null;
    private String mensajeConfirmacion = "";
    private String observacionConfirmacion = "";

    //Mapa para la lista de los bienes incluidos en la solicitud
    private HashMap<Long, SolicitudDetalle> bienesSalidas = null;
    
    //---------------------------------------------------------
    //Mapas y selectItem para la busqueda de los datos
    //---------------------------------------------------------
    private Tipo idTipoNacional = null;
    private Tipo idTipoInternacional = null;
    private Long idTipo = 0L;

    //Se define la accion a realizar
    private Integer accion = 0;
    private Boolean presentarPanel = false;
    private Boolean yaRegistrada = false;
    
    private BienCommandSolicitudSalida bienCommand;

    private PersonaCommandSolicitudSalida personaCommand;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public SolicitudSalidaCommand(UnidadEjecutora unidadEjecutora, Estado estadoSolicitud, Tipo idTipoNacional, Tipo idTipoInternacional) {
        super();
        this.initGeneral(idTipoNacional, idTipoInternacional);

        this.solicitudSalida = new SolicitudSalida(unidadEjecutora, estadoSolicitud);
        this.solicitudSalida.setTipo(idTipoNacional);
        this.solicitudSalida.setPersona(new Persona());
        this.yaRegistrada = false;
    }

    public SolicitudSalidaCommand(SolicitudSalida solicitudSalida, Tipo idTipoNacional, Tipo idTipoInternacional) {
        super();
        this.initGeneral(idTipoNacional, idTipoInternacional);

        this.solicitudSalida = solicitudSalida;
        this.idTipo = solicitudSalida.getTipo().getId();
        this.yaRegistrada = true;
    }

    private void initGeneral(Tipo idTipoNacional, Tipo idTipoInternacional) {

        //Para incluir nuevos registros en las listas
        this.bienCommand = new BienCommandSolicitudSalida();
        this.personaCommand = new PersonaCommandSolicitudSalida();
        
        this.idTipoInternacional = idTipoInternacional;
        this.idTipoNacional = idTipoNacional;
        this.idTipo = idTipoNacional.getId();
        
        //Se crean los mapas
        this.bienesSalidas = new HashMap<Long, SolicitudDetalle>();
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public SolicitudSalida getSolicitudSalida() {
        return solicitudSalida;
    }

    public void setSolicitudSalida(SolicitudSalida solicitudSalida) {
        this.solicitudSalida = solicitudSalida;
    }

    public BienCommandSolicitudSalida getBienCommand() {
        return bienCommand;
    }

    public void setBienCommand(BienCommandSolicitudSalida bienCommand) {
        this.bienCommand = bienCommand;
    }

    public String getMensajeConfirmacion() {
        return mensajeConfirmacion;
    }

    public PersonaCommandSolicitudSalida getPersonaCommand() {
        return personaCommand;
    }

    public void setPersonaCommand(PersonaCommandSolicitudSalida personaCommand) {
        this.personaCommand = personaCommand;
    }

    public void setMensajeConfirmacion(String mensajeConfirmacion) {
        this.mensajeConfirmacion = mensajeConfirmacion;
    }

    public String getObservacionConfirmacion() {
        return observacionConfirmacion;
    }

    public void setObservacionConfirmacion(String observacionConfirmacion) {
        this.observacionConfirmacion = observacionConfirmacion;
    }

    public HashMap<Long, SolicitudDetalle> getBienesSalidas() {
        return bienesSalidas;
    }

    public void setBienesSalidas(HashMap<Long, SolicitudDetalle> bienesSalidas) {
        this.bienesSalidas = bienesSalidas;
    }

    public Boolean getYaRegistrada() {
        return yaRegistrada;
    }

    public void setYaRegistrada(Boolean yaRegistrada) {
        this.yaRegistrada = yaRegistrada;
    }

    public Tipo getIdTipoNacional() {
        return idTipoNacional;
    }

    public void setIdTipoNacional(Tipo idTipoNacional) {
        this.idTipoNacional = idTipoNacional;
    }

    public Tipo getIdTipoInternacional() {
        return idTipoInternacional;
    }

    public void setIdTipoInternacional(Tipo idTipoInternacional) {
        this.idTipoInternacional = idTipoInternacional;
    }

    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public Integer getAccion() {
        return accion;
    }

    public void setAccion(Integer accion) {
        this.accion = accion;
    }

    public Boolean getPresentarPanel() {
        return presentarPanel;
    }

    public void setPresentarPanel(Boolean presentarPanel) {
        this.presentarPanel = presentarPanel;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public Boolean getPresentarPanelBuscarPersonas() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_SALIDA_BUSCAR_PERSONA);
    }

    public Boolean getPresentarPanelBuscarBienes() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_SALIDA_BUSCAR_BIEN);
    }

    public Boolean getPresentarPanelAnularConfirmar() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_SALIDA_ANULAR);
    }

    public Boolean getPresentarPanelRechazarConfirmar() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_SALIDA_RECHAZAR);
    }
    
    public Boolean getPresentarPanelCorregirConfirmar() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_SALIDA_CORREGIR);
    }
    
    public ArrayList<SolicitudDetalle> getBienesDetalles() {
        return new ArrayList<SolicitudDetalle>(this.bienesSalidas.values());
    }

    public Boolean getPermiteEliminarDetalles() {
        return yaRegistrada &&  (this.solicitudSalida.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_SALIDA_CORRECION) || 
                this.solicitudSalida.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_SALIDA_NUEVO));
    }

    public Boolean getPermiteAprobarRechazarDetalles() {
        return yaRegistrada && this.solicitudSalida.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_SALIDA_PROCESO);
    }

    public Boolean getPermiteAplicarSolicitud() {
        return yaRegistrada && (this.solicitudSalida.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_SALIDA_CORRECION) || 
                this.solicitudSalida.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_SALIDA_NUEVO));
    }

    public Boolean getPermiteGuardarSolicitud() {
        return (this.solicitudSalida.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_SALIDA_CORRECION) || 
                this.solicitudSalida.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_SALIDA_NUEVO));
    }

    public Boolean getPermiteProcesarSolicitud() {
        return yaRegistrada && this.solicitudSalida.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_SALIDA_PROCESO);
    }

    //</editor-fold>
}
