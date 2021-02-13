package com.mau.hireme.mvc.api.rest.open;

import com.mau.hireme.HireMeApplication;
import com.mau.hireme.domain.PokemonTranslation;
import com.mau.hireme.services.IPokemonTranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonTranslationController {

    private Logger logger = LoggerFactory.getLogger(PokemonTranslationController.class);

    @Autowired
    IPokemonTranslationService pokemonTranslationService;

    @GetMapping("/{name}")
    public PokemonTranslation getTranslatedDescription(@PathVariable String name) {

        // Probably not best way to do it and usually done with
        // extra layers outside of the application.
        // But I thought it could be interesting to see it in
        // this sample app's health page.
        HireMeApplication.translationServiceInvocations++;

        return pokemonTranslationService.translate(name);
    }
}