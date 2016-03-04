import com.twingly.search.Query;
import com.twingly.search.domain.Post;
import com.twingly.search.domain.Result;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HelloWorld {
    private static final long ONE_DAY = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);

    public static void main(String[] args) {
        // set java system property TWINGLY_SEARCH_KEY
        Query query = new Query();
        Date startDate = new Date(System.currentTimeMillis() - ONE_DAY);
        String queryString = query.buildRequestQuery("\"Hello World!\"", null,
                startDate, null);
        Result result = query.query(queryString);
        for (Post post : result.getPosts()) {
            System.out.println(post.getUrl());
        }
    }
}
