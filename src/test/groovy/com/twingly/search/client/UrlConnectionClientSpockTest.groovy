package com.twingly.search.client

import com.twingly.search.Constants
import com.twingly.search.QueryBuilder
import com.twingly.search.domain.Coordinate
import com.twingly.search.domain.Error
import com.twingly.search.domain.Result
import com.twingly.search.exception.TwinglySearchClientException
import com.twingly.search.exception.TwinglySearchErrorException
import com.twingly.search.exception.TwinglySearchException
import com.twingly.search.exception.TwinglySearchServerException
import spock.lang.Specification

import javax.net.ssl.HttpsURLConnection
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.UnmarshalException
import javax.xml.bind.Unmarshaller
import java.nio.file.Paths
import java.text.SimpleDateFormat

class UrlConnectionClientSpockTest extends Specification {
    private static final String API_KEY = "apiKey"
    def packagePath = Paths.get("com", "twingly", "search", "client")
    def sdf = new SimpleDateFormat(Constants.DATE_FORMAT)
    def jaxbContext = Mock(JAXBContext)
    def unmarschaller = Mock(Unmarshaller)
    def urlStreamHandler
    def urlConnection = Mock(HttpsURLConnection)
    def inputStream = Mock(InputStream)
    def client = Spy(UrlConnectionClient, constructorArgs: [API_KEY])

