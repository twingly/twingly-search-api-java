package com.twingly.search.test.functional;

import com.twingly.search.Constants;
import com.twingly.search.Language;
import com.twingly.search.Query;
import com.twingly.search.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryFunctionalTest {
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
