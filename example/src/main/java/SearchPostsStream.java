import com.twingly.search.QueryBuilder;
import com.twingly.search.client.Client;
import com.twingly.search.client.UrlConnectionClient;
import com.twingly.search.domain.Post;
import com.twingly.search.domain.Result;

import java.util.Date;

public class SearchPostsStream {
    private static final String USER_AGENT = "MyCompany/1.0";
    private QueryBuilder queryBuilder;
    private Client client;
    private int counter;

    public SearchPostsStream(String keyword) {
        queryBuilder = QueryBuilder.create(String.format("sort-order:asc sort:published %s", keyword));
        client = new UrlConnectionClient();
        client.setUserAgent(USER_AGENT);
    }

    public static void main(String[] args) {
        // set java system property TWINGLY_SEARCH_KEY
        SearchPostsStream searchPostsStream = new SearchPostsStream("(github) AND (hipchat OR slack)");
        searchPostsStream.each();
        System.out.println("Iterated through " + searchPostsStream.getCount() + " results");
    }

    public void each() {
        Result result = client.makeRequest(queryBuilder.build());
        iterateAllPosts(result);
    }

    public int getCount() {
        return counter;
    }

    private void iterateAllPosts(Result result) {
        for (Post post : result.getPosts()) {
            System.out.println(post.getUrl());
            counter++;
        }
        if (result.areAllResultsReturned()) {
            return;
        }
        Post lastPost = result.getPosts().get(result.getPosts().size() - 1);
        Date lastPublishedAt = lastPost.getPublishedAt();
        queryBuilder = queryBuilder.startTime(lastPublishedAt);
        Result newResult = client.makeRequest(queryBuilder.build());
        iterateAllPosts(newResult);
    }
}
