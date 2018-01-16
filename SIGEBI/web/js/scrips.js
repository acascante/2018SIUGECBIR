
function contarCaracteresTexto(text , desplegarArea, maximoCaracteres){
    if(text.value.length > maximoCaracteres){ 
        text.value =text.value.substring(0,maximoCaracteres);
    }
    else{
        desplegarArea.innerHTML = text.value.length +" caracteres de "+maximoCaracteres+" máximo.";
    }
}
function soloNumeros(evt){
    //asignamos el valor de la tecla a keynum
    //llamar con onkeypress="return soloNumeros(event);"
    if(window.event){// IE
        keynum = evt.keyCode;
    } else {
        keynum = evt.which;
    }
    //comprobamos si se encuentra en el rango
    if(keynum>47 && keynum<58) {
        return true;
    } else {
        if(keynum==8 || keynum == 192 || keynum == 37 || keynum == 39
            || keynum == 32 || keynum == 8
            || keynum == 13) {
            return true;
        } else {
            if(keynum==44) {
                return true
            } else {
                return false;    
            }
        }
    }   
}

function convertirMayuscula(componente) {
    componente.value=componente.value.toString().toUpperCase();
}



