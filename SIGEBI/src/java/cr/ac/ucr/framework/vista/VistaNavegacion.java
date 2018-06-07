/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.framework.vista;

import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.vista.util.MenuItemPrincipal;
import cr.ac.ucr.framework.vista.util.Util;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/*
* @author Adrián Zamora Villalta
 */
@Controller(value = "vistaNavegacion")
@Scope("session")
public class VistaNavegacion {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Variable que tiene la ruta de todas las páginas del sistema y su
     * respectivo identificador, cuando se necesite navegar, se va a buscar por
     * el identificardor y ésta retornorá la página a la cual se debe navegar
     */
    private HashMap gPaginas;
    //variable que mantiene el string con la página que se le muestra al usuario
    private String gPaginaSeleccionada;
    private String gPaginaAnterior;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos">
    /**
     * inicializa las variables de la vista
     */
    @PostConstruct
    private void inicializar() {
        gPaginas = new HashMap();
        gPaginaSeleccionada = "";

        //Opciones del menú  
        gPaginas.put(Constantes.KEY_VISTA_LISTAR_BIENES, Constantes.VISTA_LISTAR_BIENES);
        gPaginas.put(Constantes.KEY_VISTA_DETALLE_BIEN, Constantes.VISTA_DETALLE_BIEN);
        gPaginas.put(Constantes.KEY_VISTA_DETALLE_BIEN_DONACION, Constantes.VISTA_DETALLE_BIEN_DONACION);
        gPaginas.put(Constantes.KEY_VISTA_DETALLE_BIEN_INTERFAZ, Constantes.VISTA_DETALLE_BIEN_INTERFAZ);

        gPaginas.put(Constantes.VISTA_SINCRONIZACION_BIEN, Constantes.VISTA_SINCRONIZACION_BIEN_LISTADO);

        gPaginas.put(Constantes.VISTA_NOTIFICACION_LISTADO, Constantes.VISTA_NOTIFICACION_LISTADO_PAGINA);
        gPaginas.put(Constantes.VISTA_NOTIFICACION_NUEVA, Constantes.VISTA_NOTIFICACION_NUEVA_PAGINA);

        gPaginas.put(Constantes.VISTA_CONVENIO_LISTADO, Constantes.VISTA_CONVENIO_LISTADO_PAGINA);
        gPaginas.put(Constantes.VISTA_CONVENIO_NUEVO, Constantes.VISTA_CONVENIO_NUEVO_PAGINA);

        gPaginas.put(Constantes.VISTA_EXCLUSION_LISTADO, Constantes.VISTA_MANTENIMIENTO_LISTADO_PAGINA);
        gPaginas.put(Constantes.VISTA_EXCLUSION_NUEVA, Constantes.VISTA_MANTENIMIENTO_NUEVA_PAGINA);

        gPaginas.put(Constantes.VISTA_MANTENIMIENTO_LISTADO, Constantes.VISTA_MANTENIMIENTO_LISTADO_PAGINA);
        gPaginas.put(Constantes.VISTA_MANTENIMIENTO_NUEVO, Constantes.VISTA_MANTENIMIENTO_NUEVA_PAGINA);

        gPaginas.put(Constantes.VISTA_PRESTAMO_LISTADO, Constantes.VISTA_PRESTAMO_LISTADO_PAGINA);
        gPaginas.put(Constantes.VISTA_PRESTAMO_NUEVO, Constantes.VISTA_PRESTAMO_NUEVO_PAGINA);

        gPaginas.put(Constantes.VISTA_RECEPCION_PRESTAMO_LISTADO, Constantes.VISTA_RECEPCION_PRESTAMO_LISTADO_PAGINA);
        gPaginas.put(Constantes.VISTA_RECEPCION_PRESTAMO_NUEVO, Constantes.VISTA_RECEPCION_PRESTAMO_NUEVO_PAGINA);
        
        gPaginas.put(Constantes.KEY_VISTA_TIPOS, Constantes.KEY_VISTA_TIPOS_PAGINA);
        
        gPaginas.put(Constantes.VISTA_INFORME_TECNICO, Constantes.VISTA_INFORME_TECNICO_LISTADO);
        gPaginas.put(Constantes.VISTA_INFORME_TECNICO_DET, Constantes.VISTA_INFORME_TECNICO_DETALLE);

        //Gestion de procesos 
        gPaginas.put(Constantes.VISTA_PROCESOS, Constantes.VISTA_PROCESO_DIRECCION);

        //ACTAS
        gPaginas.put(Constantes.KEY_VISTA_ACTA, Constantes.VISTA_ACTA);
        gPaginas.put(Constantes.KEY_VISTA_LISTAR_ACTAS, Constantes.VISTA_LISTAR_ACTAS);

        //TRASLADOS
        gPaginas.put(Constantes.KEY_VISTA_TRASLADOS_LISTAR, Constantes.VISTA_TRASLADOS_LISTAR);
        gPaginas.put(Constantes.KEY_VISTA_TRASLADO_DETALLE, Constantes.VISTA_TRASLADO_DETALLE);
        gPaginas.put(Constantes.KEY_VISTA_TRASLADO_PERMISOS, Constantes.VISTA_TRASLADO_PERMISOS);

        //Donaciones
        gPaginas.put(Constantes.KEY_VISTA_SOLICITUD_DONACION_LISTADO, Constantes.VISTA_SOLICITUD_DONACION_LISTADO);
        gPaginas.put(Constantes.KEY_VISTA_SOLICITUD_DONACION_DETALLE, Constantes.VISTA_SOLICITUD_DONACION_DETALLE);

        //Salidas
        gPaginas.put(Constantes.KEY_VISTA_SOLICITUD_SALIDA_LISTADO, Constantes.VISTA_SOLICITUD_SALIDA_LISTADO);
        gPaginas.put(Constantes.KEY_VISTA_SOLICITUD_SALIDA_DETALLE, Constantes.VISTA_SOLICITUD_SALIDA_DETALLE);

        //REPORTES
        gPaginas.put(Constantes.KEY_VISTA_REPORTE_TRASLADO, Constantes.VISTA_REPORTE_TRASLADO);
        gPaginas.put(Constantes.KEY_VISTA_REPORTE_MOVIMIENTOS, Constantes.VISTA_REPORTE_MOVIMIENTOS);
        gPaginas.put(Constantes.KEY_VISTA_REPORTE_BIENES, Constantes.VISTA_REPORTE_BIENES);
        gPaginas.put(Constantes.KEY_REPORTE_INVENT_FALTANTES, Constantes.VISTA_REPORTE_INVENT_FALTANTES);
        gPaginas.put(Constantes.KEY_REPORTE_SOBRANTES, Constantes.VISTA_REPORTE_SOBRANTES);

        //Interfaz Bien
        gPaginas.put(Constantes.KEY_VISTA_INTERFAZ_BIEN_LISTADO, Constantes.VISTA_INTERFAZ_BIEN_LISTADO);
        gPaginas.put(Constantes.KEY_VISTA_INTERFAZ_BIEN_DETALLE, Constantes.VISTA_INTERFAZ_BIEN_DETALLE);

        //Inventarios
        gPaginas.put(Constantes.KEY_VISTA_LISTAR_TOMA_FISICA, Constantes.VISTA_LISTAR_TOMA_FISICA);
        gPaginas.put(Constantes.KEY_VISTA_TOMA_FISICA_DETALLE, Constantes.VISTA_TOMA_FISICA_DETALLE);

        //Asignacion placas
        gPaginas.put(Constantes.KEY_VISTA_LISTAR_ASIGNACION_PLACA, Constantes.VISTA_LISTAR_ASIGNACION_PLACA);
        gPaginas.put(Constantes.KEY_VISTA_ASIGNACION_PLACA_DETALLE, Constantes.VISTA_ASIGNACIONPLACA_DETALLE);

        gPaginas.put("inicio", "../inicio.xhtml");

        //Ubicaciones
        gPaginas.put(Constantes.KEY_VISTA_UBICACION, Constantes.VISTA_UBICACION);
        
        //Asignar Responsable Bienes
        gPaginas.put(Constantes.KEY_VISTA_ASIGNA_RESPONSABLE, Constantes.VISTA_ASIGNA_RESPONSABLE);
        
        //Mis Bienes
        gPaginas.put(Constantes.KEY_VISTA_MIS_BIENES, Constantes.VISTA_MIS_BIENES);
        

    }//fin del método inicializar

