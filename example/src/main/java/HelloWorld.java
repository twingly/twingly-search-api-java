import com.twingly.search.client.Client;
import com.twingly.search.client.UrlConnectionClient;
import com.twingly.search.domain.Post;
import com.twingly.search.domain.Result;

public class HelloWorld {

    public static void main(String[] args) {
        // set java system property TWINGLY_SEARCH_KEY
        String q = "\"Hello World!\" tspan:24h";
        Client client = new UrlConnectionClient();
        Result result = client.makeRequest(q);
        for (Post post : result.getPosts()) {
            System.out.println(post.getUrl());
        }
    }
}
