package com.mau.hireme.mvc.api.rest.open;

import com.mau.hireme.HireMeApplication;
import com.mau.hireme.domain.PokemonTranslation;
import com.mau.hireme.services.IPokemonTranslationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
@Tag(name = "Pokemon", description = "Take home challenge exercise")
public class PokemonTranslationController {

    private Logger logger = LoggerFactory.getLogger(PokemonTranslationController.class);

    @Autowired
    IPokemonTranslationService pokemonTranslationService;

    @GetMapping("/{name}")
    // The following two annotations are mainly for documentation purpose,
    // they will make the generated openapi documentation more readable and useful
    @Operation (summary = "Get the Pokemon description in Shakespearean style")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully translated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PokemonTranslation.class)) }),
            @ApiResponse(responseCode = "404", description = "Pokemon name not found",
                    content = @Content),
            @ApiResponse(responseCode = "429", description = "The amount of free requests to the backend translation service has been exceeded, retry later",
                    content = @Content),
            @ApiResponse(responseCode = "502", description = "Problems invoking the backend APIs",
                    content = @Content) })
    public PokemonTranslation getTranslatedDescription(@PathVariable String name) {

        // Probably not best way to do it and usually done with
        // extra layers outside of the application.
        // But I thought it could be interesting to see it in
        // this sample app's health page.
        HireMeApplication.translationServiceInvocations++;

        return pokemonTranslationService.getTranslatedDescription(name);
    }
}