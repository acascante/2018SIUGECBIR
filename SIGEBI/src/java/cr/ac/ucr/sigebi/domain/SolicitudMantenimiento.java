/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "SolicitudMantenimiento")
@Table(name = "SIGEBI_OAF.SIGB_SOLICITUD_MANTENIMIENTO")
@PrimaryKeyJoinColumn(name = "ID_SOLICITUD", referencedColumnName = "ID_SOLICITUD")
@DiscriminatorValue("3")
public class SolicitudMantenimiento extends Solicitud {

}