    def setup() {
        jaxbContext.createUnmarshaller() >> unmarschaller
        urlStreamHandler = new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) throws IOException {
                urlConnection
            }
        }
        urlConnection.getInputStream() >> inputStream
    }

    def "should throw TwinglySearchException when API key is not set in System properties"() {
        when:
        new UrlConnectionClient()
        then:
        def ex = thrown(TwinglySearchException)
        ex.message.contains("Api key missing")
    }

    def "should create client with API key from System properties"() {
        given:
        System.setProperty(Constants.TWINGLY_API_KEY_PROPERTY, API_KEY)
        when:
        new UrlConnectionClient()
        then:
        notThrown(TwinglySearchException)
        cleanup:
        System.properties.remove(Constants.TWINGLY_API_KEY_PROPERTY)
    }

    def "should set compression"() {
        when:
        client.setIsCompressionEnabled(false)
        then:
        !client.isCompressionEnabled()
    }

    def "should make request with given Query"() {
        given:
        def expectedQueryString = "https://api.twingly.com/blog/search/api/v3/search?apiKey=apiKey&q=searchQuery"
        def q = QueryBuilder.create("searchQuery").build()
        when:
        def result = client.makeRequest(q)
        then:
        1 * client.getUrl(expectedQueryString) >> new URL(null, expectedQueryString, urlStreamHandler)
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as BufferedReader) >> new Result()
        result != null
    }

    def "should make request with given q"() {
        given:
        def expectedQueryString = "https://api.twingly.com/blog/search/api/v3/search?apiKey=apiKey&q=searchQuery"
        def q = "searchQuery"
        when:
        def result = client.makeRequest(q)
        then:
        1 * client.getUrl(expectedQueryString) >> new URL(null, expectedQueryString, urlStreamHandler)
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as BufferedReader) >> new Result()
        result != null
    }

    def "should parse incomplete result"() {
        given:
        def is = resourceInputStream("incomplete_result.xml")
        when:
        def result = is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        notThrown(Throwable)
        result.numberOfMatchesReturned == 0
        result.numberOfMatchesTotal == 0
        result.secondsElapsed == 0.203d
        result.posts.size() == 0
        result.incompleteResult
    }

    def "should parse valid links result"() {
        given:
        def is = resourceInputStream("valid_links_result.xml")
        when:
        def result = is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        notThrown(Throwable)
        result.numberOfMatchesReturned == 1
        result.numberOfMatchesTotal == 29383
        result.secondsElapsed == 0.413d
        result.posts.size() == 1
        !result.incompleteResult
        result.posts[0].id == "15420612957445610214"
        result.posts[0].author == "Cornucopia?"
        result.posts[0].url == "http://cornucopia.cornubot.se/2017/02/har-ryssland-brutit-mot.html"
        result.posts[0].title == "Har Ryssland brutit mot kärnvapenprovstoppet?"
        result.posts[0].text == "USA verkar ha ombaserat sitt ena i kärnvapenprovdetekteringsplan Constant Phoenix till Europa. Samtidigt\n" +
                "            har nivåer av den med mänskligt ursprung joniserande isotopen Jod-131 detekterats, ursprungligen i\n" +
                "            Nordnorge, som ligger närmast Rysslands gamla kärnvapentestområden i Novaja Semlja. Det väcker frågan om\n" +
                "            Ryssland börjat testa kärnvapen igen? Sovjetunionens första kärnvapenprov.Jod-131 har mänskligt ursprung,\n" +
                "            och är en isotop som uppstår vid detonation av Uran-235 eller plutoniumbaserade kärnvapen. Den har nu\n" +
                "            uteslutande i partikelform detekterats i inledningsvis Nordnorge närmast gränsen mot Ryssland enligt franska\n" +
                "            strålskyddsinstitutet och sedan bland annat i norra Finland och ner över resten av Europa. Nordnorge är som\n" +
                "            bekant närmast Rysslands gamla kärnvapenprovområden vid t ex Novaja Semlja. Jod-131 har en kort\n" +
                "            halveringstid och ursprunget ligger därför i närtid. Ursprunget för isotoperna är i detta fall än så länge\n" +
                "            okänt, och det är också ovanligt att de endast förekommer i partikelform och inte även i gasform. Samtidigt\n" +
                "            verkar nu USA ombaserat en av sina två kärnvapenprovdetekteringsplan Constant Phoenix till Wales.One of the\n" +
                "            two WC-135C CONSTANT PHOENIX Nuclear detonation research aircraft heading towards Wales. Rebasing to UK?\n" +
                "            pic.twitter.com/2P4IDmovzH— S2 Intel (@Strat2Intel) February 17, 2017Flygplanstypen används för att uppe i\n" +
                "            atmosfären detektera spår av kärnvapendetonationer, till skillnad mot mätstationerna som det franska\n" +
                "            strålskyddsinstitutet rapporterar om, som är markbaserade. Det är inte orimligt att man vill söka av högre\n" +
                "            atmosfär efter spår av ett kärnvapenprov. Ryssland håller på att uppgradera sina kärnvapen till nya\n" +
                "            toppmoderna robotar och det är inte orimligt att man i samband med detta också tar fram nya stridsspetsar\n" +
                "            och inte bara bärare. Med tanke på att Ryssland redan övergett ett antal nedrustnings- och fredsavtal,\n" +
                "            åtminstone sex stycken, inklusive avtalet mot markbaserade medeldistansrobotar, så är det inte otänkbart att\n" +
                "            man nu också övergett kärnvapenprovstoppsavtalet. Det handlar i så fall om en underjordisk detonation, då\n" +
                "            den inte detekterats av satelliter. Frågan är också vad för styrka och på vilket djup. Söker man hos USGS\n" +
                "            finns det inga jordbävningar med magnitud 2.5+ detekterade i norra Ryssland tillbaka till november 2016\n" +
                "            annat än en jordbävning utanför Tiksi. Av Sovjetunionens två främsta provplatser är det bara Novaja Semlja\n" +
                "            som är kvar inom den Ryska Federationen. Man gjorde undantaget Novaja Semlja främst sina 1000+\n" +
                "            provsprängingar i andra sovjetrepubliker än Ryssland. Det finns ett omfattande nätverk av 170 seismiska\n" +
                "            detektorer som ska fånga upp kärnvapenprov, samt 80 stationer av den typ som detekterat Jod-131. Även om\n" +
                "            ursprunget för utsläppet av Jod-131 fortfarande är okänt, så gör frånvaron av seimiska utslag det\n" +
                "            osannolikt, men inte omöjligt, att Ryssland brutit mot provstoppet. Dock är Kreml medvetna om de seismiska\n" +
                "            detektorerna, vilket väcker frågan om det alls är möjligt att givet kunskapen ändå kunna göra test med t ex\n" +
                "            taktiska små laddningar, t ex de som ska finnas i markrobotavtalbrytande Iskander-M utan att det detekteras?\n" +
                "            Oavsett finns det någon anledning till att Constant Phoenix ombaserats till Europa. Sannolikt handlar det om\n" +
                "            detekterad Jod-131.\n" +
                "        "
        result.posts[0].languageCode == "sv"
        result.posts[0].locationCode == "se"
        result.posts[0].coordinates == new Coordinate()
        result.posts[0].links == [
                "\n" +
                        "                https://1.bp.blogspot.com/-4uNjjiNQiug/WKguo1sBxwI/AAAAAAAAqKE/_eR7cY8Ft3cd2fYCx-2yXK8AwSHE_A2GgCLcB/s1600/aaea427ee3eaaf8f47d650f48fdf1242.jpg\n" +
                        "            ",
                "\n" +
                        "                http://www.irsn.fr/EN/newsroom/News/Pages/20170213_Detection-of-radioactive-iodine-at-trace-levels-in-Europe-in-January-2017.aspx\n" +
                        "            ",
                "https://www.t.co/2P4IDmovzH",
                "https://www.twitter.com/Strat2Intel/status/832710701730844672"
        ]
        result.posts[0].tags == ["Frankrike", "försvar", "Ryssland", "USA", "vetenskap"]
        result.posts[0].images == []
        result.posts[0].indexedAt == sdf.parse("2017-02-18T13:12:03Z")
        result.posts[0].publishedAt == sdf.parse("2017-02-18T10:26:00Z")
        result.posts[0].reindexedAt == sdf.parse("2017-02-22T15:58:31Z")
        result.posts[0].inlinksCount == 15
        result.posts[0].blogId == "12072801357614410355"
        result.posts[0].blogName == "Cornucopia?"
        result.posts[0].blogUrl == "http://cornucopia.cornubot.se"
        result.posts[0].blogRank == 9
        result.posts[0].authority == 1591
    }

    def "should parse valid empty result"() {
        given:
        def is = resourceInputStream("valid_empty_result.xml")
        when:
        def result = is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        notThrown(Throwable)
        result.numberOfMatchesReturned == 0
        result.numberOfMatchesTotal == 0
        result.secondsElapsed == 0.203d
        result.posts.size() == 0
        !result.incompleteResult
    }

    def "should throw exception for undefined error result"() {
        given:
        def is = resourceInputStream("undefined_error_result.xml")
        when:
        is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        def ex = thrown(TwinglySearchServerException)
        ex?.error?.code == "50001"
        ex?.error?.message == "Internal Server Error"
    }

    def "should throw exception for unauthorized api key result"() {
        given:
        def is = resourceInputStream("unauthorized_api_key_result.xml")
        when:
        is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        def ex = thrown(TwinglySearchClientException)
        ex?.error?.code == "40101"
        ex?.error?.message == "Unauthorized"
    }

    def "should throw exception for service unavailable result"() {
        given:
        def is = resourceInputStream("service_unavailable_result.xml")
        when:
        is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        def ex = thrown(TwinglySearchServerException)
        ex?.error?.code == "50301"
        ex?.error?.message == "Authentication service unavailable"
    }

    def "should throw exception for non existent api key result"() {
        given:
        def is = resourceInputStream("nonexistent_api_key_result.xml")
        when:
        is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        def ex = thrown(TwinglySearchClientException)
        ex?.error?.code == "40001"
        ex?.error?.message == "Parameter apikey may not be empty"
    }

    def "should throw exception for non xml result"() {
        given:
        def is = resourceInputStream("non_xml_result.xml")
        when:
        is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        def ex = thrown(TwinglySearchException)
        ex.message == "Unable to process request"
        assert ex.cause instanceof UnmarshalException
    }

    def "should parse minimal valid result"() {
        given:
        def is = resourceInputStream("minimal_valid_result.xml")
        when:
        def result = is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        result.numberOfMatchesReturned == 3
        result.numberOfMatchesTotal == 3122050
        result.secondsElapsed == 0.369d
        result.posts.size() == 3
        !result.incompleteResult
        result.posts[0].id == "16405819479794412880"
        result.posts[0].author == "klivinihemligheten"
        result.posts[0].url == "http://nouw.com/klivinihemligheten/planering---men-dalig-30016048"
        result.posts[0].title == "Planering - men dålig"
        result.posts[0].text == "Det vart en förmiddag på boxen med en brud som jag lärt känna där. Körde en egen WOD, bland annat SDHP,\n" +
                "            shoulder press, HSPU - bland annat. Hade planerat dagen in i minsta detalj, insåg under passet att jag glömt\n" +
                "            leggings. Så - det var bara att sluta lite tidigare för att röra sig hemåt för dusch och lunch. Har alltså\n" +
                "            släpat med mig ryggsäcken med allt för dagen i onödan. Riktigt riktigt klantigt! Har nu en timma på mig att\n" +
                "            duscha och göra mig ordning för föreläsning, innan det är dags att dra igen. Och jag som skulle plugga innan\n" +
                "        "
        result.posts[0].languageCode == "sv"
        result.posts[0].locationCode == "se"
        result.posts[0].coordinates == new Coordinate("49.1", "10.75")
        result.posts[0].links == []
        result.posts[0].tags == ["Ätas & drickas", "Universitet & studentlivet", "Träning", "To to list"]
        result.posts[0].images == []
        result.posts[0].indexedAt == sdf.parse("2017-05-04T06:51:23Z")
        result.posts[0].publishedAt == sdf.parse("2017-05-04T06:50:59Z")
        result.posts[0].reindexedAt == sdf.parse("2017-05-04T08:51:23Z")
        result.posts[0].inlinksCount == 0
        result.posts[0].blogId == "5312283800049632348"
        result.posts[0].blogName == "Love life like a student"
        result.posts[0].blogUrl == "http://nouw.com/klivinihemligheten"
        result.posts[0].blogRank == 1
        result.posts[0].authority == 0

        result.posts[1].id == "4331268749726303987"
        result.posts[1].author == "berggrenjulia"
        result.posts[1].url == "http://nouw.com/berggrenjulia/sometimes-the-king-is-a-queen-30014929"
        result.posts[1].title == "Sometimes the king is a queen"
        result.posts[1].text == "Dress / Jumpsuit Hej kompisar! Jag satte ihop två söta plagg till er. Himla gölliga! Jag kan inte fatta\n" +
                "            att det är torsdag idag, det är ju helt sjukt. Vid lunch skall jag till läkaren och W följer med mig pga är\n" +
                "            så rädd för läkaren, får brutal ångest och tror att jag skall bli dödförklarad. Riktigt hypokondrisk blir\n" +
                "            man visst med åren. Usch! Känslan när man går därifrån gör dock att det känns värt det. I helgen funderar vi\n" +
                "            på att gå till Liseberg för det skall bli magiskt väder. Vilken tur för alla bal-peppade kompisar på\n" +
                "            Marstrand! Åh dom kommer ha det fantastiskt kul tror jag. För min egen del måste jag erkänna att jag för\n" +
                "            första gången just nu känner att det skall bli skönt att inte gå. Att få slippa hetsen runt omkring då jag\n" +
                "            blir lite stressad bara av att tänka på det. Har verkligen bromsat ner mitt fest-mode rejält och inte varit\n" +
                "            ute och klubbat på superlänge, är inte speciellt lockad alls. Det är alltid samma visa också, så man kan ju\n" +
                "            trösta sig med att man inte missar någonting. Hur ser eran helg ut? Puss!\n" +
                "        "
        result.posts[1].languageCode == "sv"
        result.posts[1].locationCode == "se"
        result.posts[1].coordinates == new Coordinate()
        result.posts[1].links == ["http://www.mtpc.se/tags/link/1008098", "http://www.mtpc.se/tags/link/1008099"]
        result.posts[1].tags == ["Inspiration", "Mode", "Vardag"]
        result.posts[1].images == []
        result.posts[1].indexedAt == sdf.parse("2017-05-04T06:51:23Z")
        result.posts[1].publishedAt == sdf.parse("2017-05-04T06:50:00Z")
        result.posts[1].reindexedAt == sdf.parse("2017-05-04T08:51:23Z")
        result.posts[1].inlinksCount == 0
        result.posts[1].blogId == "9763611270004865418"
        result.posts[1].blogName == "berggrenjulia blogg"
        result.posts[1].blogUrl == "http://nouw.com/berggrenjulia"
        result.posts[1].blogRank == 1
        result.posts[1].authority == 5

        result.posts[2].id == "2770252465384762934"
        result.posts[2].author == "maartiinasvardag"
        result.posts[2].url == "http://nouw.com/maartiinasvardag/god-formiddag-30016041"
        result.posts[2].title == "God förmiddag! ☀️"
        result.posts[2].text == "Hmm.... Vad ska man börja ?? Jag vet inte riktigt vad min gnista till att blogga har tagit vägen. Jag har\n" +
                "            egentligen massor att skriva om, men ändå blir det inte av att jag gör det. Varför är det så? Någon som\n" +
                "            känner likadant. Kan berätta lite om förra helgen iaf, jag & R åkte till Skövde en sväng på fredagen,\n" +
                "            det blev en hel dag där. Blev en hel del shoppande för oss båda, bilder kommer i ett annat inlägg, då jag\n" +
                "            sitter vid plattan i skrivandets stund. Lördagen var jag hemma med töserna medans R jobbade några timmar.\n" +
                "            Sen blev det bara en lugn kväll hemma. Söndagen så var det dags för mig att jobba, ett dygnspass. ✌️Var en\n" +
                "            lugn kväll på jobbet. På morgonen (måndag) så när jag kommer upp, yrvaken som man är innan första koppen\n" +
                "            kaffe är intagen. Möts jag av att en klient utbrister: Vad glad jag är av att se dig! Detta värmde mitt\n" +
                "            hjärta så oerhört mycket & jag var på strålande humör hela dagen. ❤️ För då vet man att man gör ett bra\n" +
                "            jobb & att man gör rätt för den enskilde klientens behov. Jag älskar mitt jobb, även om jag ibland\n" +
                "            tycker att det är väldigt tufft, men när man får bekräftat att man gjort ett bra jobb, då glömmer man allt\n" +
                "            som är jobbigt. Tisdagen tillbringade jag med att göra ingenting typ, var bara hemma med töserna, solade\n" +
                "            & busade. Satt på baksidan & tjötade med Jonna vid staketet. Ulrika var förbi på en kopp kaffe med\n" +
                "            innan det var dags för henne att åka & jobba. På kvällen blev det sällskapsspel med Nina & Jonna.\n" +
                "            Mycket trevligt. Onsdag blev det lite grejande hemma med att tvätta & plocka lite, tjöta med Jonna i\n" +
                "            vanlig ordning vid staketet. På kvällen blev det ett gympass med Nina & Jonna. ✌️Sen blev det soffan för\n" +
                "            mig & kolla klart på serien Tretton skäl varför. En bra med tung serie där en tjej berättar varför hon\n" +
                "            valde att ta sitt liv. ☹️ Det som skrämmer mig är att det är så här verkligheten ser ut, när det sprids så\n" +
                "            mycket hat. Hur vore det om man började sprida mer kärlek till varandra, acceptera att vi alla är olika\n" +
                "            m.m.? Ja det är nog en fråga som vi aldrig kommer att få svar på. Idag blir det att åka in till stan under\n" +
                "            dagen på lite ärenden, annars är det de gamla vanliga på schemat. Vardags sysslor & R kommer hem, ingen\n" +
                "            är gladare än jag & töserna att få hem honom. ❤️ Önskar er alla en toppen dag! ☀️\n" +
                "        "
        result.posts[2].languageCode == "sv"
        result.posts[2].locationCode == "se"
        result.posts[2].coordinates == new Coordinate()
        result.posts[2].links == []
        result.posts[2].tags == []
        result.posts[2].images == []
        result.posts[2].indexedAt == sdf.parse("2017-05-04T06:50:07Z")
        result.posts[2].publishedAt == sdf.parse("2017-05-04T06:49:50Z")
        result.posts[2].reindexedAt == sdf.parse("0001-01-01T00:00:00Z")
        result.posts[2].inlinksCount == 0
        result.posts[2].blogId == "1578135310841173675"
        result.posts[2].blogName == "maartiinasvardag blogg"
        result.posts[2].blogUrl == "http://nouw.com/maartiinasvardag"
        result.posts[2].blogRank == 1
        result.posts[2].authority == 0
    }

    def "client should change user agent"() {
        given:
        def newUserAgent = "newUserAgent"
        when:
        client.setUserAgent(newUserAgent)
        def result = client.getUserAgent()
        then:
        result == newUserAgent
    }

    def "client should throw IO exception that should be wrapped into Twingly Exception"() {
        given:
        def query = "pattern"
        when:
        client.makeRequest(query)
        then:
        1 * client.getUrl(_) >> { throw new IOException("") }
        def ex = thrown(TwinglySearchException)
        def cause = ex.getCause()
        assert cause instanceof IOException
    }

    def "client should throw JAXB exception that should be wrapped into Twingly Exception"() {
        given:
        def expectedQueryString = "https://api.twingly.com/blog/search/api/v3/search?apiKey=apiKey&q=searchQuery"
        def q = "searchQuery"
        when:
        client.makeRequest(q)
        then:
        1 * client.getUrl(expectedQueryString) >> new URL(null, expectedQueryString, urlStreamHandler)
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> { throw new JAXBException("") }
        def ex = thrown(TwinglySearchException)
        def cause = ex.getCause()
        assert cause instanceof JAXBException
    }

    def "should process query, return error and throw exception"() {
        given:
        def expectedQueryString = "https://api.twingly.com/blog/search/api/v3/search?apiKey=apiKey&q=searchQuery"
        def q = "searchQuery"
        def error = new Error()
        def code = '111'
        def message = 'message'
        error.code = code
        error.message = message
        when:
        client.makeRequest(q)
        then:
        1 * client.getUrl(expectedQueryString) >> new URL(null, expectedQueryString, urlStreamHandler)
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as BufferedReader) >> error
        def ex = thrown(TwinglySearchErrorException)
        ex.error.code == code
        ex.error.message == message
    }

    def "should process query and return result instance"() {
        given:
        def expectedQueryString = "https://api.twingly.com/blog/search/api/v3/search?apiKey=apiKey&q=searchQuery"
        def q = "searchQuery"
        when:
        def result = client.makeRequest(q)
        then:
        1 * client.getUrl(expectedQueryString) >> new URL(null, expectedQueryString, urlStreamHandler)
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as BufferedReader) >> new Result()
        result != null
        assert result instanceof Result
    }

    def "client should create JAXB context"() {
        when:
        def context = client.getJAXBContext()
        then:
        context != null
    }

    def "should throw TwinglyException with MalformedURL"() {
        given:
        def malformedUrl = "qqq"
        when:
        client.getUrl(malformedUrl)
        then:
        def ex = thrown(TwinglySearchException)
        def cause = ex.getCause()
        assert cause instanceof MalformedURLException
    }

    def "should create URL"() {
        given:
        def url = "http://google.com/"
        when:
        def result = client.getUrl(url)
        then:
        result != null
    }

    def resourceInputStream(filename) {
        def filepath = packagePath.resolve(filename)
        def is = this.getClass().getClassLoader().getResourceAsStream(filepath.toString())
        return is
    }
}
