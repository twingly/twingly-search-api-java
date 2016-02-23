package com.twingly.search.test.functional;

import co.freeside.betamax.ProxyConfiguration;
import co.freeside.betamax.junit.RecorderRule;
import com.twingly.search.Constants;
import com.twingly.search.Language;
import com.twingly.search.Query;
import com.twingly.search.Result;
import org.junit.Rule;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryFunctionalTest {
    @Rule
    public RecorderRule recorderRule = new RecorderRule(ProxyConfiguration.builder()
            .tapeRoot(new File("src\\test\\resources\\betamax\\tapes"))
            .sslEnabled(true)
            .build());
    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);

    public static void main(String[] args) throws ParseException {
        QueryFunctionalTest test = new QueryFunctionalTest();
        test.testErrorKey();
        test.testValidKey();
    }

    public void testErrorKey() throws ParseException {
        String searchPattern = "github";
        Date startDate = sdf.parse("2016-02-18 10:00:00");
        Date endDate = sdf.parse("2016-02-19 11:00:00");
        Language language = Language.English;
        String apiKey = "";
        Query query = new Query(apiKey);
        Result result = query.makeRequest(searchPattern, language, startDate, endDate);

    }

    //    @Test
//    @Betamax(tape = "validKey", mode = TapeMode.READ_WRITE)
    public void testValidKey() throws ParseException {
        String searchPattern = "github";
        Date startDate = sdf.parse("2016-02-18 10:00:00");
        Date endDate = sdf.parse("2016-02-19 11:00:00");
        Language language = Language.English;
        String apiKey = "";
        Query query = new Query(apiKey);
        Result result = query.makeRequest(searchPattern, language, startDate, endDate);

    }
}
