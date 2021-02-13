package com.mau.hireme.integration.transapi;

import com.mau.hireme.HireMeApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TranslationServiceClient implements ITranslationServiceClient {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String translate(String text) {

        // Probably not best way to do it and usually done with
        // extra layers outside of the application.
        // But I thought it could be interesting to see it in
        // this sample app.
        HireMeApplication.translationapiInvocations++;

        return text + " - still to be translated";
    }

}
