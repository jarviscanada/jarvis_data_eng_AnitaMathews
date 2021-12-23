package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;

public class TwitterHttpHelper implements HttpHelper {

    private OAuthConsumer consumer;
    private HttpClient httpClient;

    public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        httpClient = HttpClientBuilder.create().build();
    }

    @Override
    public HttpResponse httpPost(URI uri) {
        try {
            HttpPost httpPostRequest = new HttpPost(uri);
            //httpPostRequest.setEntity(new StringEntity(status));
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
            HttpResponse httpResponse = httpClient.execute(httpGetRequest);
            return httpResponse;
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Get request failed", e);
        }
    }
}
