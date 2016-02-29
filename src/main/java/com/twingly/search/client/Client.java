package com.twingly.search.client;

import com.twingly.search.Result;

/**
 * Created by Iurii Sergiichuk on 25.02.2016.
 */
public interface Client {
    public String getUserAgent();

    public void setUserAgent(String userAgent);

    public Result makeRequest(String query);
}
