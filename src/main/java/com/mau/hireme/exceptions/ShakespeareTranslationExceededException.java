package com.mau.hireme.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class ShakespeareTranslationExceededException extends RuntimeException {
    public ShakespeareTranslationExceededException(String text) {
        super("'"+text+"' - not translated because the limit of free translations on the translation service has been exceeded, retry later");
    }
}
