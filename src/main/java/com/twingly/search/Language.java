package com.twingly.search;

/**
 * This enum contain all supported by Twingly languages
 *
 * @see "https://developer.twingly.com/resources/search-language/#supported-languages"
 */
public enum Language {
    Afrikaans("af"),
    Arabic("ar"),
    Bulgarian("bg"),
    Bengali("bn"),
    Catalan("ca"),
    Czech("cs"),
    Welsh("cy"),
    Danish("da"),
    German("de"),
    Greek("el"),
    English("en"),
    Spanish("es"),
    Estonian("et"),
    Persian("fa"),
    Finnish("fi"),
    French("fr"),
    Gujarati("gu"),
    Hebrew("he"),
    Hindi("hi"),
    Croatian("hr"),
    Hungarian("hu"),
    Indonesian("id"),
    Icelandic("is"),
    Italian("it"),
    Japanese("ja"),
    Georgian("ka"),
    Kannada("kn"),
    Korean("ko"),
    Lithuanian("lt"),
    Macedonian("mk"),
    Malayalam("ml"),
    Marathi("mr"),
    Nepali("ne"),
    Dutch("nl"),
    Norwegian("no"),
    Punjabi("pa"),
    Polish("pl"),
    Portuguese("pt"),
    Romanian("ro"),
    Russian("ru"),
    Slovak("sk"),
    Slovenian("sl"),
    Somali("so"),
    Albanian("sq"),
    Serbian("sr"),
    Swedish("sv"),
    Swahili("sw"),
    Tamil("ta"),
    Telugu("te"),
    Thai("th"),
    Tagalog("tl"),
    Turkish("tr"),
    Ukrainian("uk"),
    Urdu("ur"),
    Vietnamese("vi"),
    Chinese("zh");
    private String isoCode;

    Language(String isoCode) {
        this.isoCode = isoCode;
    }

    public static Language fromStringRepresentation(String isoCode) {
        for (Language language : values()) {
            if (language.isoCode.equalsIgnoreCase(isoCode)) {
                return language;
            }
        }
        throw new IllegalArgumentException(String.format("Language with ISO code=%s is not currently supported.", isoCode));
    }

    public String toStringRepresentation() {
        return isoCode;
    }
}
