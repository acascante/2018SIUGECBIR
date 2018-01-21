/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.BienCaracteristica;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.Moneda;
import cr.ac.ucr.sigebi.domain.Proveedor;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.models.AccesorioModel;
import cr.ac.ucr.sigebi.models.AdjuntoBienModel;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.CategModel;
import cr.ac.ucr.sigebi.models.ClasificacionModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.MonedaModel;
import cr.ac.ucr.sigebi.models.NotaModel;
import cr.ac.ucr.sigebi.models.ProveedorModel;
import cr.ac.ucr.sigebi.models.SubCategoriaModel;
import cr.ac.ucr.sigebi.models.SubClasificacionModel;
import cr.ac.ucr.sigebi.models.UbicacionModel;
import cr.ac.ucr.sigebi.entities.AccesoriosEntity;
import cr.ac.ucr.sigebi.entities.AdjuntoBienEntity;
import cr.ac.ucr.sigebi.entities.CategEntity;
import cr.ac.ucr.sigebi.entities.ClasificacionEntity;
import cr.ac.ucr.sigebi.entities.DatoBienEntity;
import cr.ac.ucr.sigebi.entities.MonedaEntity;
import cr.ac.ucr.sigebi.entities.NotaEntity;
import cr.ac.ucr.sigebi.entities.PersonaEntity;
import cr.ac.ucr.sigebi.entities.ProveedorEntity;
import cr.ac.ucr.sigebi.entities.SubCategoriaEntity;
import cr.ac.ucr.sigebi.entities.SubClasificacionEntity;
import cr.ac.ucr.sigebi.entities.UbicacionEntity;
import cr.ac.ucr.sigebi.entities.ViewBienEntity;
import cr.ac.ucr.sigebi.models.TipoModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Integer.parseInt;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jorge.serrano
 */
@Controller(value = "controllerBienes")
@Scope("session")
public class BienController extends BaseController{

    //<editor-fold defaultstate="collapsed" desc="Variables de la Clase">
    TipoController tipoBienCont;

    // comboBox tiposBienes
    List<Bien> bienes;

    //<editor-fold defaultstate="collapsed" desc="Variables Listados>
    List<SelectItem> tiposBienOptions;

    // comboBox Origenes
    List<SelectItem> origenOptions;

    // comboBox clasificacion
    List<SelectItem> clasificacionOptions;

    // comboBox sub clasificacion
    List<SelectItem> subClasifOptions;

    // comboBox categorias
    List<SelectItem> categoriasOptions;

    // comboBox subCategorias
    List<SelectItem> subCategoriaOptions;

    // comboBox subCategorias
    List<SelectItem> ubicacionOptions;

    // comboBox subCategorias
    List<SelectItem> provedooresOptions;

    // comboBox subCategorias
    List<SelectItem> estadosOptions;

    // comboBox subCategorias
    List<SelectItem> monedasOptions;

    // comboBox Lotes
    List<SelectItem> loteOptions;

    Bien bien;// = new ProveedorEntity();
    NotaEntity nota;
    List<NotaEntity> notas;

    //NotaEntity catacteristica;
    String valorLote;

    Date fecAdiquisicion;
    String tipoSeleccionado;
    String origenSeleccionado;

    String selectCategoria;
    String selectSubCateg;
    String selectClasificacion;
    String selectSubClasif;

    String idSelectUbicacion;
    String selectUbicacion;
    String selectProveedor;
    String selectEstado;
    String selectMoneda;

    List<PersonaEntity> proveedores;
    String provSelccionado;
    String provId;

    String capitalizable;

    String mensaje;
    String mensajeNota;

    String constOrigenes;

    float montoCapitalizable;
    
    boolean esLote;
    
    boolean ubicacionVisible;
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Inicializa Datos">
    public BienController() {
        
        super();
        
        tipoBienCont = new TipoController();
        bienes = new ArrayList<Bien>();
        bien = new Bien();
        esLote = false;

        tiposBienOptions = new ArrayList<SelectItem>();
        subClasifOptions = new ArrayList<SelectItem>();
        subCategoriaOptions = new ArrayList<SelectItem>();
        categoriasOptions = new ArrayList<SelectItem>();

        notas = new ArrayList<NotaEntity>();
        mensajeNota = "";
        nota = new NotaEntity();

        capitalizable = "--";
        eliminarNotaVisible = false;

        constOrigenes = "ORIGEN";
        adjuntoDescripcion = "";
        vistaOrigen = "";

    }

    @PostConstruct
    private void incializaBienes() {

    }

    public void cargarOrigenes() {
        List<Tipo> origenes = tipoModel.listarPorDominio(constOrigenes);

        origenOptions = new ArrayList<SelectItem>();
        for (Tipo item : origenes) {
            origenOptions.add(new SelectItem("" + item.getIdTipo(), item.getNombre()));
        }
        //mensajeCaracteristicas = "Debo llenar el select de características";
    }

    public void cargarLotes() {
//        List<LoteEntity> lotes = bienMod.getLotes();
//
//        loteOptions = new ArrayList<SelectItem>();
//        for (LoteEntity item : lotes) {
//            loteOptions.add(new SelectItem("" + item.getCodCategLote(), item.getDescripcion()));
//        }
    }

