package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.security.auth.login.AccountExpiredException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class TwitterHttpHelperTest {

    private static String CONSUMER_KEY = System.getenv("consumerKey");
    private static String CONSUMER_SECRET = System.getenv("consumerSecret");
    private static String ACCESS_TOKEN = System.getenv("accessToken");
    private static String TOKEN_SECRET = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);

    @Test
    public void httpPost() {
        try {
            HttpResponse response = httpHelper.httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status=My_First_Tweet"));
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("httpPost method not working", e);
        }
    }

    @Test
    public void httpGet() {
        try {
            HttpResponse response = httpHelper.httpGet(new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=BarackObama"));
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (OAuthException | IOException | URISyntaxException e) {
            throw new RuntimeException("httpGet method not working", e);
        }
    }
}