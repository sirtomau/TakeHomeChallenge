package com.mau.hireme.integration.pokeapi;

import com.mau.hireme.HireMeApplication;
import com.mau.hireme.exceptions.NoDescriptionForPokemonException;
import com.mau.hireme.exceptions.PokeApiCallFailedException;
import com.mau.hireme.exceptions.PokemonNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class PokemonServiceClient implements IPokemonServiceClient {

    private Logger logger = LoggerFactory.getLogger(PokemonServiceClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${hireme.integration.pokeapi.base-uri}/v2/pokemon-species/")
    private String uri;

    @Override
    public String getDescription(String name) {

        // Probably not best way to do it and often done with
        // extra layers outside of the application.
        // But I thought it could be interesting to see some stats in
        // this sample app's happy page.
        HireMeApplication.pokeapiInvocations++;

        // IMPORTANT: refer to the email sent to Oliver on 12/02/2020 with the
        // question about the location and cardinality of the pokemon's description.

        String target = uri+name;

        ResponseEntity<PokemonSpecies> response = null;

        try {
            response = restTemplate.getForEntity(target, PokemonSpecies.class);
        } catch(HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new PokemonNotFoundException(name);
            }
            logAndRethrowAsPokeApiCallFailedException(e);
        } catch (Exception e) {
            // Just in case we end up in other types of exceptions
            logAndRethrowAsPokeApiCallFailedException(e);
        }

        String description = null;

        if (response!=null && response.getStatusCode().is2xxSuccessful()) {

            PokemonSpecies pokemonSpecies =  response.getBody();

            final FlavorTextEntry[] flavorTextEntries = pokemonSpecies.getFlavorTextEntries();

            int maxLenght = 0;

            // IMPORTANT - following Oliver's indication I made my own assumption based on a way to
            // pick a description from the list (which for most of the pokemons is pretty long).
            // This approach gives the same result described in the take-home-challenge's document
            // for Charizard".
            for (FlavorTextEntry flavorTextEntry : flavorTextEntries) {
                if (Language.EN.equals(flavorTextEntry.getLanguage().getName()) && flavorTextEntry.getFlavorText().length() >= maxLenght) {
                    // In case two are of the same lenght I use the last one found, that is why I have >= and not >
                    maxLenght = flavorTextEntry.getFlavorText().length();
                    description = flavorTextEntry.getFlavorText();
                }
            }

            // In particular unlucky case (ie no description in English for a given pokemon)
            // we could get here with no description to translate.
            if (description==null) throw new NoDescriptionForPokemonException(name);

        } else {
            // We did not get successful response
            throw new PokeApiCallFailedException();
        }

        return description;
    }

    private void logAndRethrowAsPokeApiCallFailedException(Exception e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        throw new PokeApiCallFailedException();
    }

}
