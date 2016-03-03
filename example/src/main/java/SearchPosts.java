import com.twingly.search.Query;
import com.twingly.search.client.Client;
import com.twingly.search.client.UrlConnectionClient;
import com.twingly.search.domain.Post;
import com.twingly.search.domain.Result;

public class SearchPosts {
    public static void main(String[] args) {
        // set java system property TWINGLY_SEARCH_KEY
        Client client = new UrlConnectionClient();
        client.setUserAgent("MyCompany/1.0"); //Set optional user agent
        Query query = new Query(client);
        String keyword = "(github) AND (hipchat OR slack)";
        String searchPattern = String.format("sort-order:asc sort:published %s", keyword);
        Result result = query.makeRequest(searchPattern);
        for (Post post : result.getPosts()) {
            System.out.println(post.getUrl());
        }
    }
}
