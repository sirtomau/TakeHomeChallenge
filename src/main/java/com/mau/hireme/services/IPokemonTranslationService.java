package com.mau.hireme.services;

import com.mau.hireme.domain.PokemonTranslation;

public interface IPokemonTranslationService {

    PokemonTranslation getTranslatedDescription(String name);

}
