package com.mau.hireme;

import com.mau.hireme.domain.PokemonTranslation;
import com.mau.hireme.exceptions.ShakespeareTranslationExceededException;
import com.mau.hireme.integration.pokeapi.IPokemonServiceClient;
import com.mau.hireme.integration.transapi.ITranslationServiceClient;
import com.mau.hireme.services.IPokemonTranslationService;
import com.mau.hireme.services.PokemonTranslationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@Import(value = {PokemonTranslationService.class})
public class ExampleOfUnitTest {

    @Autowired
    IPokemonTranslationService pokemonTranslationService;

    @MockBean
    IPokemonServiceClient pokemonServiceClient;

    @MockBean
    ITranslationServiceClient translationServiceClient;

    /*
    Since there is zero logic within the PokemonTranslationService
    the following test case does not do much sense in this context.
    It's mainly to show how to test classes in isolation mocking their
    dependencies.
     */
    @Test
    public void testAllOK() {
        String pokemonName = "charizard";
        String description = "whatever";
        String translation = "the expected translation";

        Mockito.when(pokemonServiceClient.getDescription(pokemonName)).thenReturn(description);
        Mockito.when(translationServiceClient.translate(description)).thenReturn(translation);

        PokemonTranslation pokemonTranslation = pokemonTranslationService.getTranslatedDescription(pokemonName);
        assertEquals(pokemonTranslation.getName(), "charizard");
        assertEquals(pokemonTranslation.getDescription(), translation);
    }

    /*
    Same as for the previous test case, this one is not really interesting either.
    Main thing it checks is that the service does not swallow the exception.
     */
    @Test
    public void test429() {
        String pokemonName = "charizard";
        String description = "whatever";

        Mockito.when(pokemonServiceClient.getDescription(pokemonName)).thenReturn(description);
        Mockito.when(translationServiceClient.translate(description)).thenThrow(new ShakespeareTranslationExceededException(description));

        try {
            PokemonTranslation pokemonTranslation = pokemonTranslationService.getTranslatedDescription(pokemonName);
        } catch(ShakespeareTranslationExceededException e) {
            return;
        }

        fail();
    }

}
