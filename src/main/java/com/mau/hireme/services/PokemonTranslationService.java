package com.mau.hireme.services;

import com.mau.hireme.domain.PokemonTranslation;
import com.mau.hireme.integration.pokeapi.IPokemonServiceClient;
import com.mau.hireme.integration.transapi.ITranslationServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PokemonTranslationService implements IPokemonTranslationService {

    @Autowired
    private IPokemonServiceClient pokemonServiceClient;

    @Autowired
    private ITranslationServiceClient translationServiceClient;

    @Override
    @Cacheable("translations")
    // The Cacheable annotation reduces the amount of invocations of the backend services.
    // They provide quite static information anyway.
    public PokemonTranslation getTranslatedDescription(String name) {

        // The pokeapi uses this convention
        name = name.toLowerCase();

        String descrToBeTranslated = pokemonServiceClient.getDescription(name);

        String translatedDescr = translationServiceClient.translate(descrToBeTranslated);

        PokemonTranslation pokemonTranslation = new PokemonTranslation();
        pokemonTranslation.setName(name);
        pokemonTranslation.setDescription(translatedDescr);

        return pokemonTranslation;
    }
}
