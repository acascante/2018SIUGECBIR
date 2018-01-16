/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cr.ac.ucr.framework.vista.util;

import java.util.UUID;


/**
 *
 * @author jonnathan
 */
public class GeneradorId {
    public static String generarid(){
            UUID uuid=java.util.UUID.randomUUID();
            return uuid.toString();
    }
}
