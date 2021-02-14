package com.mau.hireme.integration.transapi;

import com.mau.hireme.HireMeApplication;
import com.mau.hireme.exceptions.ShakespeareTranslationExceededException;
import com.mau.hireme.exceptions.ShakespeareTranslationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class TranslationServiceClient implements ITranslationServiceClient {

    private Logger logger = LoggerFactory.getLogger(TranslationServiceClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${hireme.integration.shakespearetran-api.base-uri}/shakespeare.json")
    private String uri;

    @Value("${hireme.integration.shakespearetran-api.enabled:true}")
    private boolean enabled;

    @Override
    public String translate(String text) {

        if (enabled) {
            // Probably not best way to do it and usually done with
            // extra layers outside of the application.
            // But I thought it could be interesting to see it in
            // this sample app.
            HireMeApplication.translationapiInvocations++;


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("text", text);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            ResponseEntity<Translation> response;
            try {
                response = restTemplate.postForEntity(uri, request, Translation.class);
            } catch (RestClientException e) {

                if ((e instanceof HttpClientErrorException.TooManyRequests)) {
                    throw new ShakespeareTranslationExceededException(text);
                }

                // Whatever exception we get we map it into our own, so that it gets mapped into
                // our own error message protecting our end user from messages coming from backend systems
                logger.error("Error while invoking the Shakespeare translator: "+e.getLocalizedMessage());
                throw new ShakespeareTranslationException();
            }

            return response.getBody().getContents().getTranslated();
        } else {
            return "The translation for '"+text+"' did not happen because the access to the Shakespeare translation service is disabled";
        }

    }

}
