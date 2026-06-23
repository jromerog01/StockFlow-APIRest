package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.exceptions.NombreInvalidoException;
import org.springframework.stereotype.Component;

@Component
public class MetodosAuxiliares {

    public static boolean validarPalabra(String nombre){
        if (nombre.isEmpty() || nombre.equals(" ")){
            throw new NombreInvalidoException("El nombre ingresado no puede estar vacio");
        }
        return true;
    }

    public static String capitalizarPalabra(String palabra){
        String resto = palabra.substring(1).toLowerCase();
        char primera = Character.toUpperCase(palabra.charAt(0));

        return primera + resto;
    }

}
