package cr.ac.ucr.sigebi.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeSIGEBI { 

    //<editor-fold defaultstate="collapsed" desc="Artibutos>
    private DefaultTreeModel model;
    private NodoSIGEBI nodoSeleccionado = null;    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public DefaultTreeModel getModel() {
        return model;
    }

    public void setModel(DefaultTreeModel model) {
        this.model = model;
    }

    public NodoSIGEBI getNodoSeleccionado() {
        return nodoSeleccionado;
    }

    public void setNodoSeleccionado(NodoSIGEBI nodoSeleccionado) {
        this.nodoSeleccionado = nodoSeleccionado;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores y metodos">
    public TreeSIGEBI(ArrayList<Object> objetos, String nodoPrincipal, String campoDescripcion) {

        // Crea el nodo principal
        DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
        NodoSIGEBI rootObject = new NodoSIGEBI(rootTreeNode, this, null, "");
        rootObject.setText(nodoPrincipal);
        rootTreeNode.setUserObject(rootObject);

        // model is accessed by by the ice:tree component
        model = new DefaultTreeModel(rootTreeNode);

        // Se agregan los nodos principales
        for (Object objeto : objetos) {

            try {
                //Se obtiene la clase de los objetos
                Class<?> c = objeto.getClass();
                
                //Se obtiene la descripcion del objeto
                Field f = c.getDeclaredField(campoDescripcion);
                f.setAccessible(true);
                String descripcion = (String) f.get(objeto);
                
                //Se agrega el nodo
                DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode();
                NodoSIGEBI branchObject = new NodoSIGEBI(branchNode, this, objeto, descripcion);
                branchObject.setText(descripcion);
                branchNode.setUserObject(branchObject);
                rootTreeNode.add(branchNode);

            } catch (IllegalAccessException ex) {
                Logger.getLogger(NodoSIGEBI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(NodoSIGEBI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(NodoSIGEBI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Elimina el nodo seleccionado de la lista
     */
    public void eliminaNodoSeleccionado(){
        //Se verifica que no sea el nodo principal
        if (nodoSeleccionado != null && nodoSeleccionado.getObject() != null){
            nodoSeleccionado.eliminarNodo();
            this.nodoSeleccionado = null;
        }
    }

    /**
     * Elimina el nodo seleccionado de la lista
     * @param nodoSeleccionado
     */
    public void seleccionaNodo(NodoSIGEBI nodoSeleccionado){
        this.nodoSeleccionado = nodoSeleccionado;
    }
    
    //</editor-fold>

}
