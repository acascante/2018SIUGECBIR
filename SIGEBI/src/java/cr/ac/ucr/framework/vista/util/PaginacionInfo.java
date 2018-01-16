package cr.ac.ucr.framework.vista.util;

/**
 *
 * @author Rochar
 */
public class PaginacionInfo {
    private int cantRegistroPorPagina = 10;
    private int primerRegistro = 0;
    private int cantidadRegistros = -1;
    private int cantidadPaginas = 0;
    private int paginaActual = 0;
    //Cantidad de links para paginado
    private int cantidadPaginasLinks = 4;
    //Arreglo de línks de páginas
    private Integer[] arregloPaginas = null;
    public void setCantRegistroPorPagina(int cantRegistroPorPagina) {
        this.cantRegistroPorPagina = cantRegistroPorPagina;
    }

    public int getCantidadPaginasLinks() {
        return cantidadPaginasLinks;
    }

    public void setCantidadPaginasLinks(int cantidadPaginasLinks) {
        this.cantidadPaginasLinks = cantidadPaginasLinks;
    }
    public int getCantRegistroPorPagina() {
        return cantRegistroPorPagina;
    }
    
    public int getCantidadRegistros() {
        return cantidadRegistros;
    }
    /**
     * Obtiene la lista de links de paginas
     * @return arregloPaginas Lista de links
     */
    public Integer[] getArregloPaginas() {
        if(this.cantRegistroPorPagina >= this.cantidadRegistros){
            this.arregloPaginas = new Integer[0];
            return this.arregloPaginas;
        }else{
            int lCantPags = 0;
            if(this.getCantidadPaginas() < cantidadPaginasLinks){
                lCantPags = this.getCantidadPaginas() - 1;
            }else{
                if(this.getCantidadPaginas() == cantidadPaginasLinks){
                    lCantPags = cantidadPaginasLinks  -1;
                }else{
                    lCantPags = cantidadPaginasLinks;
                }
            }            
            int lPagina = 0;
            if(this.getPaginaActual() == 1){
                lPagina = this.getPaginaActual() + 1;
            }else if(this.getPaginaActual() == this.getCantidadPaginas()){                
                lPagina = this.getCantidadPaginas() - lCantPags + 1;
            }else{
                lPagina = this.getPaginaActual() - (lCantPags/2)-1;
            }
            this.arregloPaginas = new Integer[lCantPags];
            if(lPagina <= 0){
                lPagina = 1;
            }
            if((lPagina+lCantPags)>this.getCantidadPaginas()){
                lPagina -= (lPagina+lCantPags) - this.getCantidadPaginas();
                if(lPagina <= 0){
                    lPagina = 1;
                }
            }
            int lIndice = 0;
            while(lIndice < lCantPags){
                if(lPagina != this.getPaginaActual()){
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
     * @param pArregloPaginas Lista de links de paginas
     */
    public void setArregloPaginas(Integer[] pArregloPaginas) {
        this.arregloPaginas = pArregloPaginas;
    }
    
    public void setCantidadRegistros(int itemCount) {
        this.cantidadRegistros = itemCount;
    }
    
    public int getPrimerRegistro() {
        if (cantidadRegistros == -1) {
            throw new IllegalStateException("itemCount must be set before invoking getFirstItem");
        }
        if (primerRegistro >= cantidadRegistros) {
            if (cantidadRegistros == 0) {
                primerRegistro = 0;
            } else {
                int zeroBasedItemCount = cantidadRegistros - 1;
                double pageDouble = zeroBasedItemCount / cantRegistroPorPagina;
                int page = (int) Math.floor(pageDouble);
                primerRegistro = page * cantRegistroPorPagina;
            }
        }
        return primerRegistro;
    }
    
    public void setPrimerRegistro(int firstItem) {
        this.primerRegistro = firstItem;
    }
    
    public int getUltimoRegistro() {
        getPrimerRegistro();
        return primerRegistro + cantRegistroPorPagina > cantidadRegistros ? cantidadRegistros : primerRegistro + cantRegistroPorPagina;
    }
    
    public void getSiguientePagina() {
        getPrimerRegistro();
        if (primerRegistro + cantRegistroPorPagina < cantidadRegistros) {
            primerRegistro += cantRegistroPorPagina+1;
        }
    }
    
    public void getPaginaAnterior() {
        getPrimerRegistro();
        primerRegistro -= cantRegistroPorPagina;
        if (primerRegistro < 0) {
            primerRegistro = 0;
        }
    }

    /**
     * @return the cantidadPaginas
     */
    public int getCantidadPaginas() {
        cantidadPaginas = this.cantidadRegistros/this.cantRegistroPorPagina;
        return cantidadPaginas;
    }

    /**
     * @param cantidadPaginas the cantidadPaginas to set
     */
    public void setCantidadPaginas(int cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }

    /**
     * @return the paginaActual
     */
    public int getPaginaActual() {
        paginaActual = this.primerRegistro/this.cantRegistroPorPagina + 1;
        return paginaActual;
    }

    /**
     * @param paginaActual the paginaActual to set
     */
    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }
}