    private void cargarCombos() {
        try {
            mostrarAdjunto = false;
            ubicacionVisible = false;
            
            //Aqui traemos los tipos de bienes para los Select
            List<Tipo> tipoEntity = tipoModel.listarPorDominio("TIPO");
            tiposBienOptions = new ArrayList<SelectItem>();
            for (Tipo item : tipoEntity) {
                tiposBienOptions.add(new SelectItem("" + item.getIdTipo(), item.getNombre()));
            }

            proveedores = new ArrayList<PersonaEntity>();
//            proveedores = provModel.listar();

            //Cargar Categorias
//            List<CategEntity> categorias;
//            categorias = categoriaMod.traerTodo();
//            categoriasOptions = new ArrayList<SelectItem>();
//            for (CategEntity item : categorias) {
//                categoriasOptions.add(getCategoriaOption(item));
//            }
//
//            ubicacionOptions = new ArrayList<SelectItem>();
//            List<UbicacionEntity> ubicaciones;
//            ubicaciones = ubicModel.traerTodo();
//            for (UbicacionEntity item : ubicaciones) {
//                ubicacionOptions.add(new SelectItem(item.getIdUbicacion() + "#" + item.getDetalle().replace("#", "-"), item.getDetalle().replace("#", "-")));
//            }

            estadosOptions = new ArrayList<SelectItem>();
            List<Estado> estados;
            // TODO verificar el dominio de estos estados
            estados = estadoModel.listar();
            for (Estado item : estados) {
                estadosOptions.add(new SelectItem("" + item.getId(), item.getNombre()));
            }

//            monedasOptions = new ArrayList<SelectItem>();
//            List<MonedaEntity> monedas;
//            monedas = monedaModel.traerTodo();
//            for (MonedaEntity item : monedas) {
//                monedasOptions.add(new SelectItem("" + item.getIdMoneda(), item.getDescripcion()));
//            }

            cargarOrigenes();
            cargarLotes();

        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }

    float tipoCambioDollar = 570;
    float tipoCambioEuro = 640;

    private void limpiarDatosBien(){
        try{
            
            
            
            //bien = new Bien();
            tipoSeleccionado = "-1";
            origenSeleccionado = "-1";
            capitalizable = "--";
            selectEstado = "2";

            
            valorLote = "-1";
            esLote = false;
            if(bien.getCantidad() == null 
                    || bien.getCantidad() < 1)
                bien.setCantidad(1);
            
            selectCategoria = "-1";
            selectSubCateg = "-1";

            selectClasificacion = "-1";
            selectSubClasif = "-1";
            
            selectUbicacion = "-1";
            ubicacionId = "";
            ubicacionNombre = "";
            provId = "";
            provSelccionado = "";
            
            fecAdiquisicion = null;

            selectMoneda = "-1";
            
            //Limpiamos caracteristicas
            mensajeCaracteristicas = "";
            selectCaracteristica = "-1";
            descCaracteristica = "";
            caracteristica = new DatoBienEntity();
            caracteristicas = new ArrayList<DatoBienEntity>();
            notas = new ArrayList<NotaEntity>();
            
            //Limpiamos la Garantía
            garantiaMensajeExito = "";
            garantiaMensajeError = "";
            garantiaFecIni = null;
            garantiaFecFin = null;
            garantiaDesc = "";
            
            //limpiar las Notas
            mensajeNota= "";
            notaDetalle = "";
            notas = new ArrayList<NotaEntity>();
            
            //limpiar Archivos
            mensajeAdjuntosExito = "";
            mensajeAdjuntos = "";
            adjuntoDescripcion = "";
            adjuntos = new ArrayList<AdjuntoBienEntity>();
            
            //limpiar Accesorios
            mensajeAccesError = "";
            mensajeAccesExito = "";
            accesorio = new AccesoriosEntity();
            
            mensaje = "";
            mensajeExito = "";
            
        }
        catch(Exception err){
            
        }
    }
    
    private void cargarDatosBien() {
        try {
            
            limpiarDatosBien();
            
            tipoSeleccionado = bien.getTipoBien().toString();
            origenSeleccionado = bien.getOrigen().toString();
            capitalizable = bien.getCapitalizable().equals(1) ? "SI" : "NO";
            selectEstado = bien.getEstado().getId().toString();

            esLote = bien.getNumeroLote()!= null ;
            if(esLote)
                valorLote = bien.getNumeroLote();

            //String[] subCateg = ;

            String categ;
            String subCat;
            
            if (bien.getSubCategoria() != null) {
                categ = bien.getSubCategoria().getCodigoCategoria();
                //cargar Subcategorias según la categoría.
                cargaComboSubCategoria(categ);
                
                subCat = bien.getSubCategoria().getCodigoSubCategoria();
                cargaComboClasificacion(subCat);
                
                selectCategoria = categ;
                selectSubCateg = subCat;
            }

            Integer subClasif = bien.getSubClasificacion().getId();
            //Se cargan las SubClasificaciones y sub categorias por el código de SubCategoria
            //selectCategoria = 
            SubClasificacion subClasificacion = bien.getSubClasificacion();//subClasifModel.obtenerValor(subClasif);

            if (subClasificacion != null) {
                cargaComboSubClasificacion(subClasificacion.getId().toString());
                selectClasificacion = subClasificacion.getClasificacion().getId().toString();
                selectSubClasif = subClasificacion.getId().toString();
            }
            
            if(bien.getUbicacion()!= null){
                ubicacionId = bien.getUbicacion().getId().toString();
                ubicacionNombre = bien.getUbicacion().getDetalle();
            }

            
            Proveedor prov;
            if (bien.getProveedor() != null) {
//                prov = provModel.buscarPorId(bien.getProveedor().getId());
//
//                provId = bien.getProveedor().toString();
//                provSelccionado = prov.getId().getNombre();
//                if (prov.getId().getPrimerApellido() != null) {
//                    provSelccionado += " " + prov.getId().getPrimerApellido();
//                }
//            }

            if (bien.getFechaAdquisicion() != null) {
                fecAdiquisicion = bien.getFechaAdquisicion();
            }

            if (bien.getMoneda() != null) {
                selectMoneda = bien.getMoneda().getId().toString();
            }
            
            if ( (bien.getNumeroLote() == null) || (bien.getNumeroLote().equals("-1") ) )
                valorLote = "-1";
            else
                valorLote = bien.getNumeroLote().toString();
            
            
            cargarCaracteristica();
            cargarGarantia();
            notas = notaModel.traerTodo( Integer.parseInt( bien.getId().toString() ));
            cargarAdjuntos();
//            accesorios = modelAccesorio.listarPorBien(bien);
            

        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }

    private Double getValorColones() {
        try {
            if (bien.getMoneda().getId() == 2L) {
                return bien.getCosto() * tipoCambioDollar;
            }
            if (bien.getMoneda().getId() == 3L) {
                return bien.getCosto() * tipoCambioEuro;
            }
        } catch (Exception err) {
            mensaje = err.getMessage();

        }
        return bien.getCosto();
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">

    
    
    public boolean isUbicacionVisible() {
        return ubicacionVisible;
    }

    public void setUbicacionVisible(boolean ubicacionVisible) {
        this.ubicacionVisible = ubicacionVisible;
    }
    
    public String getIdSelectUbicacion() {
        return idSelectUbicacion;
    }

    public void setIdSelectUbicacion(String idSelectUbicacion) {
        this.idSelectUbicacion = idSelectUbicacion;
    }
    
    public void setLoteOptions(List<SelectItem> LoteOptions) {
        this.loteOptions = LoteOptions;
    }

    public List<SelectItem> getLoteOptions() {
        return loteOptions;
    }

    public String getValorLote() {
        return valorLote;
    }

    public void setValorLote(String valorLote) {
        this.valorLote = valorLote;
    }

    public List<SelectItem> getOrigenOptions() {
        return origenOptions;
    }

    public void setOrigenOptions(List<SelectItem> origenOptions) {
        this.origenOptions = origenOptions;
    }

    public List<PersonaEntity> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<PersonaEntity> proveedores) {
        this.proveedores = proveedores;
    }

    public String getProvSelccionado() {
        return provSelccionado;
    }

    public void setProvSelccionado(String provSelccionado) {
        this.provSelccionado = provSelccionado;
    }

    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public String getSelectUbicacion() {
        return selectUbicacion;
    }

    public void setSelectUbicacion(String selectUbicacion) {
        this.selectUbicacion = selectUbicacion;
    }

    public String getSelectProveedor() {
        return selectProveedor;
    }

    public void setSelectProveedor(String selectProveedor) {
        this.selectProveedor = selectProveedor;
    }

    public String getSelectEstado() {
        return selectEstado;
    }

    public void setSelectEstado(String selectEstado) {
        this.selectEstado = selectEstado;
    }

    public String getSelectMoneda() {
        return selectMoneda;
    }

    public void setSelectMoneda(String selectMoneda) {
        this.selectMoneda = selectMoneda;
    }

    public List<SelectItem> getUbicacionOptions() {
        return ubicacionOptions;
    }

    public void setUbicacionOptions(List<SelectItem> ubicacionOptions) {
        this.ubicacionOptions = ubicacionOptions;
    }

    public List<SelectItem> getProvedooresOptions() {
        return provedooresOptions;
    }

    public void setProvedooresOptions(List<SelectItem> provedooresOptions) {
        this.provedooresOptions = provedooresOptions;
    }

    public List<SelectItem> getEstadosOptions() {
        return estadosOptions;
    }

    public void setEstadosOptions(List<SelectItem> estadosOptions) {
        this.estadosOptions = estadosOptions;
    }

    public List<SelectItem> getMonedasOptions() {
        return monedasOptions;
    }

    public void setMonedasOptions(List<SelectItem> monedasOptions) {
        this.monedasOptions = monedasOptions;
    }

    public List<SelectItem> getClasificacionOptions() {
        return clasificacionOptions;
    }

    public void setClasificacionOptions(List<SelectItem> clasificacionOptions) {
        this.clasificacionOptions = clasificacionOptions;
    }

    public String getSelectClasificacion() {
        return selectClasificacion;
    }

    public void setSelectClasificacion(String selectClasificacion) {
        this.selectClasificacion = selectClasificacion;
    }

    public String getSelectSubCateg() {
        return selectSubCateg;
    }

    public void setSelectSubCateg(String selectSubCateg) {
        this.selectSubCateg = selectSubCateg;
    }

    public String getSelectSubClasif() {
        return selectSubClasif;
    }

    public void setSelectSubClasif(String selectSubClasif) {
        this.selectSubClasif = selectSubClasif;
    }

    public String getSelectCategoria() {
        return selectCategoria;
    }

    public void setSelectCategoria(String selectCategoria) {
        this.selectCategoria = selectCategoria;
    }

    public List<SelectItem> getCategoriasOptions() {
        return categoriasOptions;
    }

    public void setCategoriasOptions(List<SelectItem> categoriasOptions) {
        this.categoriasOptions = categoriasOptions;
    }

    public List<SelectItem> getSubClasifOptions() {
        return subClasifOptions;
    }

    public void setSubClasifOptions(List<SelectItem> subClasifOptions) {
        this.subClasifOptions = subClasifOptions;
    }

    public List<SelectItem> getSubCategoriaOptions() {
        return subCategoriaOptions;
    }

    public void setSubCategoriaOptions(List<SelectItem> subCategoriaOptions) {
        this.subCategoriaOptions = subCategoriaOptions;
    }

    public String getCapitalizable() {
        return capitalizable;
    }

    public void setCapitalizable(String capitalizable) {
        this.capitalizable = capitalizable;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<NotaEntity> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaEntity> notas) {
        this.notas = notas;
    }

    public NotaEntity getNota() {
        return nota;
    }

    public void setNota(NotaEntity nota) {
        this.nota = nota;
    }

//    public NotaEntity getCatacteristica() {
//        return catacteristica;
//    }
//
//    public void setCatacteristica(NotaEntity catacteristica) {
//        this.catacteristica = catacteristica;
//    }
    public String getOrigenSeleccionado() {
        return origenSeleccionado;
    }

    public void setOrigenSeleccionado(String origenSeleccionado) {
        this.origenSeleccionado = origenSeleccionado;
    }

    public List<SelectItem> getTiposBienOptions() {
        return tiposBienOptions;
    }

    public void setTiposBienOptions(List<SelectItem> tiposBienOptions) {
        this.tiposBienOptions = tiposBienOptions;
    }

    public Date getFecAdiquisicion() {
        return fecAdiquisicion;
    }

    public void setFecAdiquisicion(Date fecAdiquisicion) {
        this.fecAdiquisicion = fecAdiquisicion;
    }

    public boolean isEsLote() {
        return esLote;
    }

    public void setEsLote(boolean esLote) {
        this.esLote = esLote;
    }

    
    
    
    public String getTipoSeleccionado() {
        return tipoSeleccionado;
    }

    public void setTipoSeleccionado(String tipoSeleccionado) {
        this.tipoSeleccionado = tipoSeleccionado;
    }

    public List<Bien> getBienes() {
        return this.bienes;
    }

    public void setBienes(List<Bien> bienes) {
        this.bienes = bienes;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public String getMensajeNota() {
        return mensajeNota;
    }

    public void setMensajeNota(String mensajeNota) {
        this.mensajeNota = mensajeNota;
    }

    // </editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Acceso a Datos">
    @Resource
    private CategModel categoriaMod;

    @Resource
    private ClasificacionModel clasifMod;

    @Resource
    private BienModel bienMod;

    @Resource
    private TipoModel tipoModel;

    @Resource
    private SubClasificacionModel subClasifModel;

    @Resource
    private SubCategoriaModel subCategModel;

    @Resource
    private NotaModel notaModel;

    @Resource
    private ProveedorModel provModel;

    @Resource
    private UbicacionModel ubicModel;

    @Resource
    private EstadoModel estadoModel;

    @Resource
    private MonedaModel monedaModel;

    // </editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Registro del bien">
    String mensajeExito;
    boolean bienRegistrado;

    public void guardarDatos() {
        try {
            //FIXME
            //if (bien.getId() > 0) {
            if (bien.getId() > 0) {
                if (prepararBien()) {
                    actualizarBien();
                }
               
                if (mensaje.equals("")) {
//                    bienes = bienMod.traerTodo(unidadEjecutoraId);//new ArrayList<Bien>();
                    bienRegistrado = true;
                    Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Bien.Error.ActualizacionExito"));
                    //mensajeExito = "";
                    //Util.navegar("reg_manual");
                }
                else
                    Mensaje.agregarErrorAdvertencia(mensaje);
                  
                
                
            } else {
                if (prepararBien()) {
                    registrarNuevoBien();
                }

                if (mensaje.equals("")) {
       //             bienes = bienMod.traerTodo(unidadEjecutoraId);//new ArrayList<Bien>();
                    bienRegistrado = true;
                    Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Bien.Registro.Exito"));
                    //mensajeExito = "";
                    //Util.navegar("reg_manual");
                }
                else
                    Mensaje.agregarErrorAdvertencia(mensaje);
                }
        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }

    public void registrarNuevoBien() {
        try {

            //Bien bien;// = new ProveedorEntity();
            //Mientas se ligan al select
            Integer idPlaca = buscarPlaca();
            //Asignar la placa
            if (idPlaca > 0) {
                Identificacion placa = new Identificacion();
                placa.setId(idPlaca);
                bien.setIdentificacion(placa);

//                mensaje = bienMod.guardarBien(bien);
                
            } else {
                mensaje = Util.getEtiquetas("sigebi.Bien.Error.Placa");
            }


        } catch (Exception err) {
            //mensaje = err.getMessage();
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    public boolean prepararBien() {
        try {
            
            //Descripción
            if(bien.getDescripcion().length() < 4){
                mensaje = Util.getEtiquetas("sigebi.Bien.Error.IngresoDescripcion");
                return false;
            }
            //tipoBien
            if (tipoSeleccionado == "-1") {
                mensaje = Util.getEtiquetas("sigebi.Bien.Error.Tipo");
                return false;
            }
            Tipo tipoBien = new Tipo();
            tipoBien.setIdTipo(Integer.parseInt(tipoSeleccionado));
            bien.setTipoBien(tipoBien);

            //Origen
            if (origenSeleccionado.equals("-1")) {
                mensaje = Util.getEtiquetas("sigebi.Bien.Error.Origen");
                return false;
            }
            
            Tipo tipoOrigen = new Tipo();
            tipoOrigen.setIdTipo(Integer.parseInt(origenSeleccionado));
            bien.setOrigen(tipoOrigen);

            Moneda moneda = new Moneda();
            moneda.setId(Long.parseLong(selectMoneda));
            bien.setMoneda(moneda);
            //Capitalizable loteOptions
            if (getValorColones() > montoCapitalizable) {
                bien.setCapitalizable(1);
            } else {
                bien.setCapitalizable(0);
            }

            capitalizable = bien.getCapitalizable() == 1 ? "SI" : "NO";

            Estado estado = new Estado();
            estado.setId(Integer.parseInt(selectEstado));

            bien.setEstado(estado);

            //Validar Cantidad
            if (!(bien.getCantidad() > 0)) {
                mensaje = Util.getEtiquetas("sigebi.Bien.Error.Cantidad");
                return false;
            }

            //Lote
            if (valorLote.equals("-1")) {
                bien.setNumeroLote(null);
            } else {
                bien.setNumeroLote( valorLote );
            }

            //Categoria Sub Categoria
            if (selectSubCateg.equals("-1")) {
                mensaje = Util.getEtiquetas("sigebi.Bien.Error.SubCategoria");
                return false;
            }
            
            SubCategoria subCat = new SubCategoria();
            subCat.setCodigoCategoria(selectCategoria);
            subCat.setCodigoSubCategoria(selectSubCateg);
            
            bien.setSubCategoria(subCat);

            //Categoria Sub Categoria
            if (selectSubClasif.equals("-1")) {
                bien.setSubClasificacion(null); 
                mensaje = Util.getEtiquetas("sigebi.Bien.Error.SubClasificacion");
                return false;
            } else {
                SubClasificacion subClas = new SubClasificacion();
                subClas.setId(Integer.parseInt(selectSubClasif));
                Clasificacion clasif = new Clasificacion();
                clasif.setId(Integer.parseInt(selectClasificacion));
                subClas.setClasificacion(clasif);
                bien.setSubClasificacion(subClas);
            }

            //Ubicación (BD)
            if ( ubicacionId.equals("") ||  ubicacionId.equals("-1") ) {
                mensaje = Util.getEtiquetas("sigebi.Bien.Error.Ubicacion");
                return false;
            }
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setId(Integer.parseInt(ubicacionId) );
            bien.setUbicacion( ubicacion );

            //Proveedor (BD)
            if (provId.equals("")) {
                mensaje = Util.getEtiquetas("sigebi.Bien.Error.Proveedor");
                return false;
            }
            Proveedor prov = new Proveedor();
            prov.setId(Long.parseLong(provId));
            bien.setProveedor(prov);

            if (fecAdiquisicion == null) {
                bien.setFechaAdquisicion(null);
            } else {
                bien.setFechaAdquisicion(fecAdiquisicion);
            }

            // Moneda
            if( (selectMoneda == null)||(selectMoneda.equals("-1")) ) {
                mensaje = Util.getEtiquetas("sigebi.Bien.Error.Moneda");
                return false;
            }

            bien.setPersona(1);
            UnidadEjecutora unidad = new UnidadEjecutora();
            unidad.setId(unidadEjecutoraId);
            bien.setUnidadEjecutora(unidad);

            return true;
        } catch (Exception err) {
            mensaje = err.getMessage();
            return false;
        }
    }

    public void actualizarBien() {
        try {
            int ubic = Integer.parseInt(ubicacionId);
//            mensaje = bienMod.actualizarBien(bien, ubic);
            //mensajeExito = "Los datos se modificaron con éxito.";
            if (mensaje.equals("")) {
//                bienes = bienMod.traerTodo(unidadEjecutoraId);
                //mensajeExito = "";
            }

        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }
    
    public int buscarPlaca() {
        boolean capitalizable = bien.getCapitalizable() == 1;
//        return bienMod.placaDisponible(unidadEjecutoraId, capitalizable );
        return 1;
    }
    
    public String getMensajeExito() {
        return mensajeExito;
    }

    public void setMensajeExito(String mensajeExito) {
        this.mensajeExito = mensajeExito;
    }

    public boolean isBienRegistrado() {
        return bienRegistrado;
    }

    public void setBienRegistrado(boolean bienRegistrado) {
        this.bienRegistrado = bienRegistrado;
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Navegación del MENÚ">
    private void inicializaDatos() {
        try{
//        montoCapitalizable = bienMod.getMontoCapitalizable();

        bienRegistrado = true;
        
        accesorio = new AccesoriosEntity();
        accesorios = new ArrayList<AccesoriosEntity>();
        nota = new NotaEntity();
        notas = new ArrayList<NotaEntity>();

        adjunto = new AdjuntoBienEntity();
        adjuntos = new ArrayList<AdjuntoBienEntity>();

        limpiarDatos();
        cargarCombos();
        
        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }

    public void abrirDetalle(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            ViewBienEntity item = (ViewBienEntity) pEvent.getComponent().getAttributes().get("tipoSeleccionado");
            this.vistaOrigen = "reg_manual";
            
            this.abrirDetalle(item.getIdBien());
            
            
            //VistaBienes item = (VistaBienes) pEvent.getComponent().getAttributes().get("tipoSeleccionado");
            //this.abrirDetalle(item.getIdBien());    

        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }

    public void abrirDetalleSincronizar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            ViewBienEntity item = (ViewBienEntity) pEvent.getComponent().getAttributes().get("tipoSeleccionado");
            this.vistaOrigen = "sincronizar";
            
            this.abrirDetalle(item.getIdBien());
            Util.navegar("bien_nuevo");


        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }
    
    private void abrirDetalle(Integer idBien) {
        try{
//        this.bien = bienMod.traerPorId(idBien);
        Util.navegar("bien_nuevo");
        limpiarDatosBien();
        inicializaDatos();
        if (bien.getId() > 0) {
            cargarDatosBien();
        }
        
        this.vistaOrigen = "reg_manual";

        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }
    
    public void nuevoRegistro(ActionEvent pEvent) {
        try{
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }

        inicializaDatos();
        this.vistaOrigen = "reg_manual";
        bien = new Bien();
        bienRegistrado = false;
        limpiarDatosBien();
        Util.navegar("bien_nuevo");
        

        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }

    public void regresarListado() {
        if(vistaOrigen != null){
            Util.navegar(vistaOrigen);
        }else{
            Util.navegar("reg_manual");
        }
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Acciones en Combos">
    //@Resource
    //private ClasificacionModel claMod;

    private void limpiarDatos() {
        subCategoriaOptions = new ArrayList<SelectItem>();
        subClasifOptions = new ArrayList<SelectItem>();
        clasificacionOptions = new ArrayList<SelectItem>();

        capitalizable = "--";
    }

    private SelectItem getCategoriaOption(CategEntity item) {
        SelectItem option = new SelectItem(item.getCodCategoria(), item.getDescripcion());
        return option;
    }

    private SelectItem getCategoriaOption(SubCategoriaEntity item) {
        SelectItem option = new SelectItem(item.getCodSubCateg(), item.getDescripcion());
        return option;
    }

    private SelectItem getCategoriaOption(ClasificacionEntity item) {
        SelectItem option = new SelectItem(item.getIdClasificacion().toString(), item.getNombre());
        return option;
    }

    private SelectItem getCategoriaOption(SubClasificacionEntity item) {
        SelectItem option = new SelectItem(item.getIdSubClasificacion().toString(), item.getNombre());
        return option;
    }

    public void loteSelectCambio(ValueChangeEvent event) {

        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        // Obtyengo el valor seleccionado
        String valor = event.getNewValue().toString();
        esLote = !valor.equals("-1");
        if(!esLote)
            bien.setCantidad(1);
    }

    public void cambioSelectOrigen() {

    }

    public void cambioSelectCategoria(ValueChangeEvent event) {

        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        // Obtengo el valor seleccionado
        String valor = event.getNewValue().toString();
        cargaComboSubCategoria(valor);

        clasificacionOptions = new ArrayList<SelectItem>();
        subClasifOptions = new ArrayList<SelectItem>();

        disableComboClasificacion = true;
        disableComboSubClasificacion = true;

        selectClasificacion = "-1";
        selectSubClasif = "-1";
        selectSubCateg = "-1";

        //mensaje = "Cambio en Categoria: " + valor;
    }

    public void cambioSelectSubCategoria(ValueChangeEvent event) {

        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        clasificacionOptions = new ArrayList<SelectItem>();
        subClasifOptions = new ArrayList<SelectItem>();

        disableComboClasificacion = false;
        disableComboSubClasificacion = true;

        // Obtyengo el valor seleccionado
        String valor = event.getNewValue().toString();
        cargaComboClasificacion(valor);
        //mensaje = "Cambio en Sub Categoria: " + valor;
    }

    public void cambioSelectClasificacion(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        try {

            subClasifOptions = new ArrayList<SelectItem>();
            disableComboSubClasificacion = false;

            // Obtengo el valor seleccionado
            String valor = event.getNewValue().toString();
            cargaComboSubClasificacion(valor);
        } catch (Exception err) {

        }
        // mensaje = "Cambio en Clasificacion";
    }

    public void cargaComboSubCategoria(String valor) {
        subCategoriaOptions = new ArrayList<SelectItem>();
        if (valor.length() > 0) {
            //Cargar Sub Categorias
//            List<SubCategoriaEntity> subCategorias = subCategModel.traerTodo(valor);
//            //Se cargan las Sub Categorías
//            for (SubCategoriaEntity item : subCategorias) {
//                subCategoriaOptions.add(getCategoriaOption(item));
//            }

        }
    }

    public void cargaComboClasificacion(String valor) {
        clasificacionOptions = new ArrayList<SelectItem>();
        if (valor.length() > 0) {
            //Cargar Sub Categorias
//            List<ClasificacionEntity> subCategorias = clasifMod.traerTodo(valor);
//            //Se cargan las Sub Categorías
//            for (ClasificacionEntity item : subCategorias) {
//                clasificacionOptions.add(getCategoriaOption(item));
//            }
        }
    }

    public void cargaComboSubClasificacion(String valor) {

        subClasifOptions = new ArrayList<SelectItem>();
        if (valor.length() > 0) {
            int clasif = parseInt(valor);
            //Cargar SubClasificación
//            List<SubClasificacionEntity> subClasif = subClasifModel.traerTodo(clasif);
//            //Se cargan las Sub Clasificacion
//            for (SubClasificacionEntity item : subClasif) {
//                subClasifOptions.add(getCategoriaOption(item));
//            }
        }
    }

    boolean disableComboClasificacion;
    boolean disableComboSubClasificacion;

    public boolean isDisableComboClasificacion() {
        return disableComboClasificacion;
    }

    public void setDisableComboClasificacion(boolean disableComboClasificacion) {
        this.disableComboClasificacion = disableComboClasificacion;
    }

    public boolean isDisableComboSubClasificacion() {
        return disableComboSubClasificacion;
    }

    public void setDisableComboSubClasificacion(boolean disableComboSubClasificacion) {
        this.disableComboSubClasificacion = disableComboSubClasificacion;
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Proveedores">
    
    
    public void cargarProveedor(ActionEvent pEvent) {

        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        mensajeProveedores = "";
//        proveedores = provModel.listar();
        mensajeProveedores = "Cargados";
    }

    public void filtroProveedor(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        try {

            proveedores = new ArrayList<PersonaEntity>();
 //           proveedores = provModel.filtroProveedores(provIdentificacion, provNombre);

            mensajeProveedores = "Filtros Busqueda";
        } catch (Exception err) {
            mensajeProveedores = err.getMessage();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Modal Proveedores">
    boolean proveedoresVisible;

    public void selecProveedor(ActionEvent pEvent) {

        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        mensaje = "";
        mensajeNota = "";
        PersonaEntity prov = (PersonaEntity) pEvent.getComponent().getAttributes().get("provSeleccionado");
        provId = prov.getNumPersona().toString();
        provSelccionado = prov.getNombre() + " " + prov.getPrimerApellido();

        proveedoresVisible = false;
    }

    public boolean mostrarProveedores() {
        proveedoresVisible = true;
        return true;
    }

    public void proveedoresLimpiar() {
        provId = "";
        provSelccionado = "";
    }

    public boolean cerrarProveedores() {
        proveedoresVisible = false;
        return false;
    }

    public boolean isProveedoresVisible() {
        return proveedoresVisible;
    }

    public void setProveedoresVisible(boolean proveedoresVisible) {
        this.proveedoresVisible = proveedoresVisible;
    }

    //</editor-fold>
    
    String provIdentificacion;
    String provNombre;
    String mensajeProveedores;

    public String getMensajeProveedores() {
        return mensajeProveedores;
    }

    public void setMensajeProveedores(String mensajeProveedores) {
        this.mensajeProveedores = mensajeProveedores;
    }

    public String getProvIdentificacion() {
        return provIdentificacion;
    }

    public void setProvIdentificacion(String provIdentificacion) {
        this.provIdentificacion = provIdentificacion;
    }

    public String getProvNombre() {
        return provNombre;
    }

    public void setProvNombre(String provNombre) {
        this.provNombre = provNombre;
    }

    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Ubicacion">
    
    String ubicacionId;
    String ubicacionNombre;
    
    
    public void ubicacionMostrar() {
        try{
            ubicacionVisible = true;
        }
        catch(Exception err){
            mensaje = err.getMessage();
        }
    }
    
    
    public void ubicacionLimpiar() {
        try{
            ubicacionId = "";
            ubicacionNombre = "";
        }
        catch(Exception err){
            mensaje = err.getMessage();
        }
    }
    
    
    public void cerrarUbicacion() {
        try{
            ubicacionVisible = false;
        }
        catch(Exception err){
            mensaje = err.getMessage();
        }
    }
    
    public void ubicacionSelectCambio(ValueChangeEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            // Obtyengo el valor seleccionado
            String valor = event.getNewValue().toString();
            String[] valores = valor.split("#");
            if(valores.length > 1){
                ubicacionId = valores[0];
                ubicacionNombre = valores[1];
            }
            else{
                ubicacionId = "";
                ubicacionNombre = "";
            }
            //Object valor2 = event.getSource();
            idSelectUbicacion = valor;
        }
        catch(Exception err){
            mensaje = err.getMessage();
        }
        
    }

    
    public String getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(String ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public String getUbicacionNombre() {
        return ubicacionNombre;
    }

    public void setUbicacionNombre(String ubicacionNombre) {
        this.ubicacionNombre = ubicacionNombre;
    }
    
    
    
    
    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Tab Notas del Activo">
    boolean eliminarNotaVisible;

    String notaDetalle;

    public void guardarNota() {
        mensajeNota = "";

        if (bien.getId() < 1) {
            mensajeNota = Util.getEtiquetas("sigebi.Bien.Error.BienNoRegistrado");
            return;
        }
        nota.setIdBien(Integer.parseInt(bien.getId().toString()));
        nota.setIdEstado(1);
        nota.setDetalle(notaDetalle);

        if (nota.getDetalle().trim().length() < 5) {
            mensajeNota = Util.getEtiquetas("sigebi.Bien.Error.NotaInvalida");
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.NotaInvalida"));
            return;
        }

        //mensajeNota = detalle;
        nota.setIdNota(notaModel.obtenerId());

        String resp = notaModel.guardarNuevo(nota);

        notas = notaModel.traerTodo(Integer.parseInt(bien.getId().toString()));//FIXME

        if (resp.length() > 0) {
            mensajeNota = Util.getEtiquetas("sigebi.Bien.Error.Nota");
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Nota"));
        } else {
            nota = new NotaEntity();
        }
    }

    public void eliminarNota(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            nota = new NotaEntity();
            mensajeNota = "";
            nota = (NotaEntity) pEvent.getComponent().getAttributes().get("notaSeleccionada");
            eliminarNotaVisible = true;
            //mensajeNota = notaModel.eliminarNota(nota);

        } catch (Exception err) {
            mensajeNota = err.getMessage();
        }
    }

    public void eliminarNotaCancelar() {
        try {
            nota = new NotaEntity();
            mensajeNota = "";
            eliminarNotaVisible = false;

        } catch (Exception err) {
            mensajeNota = err.getMessage();
        }
    }

    public void eliminarNotaConfirmar() {
        try {
            mensajeNota = "";
            mensajeNota = notaModel.eliminarNota(nota);

            nota = new NotaEntity();
            notas = new ArrayList<NotaEntity>();
            notas = notaModel.traerTodo(Integer.parseInt(bien.getId().toString()));
            eliminarNotaVisible = false;

        } catch (Exception err) {
            mensajeNota = err.getMessage();
        }
    }

    public String getNotaDetalle() {
        return notaDetalle;
    }

    public void setNotaDetalle(String notaDetalle) {
        this.notaDetalle = notaDetalle;
    }

    public boolean isEliminarNotaVisible() {
        return eliminarNotaVisible;
    }

    public void setEliminarNotaVisible(boolean eliminarNotaVisible) {
        this.eliminarNotaVisible = eliminarNotaVisible;
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Tab Características">
    public void guardarCaracteristica() {

        if (descCaracteristica.length() < 5) {
            mensajeCaracteristicas = "Debe registrar al menos 5 caracteristicas.";
            return;
        }
        if (selectCaracteristica.equals("-1")) {
            mensajeCaracteristicas = "Seleccione Caracteristica.";
            return;
        }
        BienCaracteristica registro = new BienCaracteristica();

        Tipo caract = new Tipo();
        caract.setIdTipo(Integer.parseInt(selectCaracteristica));

        //FIXME
        registro.setId(bien.getId());
        registro.setDetalle(descCaracteristica);
        registro.setTipo(caract);
        Estado estado = new Estado();
        estado.setId(Constantes.ESTADO_GENERAL_ACTIVO);
        registro.setEstado(estado);
        
        // TODO revisar almacenamiento de caracteristicas, deberian tener su propio model y dao, no tiene por que estar en TIPO
        //mensajeCaracteristicas = tipoModel.guardarCaracteristica(registro);
        if (mensajeCaracteristicas.length() == 0) {
            descCaracteristica = "";
        }
        cargarCaracteristica();
        //mensajeCaracteristicas = "Pendiente de implementar.";
    }

    public void cargarCaracteristica() {
        try{
            modifCaracterVisible = false;

            caracteristicas = new ArrayList<DatoBienEntity>();
            //caracteristicas = tipoModel.traerCaracteristicasRegistradas(constCaracteristicas,
            bien.getId();

            cargarOpcionesCaract();
            caracteristica = new DatoBienEntity();
            //mensajeCaracteristicas = "Debo llenar el select de características";
        }catch(Exception err){
            mensaje = err.getMessage();
        }
    }

    private void cargarOpcionesCaract() {
        //List<Tipo> caract = tipoModel.traerCaracteristicas(constCaracteristicas, bien.getId());//FIXME

        caracteristicasOptions = new ArrayList<SelectItem>();
//        for (Tipo item : caract) {
//            caracteristicasOptions.add(new SelectItem("" + item.getIdTipo(), item.getNombre()));
//        }
    }

    public void modificarCaracteristica(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            caracteristica = new DatoBienEntity();
            mensajeCaracteristicas = "";
            caracteristica = (DatoBienEntity) pEvent.getComponent().getAttributes().get("caracteristicaSelccionada");

            modifCaracterVisible = true;
        } catch (Exception err) {
            mensajeCaracteristicas = err.getMessage();
        }
    }

    public void elminimarCaracteristica() {

        //mensajeCaracteristicas = tipoModel.eliminarCaracteristica(caracteristica);

        cargarCaracteristica();
    }

    public void actualizarCaracteristica() {

        //mensajeCaracteristicas = tipoModel.modificarCaracteristica(caracteristica);
        cargarCaracteristica();
    }

    public void cancelarCaracteristica() {

        modifCaracterVisible = false;
    }

    //<editor-fold defaultstate="collapsed" desc="Variables Características">
    String mensajeCaracteristicas;
    String selectCaracteristica;
    String descCaracteristica;
    String constCaracteristicas = "CARACTERISTICA";
    // comboBox subCategorias
    List<SelectItem> caracteristicasOptions;

    List<DatoBienEntity> caracteristicas;
    DatoBienEntity caracteristica;
    boolean modifCaracterVisible;

    public boolean isModifCaracterVisible() {
        return modifCaracterVisible;
    }

    public void setModifCaracterVisible(boolean modifCaracterVisible) {
        this.modifCaracterVisible = modifCaracterVisible;
    }

    public DatoBienEntity getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(DatoBienEntity caracteristica) {
        this.caracteristica = caracteristica;
    }

    public BienModel getBienMod() {
        return bienMod;
    }

    public void setBienMod(BienModel bienMod) {
        this.bienMod = bienMod;
    }

    public List<DatoBienEntity> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<DatoBienEntity> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public List<SelectItem> getCaracteristicasOptions() {
        return caracteristicasOptions;
    }

    public void setCaracteristicasOptions(List<SelectItem> caracteristicasOptions) {
        this.caracteristicasOptions = caracteristicasOptions;
    }

    public String getSelectCaracteristica() {
        return selectCaracteristica;
    }

    public void setSelectCaracteristica(String selectCaracteristica) {
        this.selectCaracteristica = selectCaracteristica;
    }

    public String getMensajeCaracteristicas() {
        return mensajeCaracteristicas;
    }

    public void setMensajeCaracteristicas(String mensajeCaracteristicas) {
        this.mensajeCaracteristicas = mensajeCaracteristicas;
    }

    public String getDescCaracteristica() {
        return descCaracteristica;
    }

    public void setDescCaracteristica(String descCaracteristica) {
        this.descCaracteristica = descCaracteristica;
    }

    //</editor-fold>
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Tad Garantías">
    String garantiaMensajeError;
    String garantiaMensajeExito;

    Date garantiaFecIni;
    Date garantiaFecFin;
    String garantiaDesc;

    public void guardarGarantia() {
        try {
            int resp = garantiaFecIni.compareTo(garantiaFecFin);
            //Validamos los datos
            if(resp == -1){
                
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                String iniGarantia = df.format(garantiaFecIni);
                String finGarantia = df.format(garantiaFecFin);

//                garantiaMensajeError = bienMod.guardarGarantia(bien.getId(),
//                        iniGarantia,
//                        finGarantia,
//                        garantiaDesc);

                if(garantiaMensajeError.equals(""))
                    garantiaMensajeExito = "Garantía guardada.";

            }
            else{
                garantiaMensajeError = "El inicio de la garantia debe ser menor al final de la garantía";
                
            }
            
        }
        catch(NullPointerException e){
            garantiaMensajeError = "Favor registrar ambas fecha con formato (DD/MM/YYYY)";
        }
        catch (Exception err) {
            garantiaMensajeError = err.getMessage();
        }
    }

    public void cargarGarantia() {
        garantiaFecIni = bien.getInicioGarantia();
        garantiaFecFin = bien.getFinGarantia();
        garantiaDesc = bien.getDescripcionGarantia();
    }

    public String getGarantiaMensajeError() {
        return garantiaMensajeError;
    }

    public void setGarantiaMensajeError(String garantiaMensajeError) {
        this.garantiaMensajeError = garantiaMensajeError;
    }

    public String getGarantiaMensajeExito() {
        return garantiaMensajeExito;
    }

    public void setGarantiaMensajeExito(String garantiaMensajeExito) {
        this.garantiaMensajeExito = garantiaMensajeExito;
    }

    public Date getGarantiaFecIni() {
        return garantiaFecIni;
    }

    public void setGarantiaFecIni(Date garantiaFecIni) {
        this.garantiaFecIni = garantiaFecIni;
    }

    public Date getGarantiaFecFin() {
        return garantiaFecFin;
    }

    public void setGarantiaFecFin(Date garantiaFecFin) {
        this.garantiaFecFin = garantiaFecFin;
    }

    public String getGarantiaDesc() {
        return garantiaDesc;
    }

    public void setGarantiaDesc(String garantiaDesc) {
        this.garantiaDesc = garantiaDesc;
    }
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Tad Archivos-Adjuntos">
    String mensajeAdjuntos;
    String mensajeAdjuntosExito;

    String adjuntoDescripcion;
    AdjuntoBienEntity adjunto;
    List<AdjuntoBienEntity> adjuntos;

    @Resource
    AdjuntoBienModel modelAdjunto;
    
    private String componentStatus;
    private String urlAdjunto;
    private String nombreAdjunto;
    private float tamannoAdjunto;
    private String extencionAdjunto;
    private String tipoAdjunto;
    
    
    private String adjuntoDescargar;
    private String adjuntoMostrar;
    private String adjuntosUbicacion;
    private String adjuntoNombreDescarga;
    
    boolean mostrarAdjunto;
    
    public void checkFileLocation(ActionEvent event){
        InputFile inputFile =(InputFile) event.getSource();
        FileInfo fileInfo = inputFile.getFileInfo();
        //file has been saved
         if (fileInfo.isSaved()) {
             // Path with uniqueFolder attribute default
             if(inputFile.getId().endsWith("2")){
                 urlAdjunto = fileInfo.getPhysicalPath();
                 nombreAdjunto = fileInfo.getFileName();
                 tamannoAdjunto = (fileInfo.getSize() /1024 ); // pasar a bites 
                 tipoAdjunto =  fileInfo.getContentType();
                 String[] extencion = (String[]) nombreAdjunto.split(Pattern.quote("."));
                 int cant = extencion.length;
                 extencionAdjunto = extencion[cant - 1];
                 /*
                 adjuntoDescripcion += "\nNombre: "+nombreAdjunto;
                 adjuntoDescripcion += "\nTamaño: "+tamannoAdjunto;
                 adjuntoDescripcion += "\nTipoArchivo: "+tipoAdjunto;
                 adjuntoDescripcion += "\nExtención: "+extencionAdjunto;
                 */
                 guardarAdjunto();
             }
             
        }
    }

    
    
    public void guardarAdjunto() {
        mensajeAdjuntos = "";
        mensajeAdjuntosExito = "";
        try {
            if (bien.getId() < 1) {
                mensajeAdjuntos = "El bien no ha sido registrado.";
                return;
            }
                adjunto = new AdjuntoBienEntity();
            adjunto.setDetalle(adjuntoDescripcion);
            adjunto.setIdEstado(1);
            adjunto.setIdTipo(1);
            adjunto.setIdBien(Integer.parseInt(bien.getId().toString()));
            adjunto.setUrl(urlAdjunto);
            
            adjunto.setNombre(nombreAdjunto);
            adjunto.setTamano(tamannoAdjunto);
            adjunto.setTipoMime(tipoAdjunto);
            adjunto.setExtension(extencionAdjunto);
            

            //Detalle
            //El Id se registra cuando se guarda;
            String resp = modelAdjunto.guardarAdjunto(adjunto);
            if (resp.length() == 0) {
                mensajeAdjuntosExito = "El Archivo se guardó con éxito.";
            }
            
            cargarAdjuntos();
            
            if (resp.length() > 0) {
                mensajeAdjuntos = resp;
            } else {
                adjunto = new AdjuntoBienEntity();
            }
        } catch (Exception err) {
            mensajeAdjuntos = err.getMessage();
        }
    }

    private void cargarAdjuntos(){
        //FIXME
        adjuntos = modelAdjunto.traerAdjuntos(Integer.parseInt(bien.getId().toString()));
    }
    
    public void adjuntoMostrarDetalle(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            adjuntosUbicacion = "upload/";
            adjunto = new AdjuntoBienEntity();
            mensajeAdjuntos = "";
            mensajeAdjuntosExito = "";
            adjunto = (AdjuntoBienEntity) pEvent.getComponent().getAttributes().get("adjuntoSeleccionado");
            mostrarAdjunto = true;
            
            adjuntoDescargar = adjuntosUbicacion + adjunto.getNombre();
            adjuntoNombreDescarga = adjunto.getNombre().replace("."+adjunto.getExtension(), "");
            if( (adjunto.getExtension().toUpperCase().equals("JPG"))
                    ||(adjunto.getExtension().toUpperCase().equals("PNG"))
                    ||(adjunto.getExtension().toUpperCase().equals("GIF"))
                    ||(adjunto.getExtension().toUpperCase().equals("JPGE"))
                    ){
                adjuntoMostrar = adjuntoDescargar;
                
            }else{
                adjuntoMostrar = "imagenes/botones/descargar_SIIAGC.png";
            }
            //mensajeNota = notaModel.eliminarNota(nota);

        } catch (Exception err) {
            mensajeAdjuntos = err.getMessage();
        }
        
    }
    
    public void adjuntoEliminarCancelar(){
        
        adjunto = new AdjuntoBienEntity();
        mostrarAdjunto = false;
    }
    
    public void adjuntoEliminarConfirmar(){
        try{
            mensajeAdjuntos = modelAdjunto.eliminarAdjunto(adjunto);
            adjunto = new AdjuntoBienEntity();
            mostrarAdjunto = false;
            cargarAdjuntos();
        }catch(Exception err){
            mensajeAdjuntos = err.getMessage();
        }
    }

    
    public void downloadFile() throws FileNotFoundException, IOException {
        try{
            
            
            String dir =  "C:\\SIGEBI_V2\\build\\web\\upload\\";// System.getProperty("user.dir");
            mensajeAdjuntos = "current dir = " + dir;
            
            File file = new File(dir + adjunto.getNombre());
            InputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int offset = 0;
            int numRead = 0;

             while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) 
            {
              offset += numRead;
            }
            fis.close();
              HttpServletResponse response =
            (HttpServletResponse) FacesContext.getCurrentInstance()
                .getExternalContext().getResponse();

                  response.setContentType("application/octet-stream");

            response.setHeader("Content-Disposition", "attachment;filename="+adjunto.getNombre());
            response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        }catch(Exception err){
            
            mensajeAdjuntos = err.getMessage();
        }
    }
    
    
    
    public void downloadFile(ActionEvent event) throws FileNotFoundException, IOException {
        try{
            File file = new File(adjuntoDescargar);
            InputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int offset = 0;
            int numRead = 0;

             while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) 
            {
              offset += numRead;
            }
            fis.close();
              HttpServletResponse response =
            (HttpServletResponse) FacesContext.getCurrentInstance()
                .getExternalContext().getResponse();

                  response.setContentType("application/octet-stream");

            response.setHeader("Content-Disposition", "attachment;filename="+adjunto.getNombre());
            response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        }catch(Exception err){
            mensajeAdjuntos = err.getMessage();
        }
    }

    
    public String getAdjuntoDescargar() {
        return adjuntoDescargar;
    }

    public void setAdjuntoDescargar(String adjuntoDescargar) {
        this.adjuntoDescargar = adjuntoDescargar;
    }

    public String getAdjuntoNombreDescarga() {
        return adjuntoNombreDescarga;
    }

    public void setAdjuntoNombreDescarga(String adjuntoNombreDescarga) {
        this.adjuntoNombreDescarga = adjuntoNombreDescarga;
    }
    
    public String getAdjuntoMostrar() {
        return adjuntoMostrar;
    }

    public void setAdjuntoMostrar(String adjuntoMostrar) {
        this.adjuntoMostrar = adjuntoMostrar;
    }
    
    public AdjuntoBienEntity getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(AdjuntoBienEntity adjunto) {
        this.adjunto = adjunto;
    }

    public List<AdjuntoBienEntity> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<AdjuntoBienEntity> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public String getMensajeAdjuntosExito() {
        return mensajeAdjuntosExito;
    }

    public void setMensajeAdjuntosExito(String mensajeAdjuntosExito) {
        this.mensajeAdjuntosExito = mensajeAdjuntosExito;
    }

    public String getMensajeAdjuntos() {
        return mensajeAdjuntos;
    }

    public void setMensajeAdjuntos(String mensajeAdjuntos) {
        this.mensajeAdjuntos = mensajeAdjuntos;
    }

    public String getComponentStatus() {
        return componentStatus;
    }

    public String getAdjuntoDescripcion() {
        return adjuntoDescripcion;
    }

    public void setAdjuntoDescripcion(String adjuntoDescripcion) {
        this.adjuntoDescripcion = adjuntoDescripcion;
    }

    public boolean isMostrarAdjunto() {
        return mostrarAdjunto;
    }

    public void setMostrarAdjunto(boolean mostrarAdjunto) {
        this.mostrarAdjunto = mostrarAdjunto;
    }

    
    
    
    
    
    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Tab Notas del Activo">
    boolean eliminarAccesorioVisible;
    String mensajeAccesExito;
    String mensajeAccesError;
    AccesoriosEntity accesorio;
    List<AccesoriosEntity> accesorios;

    @Resource
    AccesorioModel modelAccesorio;

    public void guardarAccesorio() {
        mensajeAccesExito = "";
        mensajeAccesError = "";
        try {
            if (bien.getId() < 1) {
                mensajeAccesError = "El bien no ha sido registrado.";
                return;
            }
            accesorio.setIdBien(Integer.parseInt(bien.getId().toString()));
            accesorio.setIdEstado(1);

            String detalle = accesorio.getDetalle();
            if (accesorio.getDetalle().length() < 5) {
                mensajeAccesError = "EL accesorio debe tener al menos 5 caracteres.";
            }

            //MEl Id se registra coando se guarda;
//            String resp = modelAccesorio.guardarAccesorio(accesorio);
//            if (resp.length() == 0) {
//                mensajeAccesExito = "El registro se guardó con éxito.";
//            }//FIXME
//            accesorios = modelAccesorio.traerAccesorios(bien.getId());

//            if (resp.length() > 0) {
//                mensajeAccesError = resp;
//            } else {
//                accesorio = new AccesoriosEntity();
//            }
        } catch (Exception err) {
            mensajeAccesError = err.getMessage();
        }
    }

    public void eliminarAccesorio(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            accesorio = new AccesoriosEntity();
            mensajeAccesExito = "";
            mensajeAccesError = "";
            accesorio = (AccesoriosEntity) pEvent.getComponent().getAttributes().get("accesorioSeleccionado");
            eliminarAccesorioVisible = true;
            //mensajeNota = notaModel.eliminarNota(nota);

        } catch (Exception err) {
            mensajeAccesError = err.getMessage();
        }
    }

    public void eliminarAccesorioCancelar() {
        try {
            accesorio = new AccesoriosEntity();
            mensajeAccesExito = "";
            mensajeAccesError = "";
            //mensajeNota = notaModel.eliminarNota(nota);
            eliminarAccesorioVisible = false;
            //notas = new 

        } catch (Exception err) {
            mensajeAccesError = err.getMessage();
        }
    }

    public void eliminarAccesorioConfirmar() {
        try {
//            mensajeAccesError = "";
//            mensajeAccesError = modelAccesorio.eliminarAccesorio(accesorio);
//
//            accesorio = new AccesoriosEntity();
//            accesorios = new ArrayList<AccesoriosEntity>();
//            //FIXME
//            accesorios = modelAccesorio.traerAccesorios(bien.getId());
//            eliminarAccesorioVisible = false;

        } catch (Exception err) {
            mensajeAccesError = err.getMessage();
        }
    }

    public boolean isEliminarAccesorioVisible() {
        return eliminarAccesorioVisible;
    }

    public void setEliminarAccesorioVisible(boolean eliminarAccesorioVisible) {
        this.eliminarAccesorioVisible = eliminarAccesorioVisible;
    }

    public String getMensajeAccesExito() {
        return mensajeAccesExito;
    }

    public void setMensajeAccesExito(String mensajeAccesExito) {
        this.mensajeAccesExito = mensajeAccesExito;
    }

    public String getMensajeAccesError() {
        return mensajeAccesError;
    }

    public void setMensajeAccesError(String mensajeAccesError) {
        this.mensajeAccesError = mensajeAccesError;
    }

    public AccesoriosEntity getAccesorio() {
        return accesorio;
    }

    public void setAccesorio(AccesoriosEntity accesorio) {
        this.accesorio = accesorio;
    }

    public List<AccesoriosEntity> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(List<AccesoriosEntity> accesorios) {
        this.accesorios = accesorios;
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Panel Observacion Sincronizar o Rechazar">
    
    boolean panelObservaVisible = false;
    boolean accionSincronizar = false;
    boolean accionRechazar = false;
    String observacionCliente = "";
    
    public void rechazarBien() {
        if (this.observacionCliente == null || this.observacionCliente.isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.rechazarBien.sin.observacion"));
        } else {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
           // bienMod.cambiaEstadoBien(this.bien, estadoModel.obtenerPorEstado(Constantes.DOMINI0_ESTADO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE), observacionCliente, telefono);
            observacionCliente = "";
            //Se oculta el panel
            this.cerrarPanelObserva();
        }
    }

    public void solicitarSincronizacion() {
        Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
        //bienMod.cambiaEstadoBien(this.bien, estadoModel.buscarPorDominioEstado(Constantes.DOMINI0_ESTADO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR), observacionCliente, telefono);
        //Se oculta el panel
        this.cerrarPanelObserva();

    }
    public boolean mostrarPanelObserva(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return false;
        }

        //Se presenta el panel de obervacion
        panelObservaVisible = true;
        return true;
    }

    public boolean cerrarPanelObserva() {
        panelObservaVisible = false;
        return false;
    }

    public boolean isPanelObservaVisible() {
        return panelObservaVisible;
    }

    public void setPanelObservaVisible(boolean panelObservaVisible) {
        this.panelObservaVisible = panelObservaVisible;
    }

    public String getObservacionCliente() {
        return observacionCliente;
    }

    public void setObservacionCliente(String observacionCliente) {
        this.observacionCliente = observacionCliente;
    }

    //</editor-fold>


}
