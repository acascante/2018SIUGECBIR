package cr.ac.ucr.framework.seguridad;

/**
 * Clase Abstracta para contener valores y generalizar todos los Objetos Entitys de la aplicacion
 * @author Adam M. Gamboa González
 * @since 04 Junio 2010
 * @version 1.0
 */
public abstract class ObjetoBase {

    protected volatile transient boolean seleccionado;
    protected volatile transient boolean marcado;
    protected volatile transient Long idTemporal = -1L;

    public ObjetoBase() {

    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public Long getIdTemporal() {
        return idTemporal;
    }

    public void setIdTemporal(Long idTemporal) {
        this.idTemporal = idTemporal;
    }
}
