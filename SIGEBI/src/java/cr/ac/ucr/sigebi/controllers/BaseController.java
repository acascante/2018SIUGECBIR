/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.seguridad.entidades.SegUsuario;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.vista.VistaUsuario;
import cr.ac.ucr.framework.vista.util.PaginacionOracle;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerBase")
@Scope("session")
public class BaseController extends PaginacionOracle {

    //<editor-fold defaultstate="collapsed" desc="Constantes">
    Integer estadoPendiente;// = Constantes.ESTADO_BIEN_PENDIENTE;
    Integer estadoPendienteSincronizar;// = Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    VistaUsuario lVistaUsuario;
    String nombreUnidad;
    String codPersonaReg;
    String usuarioRegistrado;
    String vistaOrigen;
    String vistaActual;
    UnidadEjecutora unidadEjecutora;

    List<Estado> estadosGenerales;
    List<Tipo> tiposGenerales;

    Map<Integer, Estado> tiposBienes;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public String getVistaActual() {
        return vistaActual;
    }

    public void setVistaActual(String vistaActual) {
        this.vistaActual = vistaActual;
    }

    public Integer getEstadoPendiente() {
        return estadoPendiente;
    }

    public Integer getEstadoPendienteSincronizar() {
        return estadoPendienteSincronizar;
    }

    public VistaUsuario getlVistaUsuario() {
        return lVistaUsuario;
    }

    public void setlVistaUsuario(VistaUsuario lVistaUsuario) {
        this.lVistaUsuario = lVistaUsuario;
    }

    public String getVistaOrigen() {
        return vistaOrigen;
    }

    public void setVistaOrigen(String vistaOrigen) {
        this.vistaOrigen = vistaOrigen;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getCodPersonaReg() {
        return codPersonaReg;
    }

    public void setCodPersonaReg(String codPersonaReg) {
        this.codPersonaReg = codPersonaReg;
    }

    public String getUsuarioRegistrado() {
        return usuarioRegistrado;
    }

    public void setUsuarioRegistrado(String usuarioRegistrado) {
        this.usuarioRegistrado = usuarioRegistrado;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public BaseController() {
        estadoPendiente = Constantes.ESTADO_BIEN_PENDIENTE;
        estadoPendienteSincronizar = Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR;
        incializaBienes();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    //@PostConstruct
    private void incializaBienes() {
        lVistaUsuario = (VistaUsuario) Util.obtenerVista("#{vistaUsuario}");

        //Obtener el id de la unidad ejecutora
        unidadEjecutora = lVistaUsuario.getUnidadEjecutoraSIGEBI();

        //Obtener usuario
        SegUsuario usuario = lVistaUsuario.getgUsuarioActual();
        codPersonaReg = usuario.getIdUsuario();
        usuarioRegistrado = usuario.getNombre_completo();

        ArrayList<SelectItem> cantPorPaginas = new ArrayList<SelectItem>();
        cantPorPaginas.add(new SelectItem(5, "5"));
        cantPorPaginas.add(new SelectItem(10, "10"));
        cantPorPaginas.add(new SelectItem(25, "25"));
        cantPorPaginas.add(new SelectItem(50, "50"));
        this.setListaRegistrosPagina(cantPorPaginas);

        //Se asigna la lista de estados y tipos
        estadosGenerales = lVistaUsuario.getEstadosSIGEBI();
        tiposGenerales = lVistaUsuario.getTiposSIGEBI();
    }

    public List<Estado> estadosPorDominio(final String dominio) {
        return this.estadosFilter(estadosGenerales, new PredicateFilter() {
            @Override
            public boolean verifica(Object t) {
                return dominio.equals(((Estado) t).getDominio());
            }
        });
    }

    public Estado estadoPorDominioValor(final String dominio, final Integer valor) {
        return this.estadosFilter(estadosGenerales, new PredicateFilter() {
            @Override
            public boolean verifica(Object t) {
                return dominio.equals(((Estado) t).getDominio()) && valor.equals(((Estado) t).getValor());
            }
        }).get(0);
    }

    public List<Tipo> tiposPorDominio(final String dominio) {
        return this.tiposFilter(tiposGenerales, new PredicateFilter() {
            @Override
            public boolean verifica(Object t) {
                return dominio.equals(((Tipo) t).getDominio());
            }
        });
    }

    public Tipo tipoPorDominioValor(final String dominio, final Integer valor) {
        return this.tiposFilter(tiposGenerales, new PredicateFilter() {
            @Override
            public boolean verifica(Object t) {
                return dominio.equals(((Tipo) t).getDominio()) && valor.equals(((Tipo) t).getValor());
            }
        }).get(0);
    }

    public List<Tipo> tiposFilter(List<Tipo> tipos, PredicateFilter tester) {
        List<Tipo> resultado = new ArrayList();
        for (Tipo p : tipos) {
            if (tester.verifica(p)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public List<Estado> estadosFilter(List<Estado> estados, PredicateFilter tester) {
        List<Estado> resultado = new ArrayList();
        for (Estado p : estados) {
            if (tester.verifica(p)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public interface PredicateFilter {
        boolean verifica(Object p);
    }
}