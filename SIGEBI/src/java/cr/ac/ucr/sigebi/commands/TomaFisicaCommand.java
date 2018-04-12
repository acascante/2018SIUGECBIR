/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.framework.vista.util.PaginacionOracle;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Categoria;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Lote;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.TomaFisica;
import cr.ac.ucr.sigebi.domain.TomaFisicaLote;
import cr.ac.ucr.sigebi.domain.TomaFisicaSobrante;
import cr.ac.ucr.sigebi.domain.TomaFisicaUnitaria;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author jairo.cisneros
 */
public class TomaFisicaCommand {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    private TomaFisica tomaFisica;
    private Ubicacion ubicacion;
    private TomaFisicaUnitariaCommand tomaFisicaUnitariaCommand;
    private TomaFisicaLoteCommand tomaFisicaLoteCommand;
    private TomaFisicaSobranteCommand tomaFisicaSobranteCommand;
    
    List<SelectItem> tiposOptions;
    List<SelectItem> tiposMotivoOptions;
    List<SelectItem> loteOptions;
    List<SelectItem> categoriaOptions;
    List<SelectItem> subCategoriaOptions;
    List<SelectItem> clasificacionOptions;
    List<SelectItem> subClasificacionOptions;
    List<SelectItem> ubicacionOptions;

    List<TomaFisicaUnitaria> tomasFisicasUnitarias;
    List<TomaFisicaLote> tomasFisicasLotes;
    List<TomaFisicaSobrante> tomasFisicasSobrantes;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public TomaFisicaCommand() {
        this.tomaFisica = new TomaFisica();
        this.tomaFisica.setTipo(new Tipo());
        this.tomaFisica.setMotivo(new Tipo());
        this.ubicacion = new Ubicacion();
    }

    public TomaFisicaCommand(TomaFisica tomaFisica) {
        this.tomaFisica = tomaFisica;        
        this.ubicacion = tomaFisica.getUbicacion() != null ? tomaFisica.getUbicacion() : new Ubicacion();
        this.ubicacion.setIdTemporal(tomaFisica.getUbicacion() != null ? tomaFisica.getUbicacion().getIdTemporal() : -1L);        
        tomaFisica.getTipo().setIdTemporal(tomaFisica.getTipo().getId());
        tomaFisica.getMotivo().setIdTemporal(tomaFisica.getMotivo().getId());
        this.tomaFisicaUnitariaCommand = new TomaFisicaUnitariaCommand();
        this.tomaFisicaLoteCommand = new TomaFisicaLoteCommand();
        this.tomaFisicaSobranteCommand = new TomaFisicaSobranteCommand();
    }

