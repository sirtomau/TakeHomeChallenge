package com.mau.hireme.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
// https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404
public class NoDescriptionForPokemonException extends RuntimeException {
    public NoDescriptionForPokemonException(String name) {
        super("No description available for pokemon "+name);
    }
}
