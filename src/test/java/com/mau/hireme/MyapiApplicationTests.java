package com.mau.hireme;

import com.mau.hireme.exceptions.NoDescriptionForPokemonException;
import com.mau.hireme.exceptions.PokemonNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MyapiApplicationTests {

    private static final String MY_API_URI = "http://localhost/api/pokemon/";

    @Autowired
    private MockMvc mockMvc;

    private static final String responseForCharizard = "{\"name\":\"charizard\",\"description\":\"Charizard flies 'round " +
            "the sky in search of powerful opponents. 't breathes fire of such most wondrous heat yond 't melts aught. " +
            "However,  't nev'r turns its fiery breath on any opponent weaker than itself.\"}";

    @Test
    void contextLoads() {
    }

    @Test
    public void testTranslationOK() throws Exception {

        // This test case does not make sense because if pokeapi changes a single word in the description we are screwed.
        // And what is the point in testing the exact content.
        // In the same way we would have troubles if we use a specific pokemon name and they remove it.

        MvcResult mvcResult = mockMvc.perform(get(MY_API_URI + "charizard"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseForCharizard, false)).andReturn();

    }



    /*
    With this test case we are verifying that out app returns the expected error
     */
    @Test
    public void testBadPokemonName() throws Exception {

        String badName = "bad_input";
        String expectedErrorMessage = "Pokemon "+badName+" not found";

        MvcResult mvcResult = mockMvc.perform(get(MY_API_URI+ badName)
                .contentType(MediaType.ALL.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PokemonNotFoundException))
                .andExpect(result -> assertEquals(expectedErrorMessage, result.getResolvedException().getMessage())).andReturn();

    }
}
