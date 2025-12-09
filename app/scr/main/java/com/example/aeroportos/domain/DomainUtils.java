package com.example.aeroportos.domain;

import java.util.Locale;
import java.util.Map;

public final class DomainUtils {

    private static final Map<String, String> PAIS_PARA_ISO = Map.of(
            "Brazil", "BR",
            "Brasil", "BR",
            "United States", "US",
            "Estados Unidos", "US",
            "Argentina", "AR",
            "Germany", "DE"
 
    );

    private DomainUtils() {}


    public static double converterPesParaMetros(double pes) {
        return pes * 0.3048;
    }


    public static String obterIsoPais(String nomePais) {
        if (nomePais == null) return null;
        String key = nomePais.trim();
        String iso = PAIS_PARA_ISO.get(key);
        if (iso != null) {
            return iso;
        }

        return key.toUpperCase(Locale.ROOT).substring(0, Math.min(2, key.length()));
    }
}
