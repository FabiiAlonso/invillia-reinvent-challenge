package com.invillia.reinvent.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super("RESOURCE NOT FOUND - 404");
    }

    public ResourceNotFoundException(Object sku){
        super("There is no product on shopping cart for the given SKU: " + sku );
    }
}
