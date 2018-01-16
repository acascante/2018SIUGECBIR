package cr.ac.ucr.framework.vista;

import com.icesoft.faces.context.effects.Appear;
import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.EffectQueue;
import com.icesoft.faces.context.effects.Fade;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller(value = "vistaMensajes")
@Scope(value = "session")
public class VistaMensajes {

    private Effect efecto;
    private List<String> mensaje;
    private TIPO tipo;
    private boolean visible;
    private boolean acoplado;

    public enum TIPO {

        INFORMACION,
        ADVERTENCIA,
        ERROR,
        ERROR_FATAL,
        INVISBLE;
    }

    /** Creates a new instance of VistaMensajes */
    public VistaMensajes() {
        tipo = TIPO.INVISBLE;
    }

//<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
//Métodos
    /**
     * Muestra el mensaje y su debido efecto visual
     */
    private void mostrarEfecto() {
        EffectQueue aparecer_desaparecer = new EffectQueue("effectAppearFade");

        Effect aparecer = new Appear();
        aparecer.setDuration(1f);

        Effect desaparecer = new Fade();
        desaparecer.setDuration(15f);

        aparecer_desaparecer.add(aparecer);
        aparecer_desaparecer.add(desaparecer);

        this.efecto = aparecer_desaparecer;
        visible = false;
    }   //mostrarEfecto

    public void cerrarPopup(ActionEvent e) {
        visible = false;
    }   //

    public void cerrarMensaje(ActionEvent ev) {
        if (ev.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
            ev.setPhaseId(PhaseId.INVOKE_APPLICATION);
            ev.queue();
        } else {
            this.tipo = TIPO.INVISBLE;
            visible = false;
            acoplado = false;
        }
    }

    public void anclarMensaje(ActionEvent ev) {
        if (ev.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
            ev.setPhaseId(PhaseId.INVOKE_APPLICATION);
            ev.queue();
        } else {
            acoplado = true;
        }
    }

    public void limpiarMensaje() {
        this.tipo = TIPO.INVISBLE;
        visible = false;
        acoplado = false;
    }


    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>   GETS y SETS  <<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/
    /**
     * Devuelve el estilo que van a tener los mensajes
     * @return
     */
    public String getEstilo() {
        switch (tipo) {
            case INFORMACION:
                return "mensaje_informativo";
            case ADVERTENCIA:
                return "mensaje_advertencia";
            case ERROR:
                return "mensaje_error";
            default:
                return "invisible";
        }   //switch
    }

    public Effect getEfecto() {
        return efecto;
    }

    public List<String> getMensaje() {
        return mensaje;
    }

    public void setMensaje(List<String> mensaje) {
        setMensaje(mensaje, TIPO.INFORMACION);
    }

    public void setMensaje(List<String> mensaje, TIPO tipo) {
        this.mensaje = mensaje;
        acoplado = false;
        mostrarEfecto();
        setTipo(tipo);
    }

    public void setMensajeFatal(List<String> mensaje, TIPO tipo) {
        setMensaje(mensaje, tipo);
        visible = true;
    }

    public TIPO getTipo() {
        return tipo;
    }

    public void setTipo(TIPO tipo) {
        this.tipo = tipo;
    }

    public boolean isAcoplado() {
        return acoplado;
    }

    public void setAcoplado(boolean acoplado) {
        this.acoplado = acoplado;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}   //VistaMensaje