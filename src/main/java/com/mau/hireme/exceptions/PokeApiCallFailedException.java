package com.mau.hireme.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class PokeApiCallFailedException extends RuntimeException {

    public PokeApiCallFailedException() {
        super("Issues invoking pokeapi");
    }

}
