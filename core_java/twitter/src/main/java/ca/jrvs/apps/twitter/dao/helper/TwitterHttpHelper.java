package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class TwitterHttpHelper implements HttpHelper {

    private OAuthConsumer consumer;
    private HttpClient httpClient;

    public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        httpClient = HttpClientBuilder.create().build();
    }

    /**
     * Default constructor
     */

    public TwitterHttpHelper() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        httpClient = HttpClients.createDefault();
    }

    @Override
    public HttpResponse httpPost(URI uri) {
        try {
            HttpPost httpPostRequest = new HttpPost(uri);
            consumer.sign(httpPostRequest);
            return httpClient.execute(httpPostRequest);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Post request failed", e);
        }
    }

    @Override
    public HttpResponse httpGet(URI uri) {
        try {
            HttpGet httpGetRequest = new HttpGet(uri);
            consumer.sign(httpGetRequest);
            return httpClient.execute(httpGetRequest);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Get request failed", e);
        }
    }
}
