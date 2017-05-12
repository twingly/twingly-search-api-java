import com.twingly.search.Constants;
import com.twingly.search.client.Client;
import com.twingly.search.client.UrlConnectionClient;
import com.twingly.search.domain.Post;
import com.twingly.search.domain.Result;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchPostsStream {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(Constants.DATE_FORMAT);
    private static final String USER_AGENT = "MyCompany/1.0";
    private final String keyword;
    private String q;
    private Client client;
    private int counter;

    public SearchPostsStream(String keyword) {
        this.keyword = keyword;
        q = String.format("sort-order:asc sort:published %s", keyword);
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
        Result result = client.makeRequest(q);
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
        Date publishedAt = lastPost.getPublishedAt();
        String startDate = SIMPLE_DATE_FORMAT.format(publishedAt);
        q = String.format("sort-order:asc sort:published %s start-date:%s", keyword, startDate);
        Result newResult = client.makeRequest(q);
        iterateAllPosts(newResult);
    }
}
