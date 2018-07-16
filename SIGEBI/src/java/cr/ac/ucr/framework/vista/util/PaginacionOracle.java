package cr.ac.ucr.framework.vista.util;

import java.util.List;
import javax.faces.model.SelectItem;

public class PaginacionOracle {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    //Cantidad de registros por página
    private static int cantRegistroPorPagina = 5;
    //Primer registro de la pagina
    private int primerRegistro = 1;
    //Cantidad de registros totales
    private int cantidadRegistros = 0;
    //Cantidad de páginas
    private int cantidadPaginas = 0;
    //Página actual
    private int paginaActual = 1;
    //Cantidad de links para paginado
    private int cantidadPaginasLinks = 4;
    //Arreglo de línks de páginas
    private Integer[] arregloPaginas = null;
    //Lista registros por pagina
    private List<SelectItem> listaRegistrosPagina = null;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Sets y gets">
    /**
     * Obtiene la cantidad de registros por página
     *
     * @return cantRegistroPorPagina Registros por pagina
     */
    public int getCantRegistroPorPagina() {
        return cantRegistroPorPagina;
    }

    /**
     * Modifica la cantidad de registros por página
     *
     * @param pCantRegistros Registros por página
     */
    public void setCantRegistroPorPagina(int pCantRegistros) {
        cantRegistroPorPagina = pCantRegistros;
    }

    /**
     * Obtiene la cantidad total de registros
     *
     * @return cantidadRegistros Cantidad total de registros
     */
    public int getCantidadRegistros() {
        return cantidadRegistros;
    }

    /**
     * Modifica la cantidad total de registros
     *
     * @param pCantidadRegistros Cantidad total de registros
     */
    public void setCantidadRegistros(int pCantidadRegistros) {
        this.cantidadRegistros = pCantidadRegistros;
    }

    /**
     * Obtiene el primer registro de la página
     *
     * @return primerRegistro Primer registro de la página
     */
    public int getPrimerRegistro() {
        //Si no hay registros
        if (cantidadRegistros == -1) {
            cantidadRegistros = 0;
        }
//        if (primerRegistro == 0) {
//            primerRegistro = 1;
//        }
        //Si el primer registro es mayor a la cantidad de registros
        if (primerRegistro >= cantidadRegistros) {
            //Si no hay registros
            if (cantidadRegistros == 0) {
                primerRegistro = 0;
            } else {
                //Se calcula la cantidad de paginas que hay, contando las
                //páginas que no tienen la cantidad de registros por página
                //completos
                int lIntCantRegs = cantidadRegistros;// - 1;
                double lDoubleCantRegs = cantidadRegistros;
                double lPageInt = lIntCantRegs / cantRegistroPorPagina;
                double lPageDouble = lDoubleCantRegs / cantRegistroPorPagina;
                int lPage = (int) Math.floor(lPageDouble);
                //Se asigna el primer registro de la pagina
                if (lPageInt == lPageDouble) {
                    primerRegistro = (lPage * cantRegistroPorPagina)
                            - cantRegistroPorPagina + 1;
                } else {
                    primerRegistro = lPage * cantRegistroPorPagina + 1;
                }
            }
        }
        return primerRegistro;
    }

    /**
     * Modifica el valor del primer registro
     *
     * @param pPrimerRegistro Primer registro de la página
     */
    public void setPrimerRegistro(int pPrimerRegistro) {
        this.primerRegistro = pPrimerRegistro;
    }

    /**
     * Obtiene el último registro de la pagina
     *
     * @return último registro de la página
     */
    public int getUltimoRegistro() {
        getPrimerRegistro();
        return primerRegistro + cantRegistroPorPagina > cantidadRegistros
                ? cantidadRegistros : primerRegistro + cantRegistroPorPagina - 1;
    }

    /**
     * Obtiene setea los valores de primer registro para que se desplace a la
     * siguiente página
     */
    public void getSiguientePagina() {
        getPrimerRegistro();
        if (primerRegistro + cantRegistroPorPagina < cantidadRegistros) {
            primerRegistro += cantRegistroPorPagina;
        } else {
            primerRegistro = getUltimoRegistro() + 1;
        }
    }

    /**
     * Obtiene setea los valores de primer registro para que se desplace a la
     * página anterior
     */
    public void getPaginaAnterior() {
        getPrimerRegistro();
        primerRegistro -= cantRegistroPorPagina;
        if (primerRegistro <= 0) {
            primerRegistro = 1;
        }
    }

    /**
     * Obtiene la cantidad total de paginas
     *
     * @return cantidadPaginas Cantidad de páginas
     */
    public int getCantidadPaginas() {
        int lIntCantRegs = cantidadRegistros;
        double lDoubleCantRegs = cantidadRegistros;
        double lPageInt = 0;
        double lPageDouble = 0;
        if (cantRegistroPorPagina != 0) {
            lPageInt = lIntCantRegs / cantRegistroPorPagina;
            lPageDouble = lDoubleCantRegs / cantRegistroPorPagina;
        }
        int lPage = (int) Math.floor(lPageDouble);
        if (lPageInt == lPageDouble) {
            cantidadPaginas = lPage;
        } else {
            cantidadPaginas = lPage + 1;
        }
        return cantidadPaginas;
    }

