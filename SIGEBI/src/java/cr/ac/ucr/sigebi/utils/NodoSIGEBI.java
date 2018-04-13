package cr.ac.ucr.sigebi.utils;

import com.icesoft.faces.component.tree.IceUserObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.tree.DefaultMutableTreeNode;

public class NodoSIGEBI extends IceUserObject {

    private TreeSIGEBI treeSIGEBI;
    private Object object;

    /**
     * Constructor del objecto
     *
     * @param wrapper
     * @param tree
     * @param object
     * @param nodeToolTip
     */
    public NodoSIGEBI(DefaultMutableTreeNode wrapper, TreeSIGEBI tree, Object object, String nodeToolTip) {
        super(wrapper);

        this.treeSIGEBI = tree;
        this.object = object;

        setLeafIcon("xmlhttp/css/xp/css-images/tree_document.gif");
        setBranchContractedIcon("xmlhttp/css/xp/css-images/tree_folder_close.gif");
        setBranchExpandedIcon("xmlhttp/css/xp/css-images/tree_folder_open.gif");
        setText("");
        setTooltip(nodeToolTip);
        setExpanded(true);
    }

    /**
     * Elimina el nodo de la lista
     */
    public void eliminarNodo() {
        ((DefaultMutableTreeNode) getWrapper().getParent()).remove(getWrapper());
    }

    /**
     * Se agrega una lista
     * @param objetos
     * @param campoDescripcion
     */
    public void agregarNodos(ArrayList<Object> objetos, String campoDescripcion) {

        //Se limpian los hijos del node
        this.wrapper.removeAllChildren();

        for (Object objeto : objetos) {
            try {

                //Se obtiene la clase de los objetos
                Class<?> c = objeto.getClass();

                //Se obtiene la descripcion del objeto
                Field f = c.getDeclaredField(campoDescripcion);
                f.setAccessible(true);
                String descripcion = (String) f.get(objeto);

                //Se agrega el nuevo nodo
                DefaultMutableTreeNode subBranchNode = new DefaultMutableTreeNode();
                NodoSIGEBI subBranchObject = new NodoSIGEBI(subBranchNode, this.treeSIGEBI, objeto, descripcion);
                subBranchObject.setText(descripcion);
                subBranchObject.setLeaf(true);
                subBranchNode.setUserObject(subBranchObject);
                this.wrapper.add(subBranchNode);

            } catch (IllegalAccessException ex) {
                Logger.getLogger(NodoSIGEBI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(NodoSIGEBI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(NodoSIGEBI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public TreeSIGEBI getTreeSIGEBI() {
        return treeSIGEBI;
    }

    public void setTreeSIGEBI(TreeSIGEBI treeSIGEBI) {
        this.treeSIGEBI = treeSIGEBI;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
