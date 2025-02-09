package br.com.fiap.pedido.shared.validations;

public class StringValidator {

    public static boolean isNullOrEmpty(String value){
        return value == null || value.isBlank();
    }

}
