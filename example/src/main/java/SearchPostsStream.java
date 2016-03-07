import com.twingly.search.Query;
import com.twingly.search.QueryBuilder;
import com.twingly.search.client.Client;
import com.twingly.search.client.UrlConnectionClient;
import com.twingly.search.domain.Post;
import com.twingly.search.domain.Result;

public class SearchPostsStream {
    private static final String USER_AGENT = "MyCompany/1.0";
    private final String searchPattern;
    private Client client;
    private Query query;
    private int counter;

    public SearchPostsStream(String keyword) {
        searchPattern = String.format("sort-order:asc sort:published %s", keyword);
        client = new UrlConnectionClient();
        client.setUserAgent(USER_AGENT);
        query = QueryBuilder.create(searchPattern).build();
    }

    public static void main(String[] args) {
        // set java system property TWINGLY_SEARCH_KEY
        SearchPostsStream searchPostsStream = new SearchPostsStream("(github) AND (hipchat OR slack)");
        searchPostsStream.each();
        System.out.println("Iterated through " + searchPostsStream.getCount() + " results");
    }

    public void each() {
        Result result = client.makeRequest(query);
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
        query.setStartTime(lastPost.getPublished());
        Result newResult = client.makeRequest(query);
        iterateAllPosts(newResult);
    }
}
