import com.twingly.search.Query;
import com.twingly.search.QueryBuilder;
import com.twingly.search.client.Client;
import com.twingly.search.client.UrlConnectionClient;
import com.twingly.search.domain.Post;
import com.twingly.search.domain.Result;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HelloWorld {
    private static final long ONE_DAY = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);

    public static void main(String[] args) {
        // set java system property TWINGLY_SEARCH_KEY
        Date startDate = new Date(System.currentTimeMillis() - ONE_DAY);
        Query query = QueryBuilder.create("\"Hello World!\"").startTime(startDate).build();
        Client client = new UrlConnectionClient();
        Result result = client.makeRequest(query);
        for (Post post : result.getPosts()) {
            System.out.println(post.getUrl());
        }
    }
}
