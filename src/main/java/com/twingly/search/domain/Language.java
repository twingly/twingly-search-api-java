package com.twingly.search.domain;

/**
 * This enum contain all supported by Twingly languages
 *
 * @see <a href="https://developer.twingly.com/resources/search-language/#supported-languages">Supported languages</a>
 */
public enum Language {
    /**
     * Afrikaans language.
     */
    Afrikaans("af"),
    /**
     * Arabic language.
     */
    Arabic("ar"),
    /**
     * Bulgarian language.
     */
    Bulgarian("bg"),
    /**
     * Bengali language.
     */
    Bengali("bn"),
    /**
     * Catalan language.
     */
    Catalan("ca"),
    /**
     * Czech language.
     */
    Czech("cs"),
    /**
     * Welsh language.
     */
    Welsh("cy"),
    /**
     * Danish language.
     */
    Danish("da"),
    /**
     * German language.
     */
    German("de"),
    /**
     * Greek language.
     */
    Greek("el"),
    /**
     * English language.
     */
    English("en"),
    /**
     * Spanish language.
     */
    Spanish("es"),
    /**
     * Estonian language.
     */
    Estonian("et"),
    /**
     * Persian language.
     */
    Persian("fa"),
    /**
     * Finnish language.
     */
    Finnish("fi"),
    /**
     * French language.
     */
    French("fr"),
    /**
     * Gujarati language.
     */
    Gujarati("gu"),
    /**
     * Hebrew language.
     */
    Hebrew("he"),
    /**
     * Hindi language.
     */
    Hindi("hi"),
    /**
     * Croatian language.
     */
    Croatian("hr"),
    /**
     * Hungarian language.
     */
    Hungarian("hu"),
    /**
     * Indonesian language.
     */
    Indonesian("id"),
    /**
     * Icelandic language.
     */
    Icelandic("is"),
    /**
     * Italian language.
     */
    Italian("it"),
    /**
     * Japanese language.
     */
    Japanese("ja"),
    /**
     * Georgian language.
     */
    Georgian("ka"),
    /**
     * Kannada language.
     */
    Kannada("kn"),
    /**
     * Korean language.
     */
    Korean("ko"),
    /**
     * Lithuanian language.
     */
    Lithuanian("lt"),
    /**
     * Macedonian language.
     */
    Macedonian("mk"),
    /**
     * Malayalam language.
     */
    Malayalam("ml"),
    /**
     * Marathi language.
     */
    Marathi("mr"),
    /**
     * Nepali language.
     */
    Nepali("ne"),
    /**
     * Dutch language.
     */
    Dutch("nl"),
    /**
     * Norwegian language.
     */
    Norwegian("no"),
    /**
     * Punjabi language.
     */
    Punjabi("pa"),
    /**
     * Polish language.
     */
    Polish("pl"),
    /**
     * Portuguese language.
     */
    Portuguese("pt"),
    /**
     * Romanian language.
     */
    Romanian("ro"),
    /**
     * Russian language.
     */
    Russian("ru"),
    /**
     * Slovak language.
     */
    Slovak("sk"),
    /**
     * Slovenian language.
     */
    Slovenian("sl"),
    /**
     * Somali language.
     */
    Somali("so"),
    /**
     * Albanian language.
     */
    Albanian("sq"),
    /**
     * Serbian language.
     */
    Serbian("sr"),
    /**
     * Swedish language.
     */
    Swedish("sv"),
    /**
     * Swahili language.
     */
    Swahili("sw"),
    /**
     * Tamil language.
     */
    Tamil("ta"),
    /**
     * Telugu language.
     */
    Telugu("te"),
    /**
     * Thai language.
     */
    Thai("th"),
    /**
     * Tagalog language.
     */
    Tagalog("tl"),
    /**
     * Turkish language.
     */
    Turkish("tr"),
    /**
     * Ukrainian language.
     */
    Ukrainian("uk"),
    /**
     * Urdu language.
     */
    Urdu("ur"),
    /**
     * Vietnamese language.
     */
    Vietnamese("vi"),
    /**
     * Chinese language.
     */
    Chinese("zh");
    /**
     * Represents two-letter <a href="https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes#Partial_ISO_639_table">ISO 639-1</a> code
     */
    private final String isoCode;

    Language(String isoCode) {
        this.isoCode = isoCode;
    }

    /**
     * Create language enum from iso code representation.
     *
     * @param isoCode the iso code
     * @return the language with given ISO code or null, if no language for ISO code was found
     */
    public static Language fromIsoCode(String isoCode) {
        for (Language language : values()) {
            if (language.isoCode.equalsIgnoreCase(isoCode)) {
                return language;
            }
        }
        return null;
    }

    /**
     * Gets iso code.
     *
     * @return the iso code
     */
    public String getIsoCode() {
        return isoCode;
    }
}
