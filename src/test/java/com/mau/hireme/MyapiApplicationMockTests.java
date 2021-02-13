package com.mau.hireme;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MyapiApplicationMockTests {

    private static final String MY_API_URI="http://localhost/api/pokemon/";

    @Autowired
    private MockMvc mockMvc;

    // We mock the clients that access the backend APIs ------
    // This type of test cases could be useful to test our internal logic (not much in this simple application though)
    // and the external APIs are either not ready or not available (ie build process running with no connectivity
    // to them).
    /*
    @MockBean
    IPokemonServiceClient pokemonServiceClient;
    @MockBean
    ITranslationServiceClient translationServiceClient;

     */
    // --------------------------------------------------------

    // Mocking the rest template we do not go out to ext apis,
    // but we just emulate that.
    @MockBean
    RestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void testTranslationOK() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(MY_API_URI+"charizard"))
                .andExpect(status().isOk()).andReturn();
               // .andExpect(content().json("[{\"id\":1,\"title\":\"djkfjsdhfdsk\",\"author\":\"jdjdj\",\"description\":null}]", true)).andReturn();

    }
}