    /**
     * Modifica la cantidad de páginas
     *
     * @param pCantidadPaginas Cantidad de páginas
     */
    public void setCantidadPaginas(int pCantidadPaginas) {
        this.cantidadPaginas = pCantidadPaginas;
    }

    /**
     * Obtiene la página actual
     *
     * @return paginaActual Obtengo la página en la que me encuentro
     */
    public int getPaginaActual() {
        //Realizo el cálculo entre el registro en el que estoy contra
        if (cantidadRegistros <= 0) {
            return 0;
        }
        //La cantidad total de registros por página
        if (this.primerRegistro / this.cantRegistroPorPagina
                == this.primerRegistro) {
            paginaActual = this.primerRegistro / this.cantRegistroPorPagina;
        } else {
            paginaActual = this.primerRegistro / this.cantRegistroPorPagina + 1;
        }
        //Si la pagina actual es mayor a la cantidad de paginas le asigno el
        //varlo mayor
        if (paginaActual > cantidadPaginas) {
            paginaActual = cantidadPaginas;
        }
        //Si la pagina es cero, le asigno la primera
        if (paginaActual == 0) {
            paginaActual = 1;
        }
        return paginaActual;
    }

    /**
     * Modifica la página actual
     *
     * @param pPaginaActual Página actual
     */
    public void setPaginaActual(int pPaginaActual) {
        this.paginaActual = pPaginaActual;
    }

    /**
     * Obtiene la cantidad de registros mostrados
     *
     * @return calculo entre la posición del primer registro de la pagina y el
     * último de la pagina
     */
    public int getCantidadRegistrosMostrados() {
        if (cantidadRegistros <= 0) {
            return 0;
        } else {
            return this.getUltimoRegistro() - this.primerRegistro + 1;
        }
    }

    /**
     * Obtiene el primer registro de la última página
     */
    public void getPrimerRegistroUltimaPagina() {
        int lIntCantRegs = cantidadRegistros;// - 1;
        double lDoubleCantRegs = cantidadRegistros;
        double lPageInt = lIntCantRegs / cantRegistroPorPagina;
        double lPageDouble = lDoubleCantRegs / cantRegistroPorPagina;
        int lPage = (int) Math.floor(lPageDouble);
        if (lPageInt == lPageDouble) {
            primerRegistro = lPage * cantRegistroPorPagina;
        } else {
            primerRegistro = lPage * cantRegistroPorPagina + 1;
        }
    }

    /**
     * Obtiene el primer registro de una pagina
     */
    public void getPrimerRegistroPagina(int pNumeroPagina) {
        if (pNumeroPagina == 1) {
            this.setPrimerRegistro(1);
        } else {
            if (pNumeroPagina == this.getCantidadPaginas()) {
                this.getPrimerRegistroUltimaPagina();
            } else {
                this.primerRegistro = (pNumeroPagina * cantRegistroPorPagina)
                        - cantRegistroPorPagina + 1;
            }
        }
    }

    /**
     * Obtiene la lista de links de paginas
     *
     * @return arregloPaginas Lista de links
     */
    public Integer[] getArregloPaginas() {
        if (this.cantRegistroPorPagina >= this.cantidadRegistros) {
            this.arregloPaginas = new Integer[0];
            return this.arregloPaginas;
        } else {
            int lCantPags = 0;
            if (this.getCantidadPaginas() < cantidadPaginasLinks) {
                lCantPags = this.getCantidadPaginas() - 1;
            } else {
                if (this.getCantidadPaginas() == cantidadPaginasLinks) {
                    lCantPags = cantidadPaginasLinks - 1;
                } else {
                    lCantPags = cantidadPaginasLinks;
                }
            }
            int lPagina = 0;
            if (this.getPaginaActual() == 1) {
                lPagina = this.getPaginaActual() + 1;
            } else if (this.getPaginaActual() == this.getCantidadPaginas()) {
                lPagina = this.getCantidadPaginas() - lCantPags + 1;
            } else {
                lPagina = this.getPaginaActual() - (lCantPags / 2) - 1;
            }
            this.arregloPaginas = new Integer[lCantPags];
            if (lPagina <= 0) {
                lPagina = 1;
            }
            if ((lPagina + lCantPags) > this.getCantidadPaginas()) {
                lPagina -= (lPagina + lCantPags) - this.getCantidadPaginas();
                if (lPagina <= 0) {
                    lPagina = 1;
                }
            }
            int lIndice = 0;
            while (lIndice < lCantPags) {
                if (lPagina != this.getPaginaActual()) {
                    this.arregloPaginas[lIndice] = lPagina;
                    lIndice++;
                }
                lPagina++;
            }
            return arregloPaginas;
        }
    }

    /**
     * Modifica el valor del arreglo de paginas
     *
     * @param pArregloPaginas Lista de links de paginas
     */
    public void setArregloPaginas(Integer[] pArregloPaginas) {
        this.arregloPaginas = pArregloPaginas;
    }

    public List<SelectItem> getListaRegistrosPagina() {
        return listaRegistrosPagina;
    }

    public void setListaRegistrosPagina(List<SelectItem> listaRegistrosPagina) {
        this.listaRegistrosPagina = listaRegistrosPagina;
    }

    // </editor-fold>
}
