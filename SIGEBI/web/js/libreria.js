function reporte(idReporte, extension)
{
    var url = "reportes/" + idReporte + extension;
    ventana_nueva = window.open(url, "mywindow", "location=0,status=0,scrollbars=1,width=800,height=500,resizable=1,menubar=0");
    ventana_nueva.focus();
    return false;
}


//------agregado por manuel sanabria 

function deshabilitarBoton(elemento) {
    sessionStorage.setItem('idComponenteActual', elemento.id);
    var obj = document.getElementById(sessionStorage.getItem('idComponenteActual').toString());
    if (obj) {
        obj.style.visibility = "hidden";
    }
}
function habilitarBoton() {
    var obj = document.getElementById(sessionStorage.getItem('idComponenteActual').toString());

    if (obj) {
        obj.style.visibility = "visible";
    }
    sessionStorage.clear();
}
//-----------------------------------