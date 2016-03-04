import com.twingly.search.Query;
import com.twingly.search.client.Client;
import com.twingly.search.client.UrlConnectionClient;
import com.twingly.search.domain.Post;
import com.twingly.search.domain.Result;

public class SearchPostsStream {
    private static final String USER_AGENT = "MyCompany/1.0";
    private Query query;
    private final String searchPattern;
    private int counter;

    public SearchPostsStream(String keyword) {
        searchPattern = String.format("sort-order:asc sort:published %s", keyword);
        Client client = new UrlConnectionClient();
        client.setUserAgent(USER_AGENT);
        query = new Query(client);
    }

    public void each() {
        Result result = query.makeRequest(searchPattern);
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
        Result newResult = query.makeRequest(searchPattern, null, lastPost.getPublished());
        iterateAllPosts(newResult);
    }

    public static void main(String[] args) {
        // set java system property TWINGLY_SEARCH_KEY
        SearchPostsStream searchPostsStream = new SearchPostsStream("(github) AND (hipchat OR slack)");
        searchPostsStream.each();
        System.out.println("Iterated through " + searchPostsStream.getCount() + " results");
    }
}
