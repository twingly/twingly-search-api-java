package com.twingly.search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryFunctionalTest {
    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);

    public void test() throws ParseException {
        String searchPattern = "github";
        Date startDate = sdf.parse("2016-02-18 10:00:00");
        Date endDate = sdf.parse("2016-02-19 11:00:00");
        Language language = Language.English;
        String apiKey = "";
        Query query = new Query(apiKey);
        Result result = query.makeRequest(searchPattern, language, startDate, endDate);

    }
}