    /**
     * metodo que intoduce la navegacion al sistema por medio de la
     * identificacion de base de datos del sistema de seguridad
     *
     * @param event que genera al usuario al seleccionar un elemento del menú
     */
    public void navegar(ActionEvent event) {
        if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
        } else {
            MenuItemPrincipal temp = (MenuItemPrincipal) event.getSource();
            if (temp.getRegla_navegacion() != null && temp.getRegla_navegacion().compareTo("") != 0) {

                Object lReglaNavegacion = gPaginas.get(temp.getRegla_navegacion());
                if (lReglaNavegacion != null) {
                    if (!gPaginaSeleccionada.equals(lReglaNavegacion.toString())) {
                        Util.removerVistasSesion();
                    }
                    gPaginaAnterior = gPaginaSeleccionada;
                    gPaginaSeleccionada = lReglaNavegacion.toString();
                }//if
            }//else
        }//if
    }//fin método navegar

    public void navegacionBotones(String regla) {
        gPaginaAnterior = gPaginaSeleccionada;
        gPaginaSeleccionada = gPaginas.get(regla).toString();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Sets y Gets">
    public String getgPaginaAnterior() {
        return gPaginaAnterior;
    }

    public void setgPaginaAnterior(String gPaginaAnterior) {
        this.gPaginaAnterior = gPaginaAnterior;
    }

    public String getgPaginaSeleccionada() {
        return gPaginaSeleccionada;
    }

    public void setgPaginaSeleccionada(String gPaginaSeleccionada) {
        this.gPaginaSeleccionada = gPaginaSeleccionada;
    }

    public HashMap getgPaginas() {
        return gPaginas;
    }

    public void setgPaginas(HashMap gPaginas) {
        this.gPaginas = gPaginas;
    }
    // </editor-fold>
}//fin de la clase