    public TomaFisica prepararTomaFisica(Estado estado) {
        if (this.ubicacion != null && this.getUbicacion().getIdTemporal() > 0) {
            tomaFisica.setUbicacion(ubicacion);
        } else {
            tomaFisica.setUbicacion(null);
        }

        if (estado != null) {
            tomaFisica.setEstado(estado);
        }
        return tomaFisica;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get and Set">
    public TomaFisica getTomaFisica() {
        return tomaFisica;
    }

    public void setTomaFisica(TomaFisica tomaFisica) {
        this.tomaFisica = tomaFisica;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<SelectItem> getTiposOptions() {
        return tiposOptions;
    }

    public void setTiposOptions(List<SelectItem> tiposOptions) {
        this.tiposOptions = tiposOptions;
    }

    public List<SelectItem> getTiposMotivoOptions() {
        return tiposMotivoOptions;
    }

    public void setTiposMotivoOptions(List<SelectItem> tiposMotivoOptions) {
        this.tiposMotivoOptions = tiposMotivoOptions;
    }

    public List<SelectItem> getLoteOptions() {
        return loteOptions;
    }

    public void setLoteOptions(List<SelectItem> loteOptions) {
        this.loteOptions = loteOptions;
    }

    public List<SelectItem> getCategoriaOptions() {
        return categoriaOptions;
    }

    public void setCategoriaOptions(List<SelectItem> categoriaOptions) {
        this.categoriaOptions = categoriaOptions;
    }

    public List<SelectItem> getUbicacionOptions() {
        return ubicacionOptions;
    }

    public void setUbicacionOptions(List<SelectItem> ubicacionOptions) {
        this.ubicacionOptions = ubicacionOptions;
    }

    public TomaFisicaUnitariaCommand getTomaFisicaUnitariaCommand() {
        return tomaFisicaUnitariaCommand;
    }

    public void setTomaFisicaUnitariaCommand(TomaFisicaUnitariaCommand tomaFisicaUnitariaCommand) {
        this.tomaFisicaUnitariaCommand = tomaFisicaUnitariaCommand;
    }

    public List<TomaFisicaUnitaria> getTomasFisicasUnitarias() {
        return tomasFisicasUnitarias;
    }

    public void setTomasFisicasUnitarias(List<TomaFisicaUnitaria> tomasFisicasUnitarias) {
        this.tomasFisicasUnitarias = tomasFisicasUnitarias;
    }

    public List<TomaFisicaLote> getTomasFisicasLotes() {
        return tomasFisicasLotes;
    }

    public void setTomasFisicasLotes(List<TomaFisicaLote> tomasFisicasLotes) {
        this.tomasFisicasLotes = tomasFisicasLotes;
    }

    public List<TomaFisicaSobrante> getTomasFisicasSobrantes() {
        return tomasFisicasSobrantes;
    }

    public void setTomasFisicasSobrantes(List<TomaFisicaSobrante> tomasFisicasSobrantes) {
        this.tomasFisicasSobrantes = tomasFisicasSobrantes;
    }

    public TomaFisicaLoteCommand getTomaFisicaLoteCommand() {
        return tomaFisicaLoteCommand;
    }

    public void setTomaFisicaLoteCommand(TomaFisicaLoteCommand tomaFisicaLoteCommand) {
        this.tomaFisicaLoteCommand = tomaFisicaLoteCommand;
    }

    public TomaFisicaSobranteCommand getTomaFisicaSobranteCommand() {
        return tomaFisicaSobranteCommand;
    }

    public void setTomaFisicaSobranteCommand(TomaFisicaSobranteCommand tomaFisicaSobranteCommand) {
        this.tomaFisicaSobranteCommand = tomaFisicaSobranteCommand;
    }

    public List<SelectItem> getSubCategoriaOptions() {
        return subCategoriaOptions;
    }

    public void setSubCategoriaOptions(List<SelectItem> subCategoriaOptions) {
        this.subCategoriaOptions = subCategoriaOptions;
    }

    public List<SelectItem> getClasificacionOptions() {
        return clasificacionOptions;
    }

    public void setClasificacionOptions(List<SelectItem> clasificacionOptions) {
        this.clasificacionOptions = clasificacionOptions;
    }

    public List<SelectItem> getSubClasificacionOptions() {
        return subClasificacionOptions;
    }

    public void setSubClasificacionOptions(List<SelectItem> subClasificacionOptions) {
        this.subClasificacionOptions = subClasificacionOptions;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Commands">
    public class TomaFisicaUnitariaCommand extends PaginacionOracle{

        //<editor-fold defaultstate="collapsed" desc="Atributos">
        //Filtros
        private String fltId;
        private String fltDescripcion;
        private String fltIdentificacion;

        //Campos bien
        Bien bien;
        String identificacionBusqueda;

        //</editor-fold>            
        
        //<editor-fold defaultstate="collapsed" desc="Constructor">
        public TomaFisicaUnitariaCommand() {
            bien = new Bien();
            identificacionBusqueda = "";
            fltId = "";
            fltDescripcion = "";
            fltIdentificacion = "";
        }
        //</editor-fold>            

        //<editor-fold defaultstate="collapsed" desc="Get and Set">
        public String getFltId() {
            return fltId;
        }

        public void setFltId(String fltId) {
            this.fltId = fltId;
        }

        public String getFltDescripcion() {
            return fltDescripcion;
        }

        public void setFltDescripcion(String fltDescripcion) {
            this.fltDescripcion = fltDescripcion;
        }

        public String getFltIdentificacion() {
            return fltIdentificacion;
        }

        public void setFltIdentificacion(String fltIdentificacion) {
            this.fltIdentificacion = fltIdentificacion;
        }

        public Bien getBien() {
            return bien;
        }

        public void setBien(Bien bien) {
            this.bien = bien;
        }

        public String getIdentificacionBusqueda() {
            return identificacionBusqueda;
        }

        public void setIdentificacionBusqueda(String identificacionBusqueda) {
            this.identificacionBusqueda = identificacionBusqueda;
        }
        //</editor-fold>            
    }
    
    public class TomaFisicaLoteCommand extends PaginacionOracle{

        //<editor-fold defaultstate="collapsed" desc="Atributos">
        
        //Filtros
        private String fltId;
        private String fltLote;

        //Campos bien
        Lote lote;
        Long cantidad = 0L;
        
        //</editor-fold>            
        
        //<editor-fold defaultstate="collapsed" desc="Constructor">
        public TomaFisicaLoteCommand() {
            lote = new Lote();
            fltLote = "";
            fltId = "";
            cantidad = 0L;
        }
        //</editor-fold>            

        //<editor-fold defaultstate="collapsed" desc="Get and Set">
        public String getFltLote() {
            return fltLote;
        }

        public void setFltLote(String fltLote) {
            this.fltLote = fltLote;
        }

        public String getFltId() {
            return fltId;
        }

        public void setFltId(String fltId) {
            this.fltId = fltId;
        }

        public Lote getLote() {
            return lote;
        }

        public void setLote(Lote lote) {
            this.lote = lote;
        }

        public Long getCantidad() {
            return cantidad;
        }

        public void setCantidad(Long cantidad) {
            this.cantidad = cantidad;
        }
        //</editor-fold>            
    }
    
    public class TomaFisicaSobranteCommand extends PaginacionOracle{

        //<editor-fold defaultstate="collapsed" desc="Atributos">
        
        //Filtros
        private String fltId;
        private String fltIdentificacion;
        private String fltDescripcion;
        private String fltSerie;
        private String fltMarca;
        private String fltModelo;

        //Campos bien
        Categoria categoria;
        SubCategoria subCategoria;
        Clasificacion clasificacion;
        SubClasificacion subClasificacion;
        Ubicacion ubicacion;
        
        TomaFisicaSobrante tomaFisicaSobrante;
        
        //</editor-fold>            
        
        //<editor-fold defaultstate="collapsed" desc="Constructor y Metodos">
        public TomaFisicaSobranteCommand() {
            ubicacion = new Ubicacion();
            subClasificacion = new SubClasificacion();
            clasificacion = new Clasificacion();
            subCategoria = new SubCategoria();
            categoria = new Categoria();
            tomaFisicaSobrante = new TomaFisicaSobrante();
            
            fltSerie = "";
            fltDescripcion = "";
            fltIdentificacion = "";
            fltId = "";
        }
        
        public TomaFisicaSobrante prepararTomaFisicaSobrante(TomaFisicaSobrante tomaFisicaSobrante, TomaFisica tomaFisica) {
            
            TomaFisicaSobrante resultado = null;
            
            if(tomaFisicaSobrante != null){
                resultado = tomaFisicaSobrante; 
                resultado.setMarca(this.tomaFisicaSobrante.getMarca());
                resultado.setModelo(this.tomaFisicaSobrante.getModelo());
                resultado.setSerie(this.tomaFisicaSobrante.getSerie());
                resultado.setDescripcion(this.tomaFisicaSobrante.getDescripcion());
            }else{
                resultado = this.tomaFisicaSobrante;
            }
            resultado.setTomaFisica(tomaFisica);

            if (this.subCategoria != null && this.subCategoria.getIdTemporal() > 0) {
                resultado.setSubCategoria(this.subCategoria);
            }else{
                resultado.setSubCategoria(null);
            }
            if (this.subClasificacion != null && this.subClasificacion.getIdTemporal() > 0) {
                resultado.setSubClasificacion(this.subClasificacion);
            }else{
                resultado.setSubClasificacion(null);
            }
            if (this.getUbicacion() != null && this.getUbicacion().getIdTemporal() > 0) {
                resultado.setUbicacion(this.getUbicacion());
            }else{
                resultado.setUbicacion(null);
            }
            return resultado;
        }
        
        //</editor-fold>            

        //<editor-fold defaultstate="collapsed" desc="Get and Set">
        public String getFltId() {
            return fltId;
        }

        public void setFltId(String fltId) {
            this.fltId = fltId;
        }

        public String getFltMarca() {
            return fltMarca;
        }

        public void setFltMarca(String fltMarca) {
            this.fltMarca = fltMarca;
        }

        public String getFltModelo() {
            return fltModelo;
        }

        public void setFltModelo(String fltModelo) {
            this.fltModelo = fltModelo;
        }

        public String getFltIdentificacion() {
            return fltIdentificacion;
        }

        public void setFltIdentificacion(String fltIdentificacion) {
            this.fltIdentificacion = fltIdentificacion;
        }

        public String getFltDescripcion() {
            return fltDescripcion;
        }

        public void setFltDescripcion(String fltDescripcion) {
            this.fltDescripcion = fltDescripcion;
        }

        public String getFltSerie() {
            return fltSerie;
        }

        public void setFltSerie(String fltSerie) {
            this.fltSerie = fltSerie;
        }

        public Categoria getCategoria() {
            return categoria;
        }

        public void setCategoria(Categoria categoria) {
            this.categoria = categoria;
        }

        public SubCategoria getSubCategoria() {
            return subCategoria;
        }

        public void setSubCategoria(SubCategoria subCategoria) {
            this.subCategoria = subCategoria;
        }

        public Clasificacion getClasificacion() {
            return clasificacion;
        }

        public void setClasificacion(Clasificacion clasificacion) {
            this.clasificacion = clasificacion;
        }

        public SubClasificacion getSubClasificacion() {
            return subClasificacion;
        }

        public void setSubClasificacion(SubClasificacion subClasificacion) {
            this.subClasificacion = subClasificacion;
        }

        public Ubicacion getUbicacion() {
            return ubicacion;
        }

        public void setUbicacion(Ubicacion ubicacion) {
            this.ubicacion = ubicacion;
        }

        public TomaFisicaSobrante getTomaFisicaSobrante() {
            return tomaFisicaSobrante;
        }

        public void setTomaFisicaSobrante(TomaFisicaSobrante tomaFisicaSobrante) {
            this.tomaFisicaSobrante = tomaFisicaSobrante;
        }
               
        //</editor-fold>            
    }

    //</editor-fold>
    
    
}
