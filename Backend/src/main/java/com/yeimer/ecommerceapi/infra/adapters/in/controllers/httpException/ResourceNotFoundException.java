package com.yeimer.ecommerceapi.infra.adapters.in.controllers.httpException;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
