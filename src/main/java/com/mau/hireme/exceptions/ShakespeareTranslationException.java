package com.mau.hireme.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
// https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/502
public class ShakespeareTranslationException extends RuntimeException {
    public ShakespeareTranslationException() {
        super("Problems invoking the Shakespeare translation service, try later");
    }
}
