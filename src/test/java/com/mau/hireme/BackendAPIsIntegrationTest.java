package com.mau.hireme;

import com.mau.hireme.exceptions.PokemonNotFoundException;
import org.junit.jupiter.api.Disabled;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
// THE EXECUTION OF THIS TEST CASE SHOULD BE SKIPPED IN THOSE ENVIRONMENTS
// AND IN GENERAL WHEN YOU DO NOT WANT TO RUN INTEGRATION TESTING.
// IT COULD POTENTIALLY BE UNSTABLE BECAUSE IT DEPENDS ON EXTERNAL SYSTEMS.
// PROBABLY NOT A VERY USEFUL TEST CASE, but here to show that it is feasible!!
class BackendAPIsIntegrationTest {

    private static final String MY_API_URI = "http://localhost/pokemon/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Disabled("Enable it if you want to see it running with real integration")
    public void testTranslationOKAndCorrectJSONFormat() throws Exception {

        // No point in checking the content of the description since the pokeapi could change it any time

        MvcResult mvcResult = mockMvc.perform(get(MY_API_URI + "charizard")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // 429 is returned when we exceed the number of free translations
        assertTrue((status==200)||(status==429));
        if (status==200) assertTrue(mvcResult.getResponse().getContentAsString().length() > 0);
    }

    /*
    With this test case we are verifying that out app returns the expected error
     */
    @Test
    @Disabled("Enable it if you want to see it running with real integration")
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
