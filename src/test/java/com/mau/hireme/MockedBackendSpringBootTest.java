package com.mau.hireme;

import com.mau.hireme.exceptions.ShakespeareTranslationExceededException;
import com.mau.hireme.integration.pokeapi.IPokemonServiceClient;
import com.mau.hireme.integration.transapi.ITranslationServiceClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
/*
The test cases in this class are testing the entire application except for the
two client classes invoking the backend services.
These two classes are mockec and we can trigger any type of response we want.
 */
class MockedBackendSpringBootTest {

    private static final String MY_API_URI = "http://localhost/pokemon/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IPokemonServiceClient pokemonServiceClient;

    @MockBean
    ITranslationServiceClient translationServiceClient;

    private final String pokemonName = "charizard";
    private final String description = "whatever";
    private final String translation = "the expected translation";

    @Test
    public void testAllGood() throws Exception {

        Mockito.when(pokemonServiceClient.getDescription(pokemonName)).thenReturn(description);
        Mockito.when(translationServiceClient.translate(description)).thenReturn(translation);

        MvcResult mvcResult = mockMvc.perform(get(MY_API_URI + pokemonName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(pokemonName))
                .andExpect(jsonPath("$.description").value(translation)).andReturn();
        System.out.println("XXXXXXXXXXXXXXXXXXXXX"+mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void test429() throws Exception {

        Mockito.when(pokemonServiceClient.getDescription(pokemonName)).thenReturn(description);
        Mockito.when(translationServiceClient.translate(description)).thenThrow(new ShakespeareTranslationExceededException(description));

        mockMvc.perform(get(MY_API_URI + pokemonName))
                .andExpect(status().isTooManyRequests())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ShakespeareTranslationExceededException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().indexOf("- not translated because " +
                        "the limit of free translations on the translation service has been exceeded")!=-1));

    }


}
