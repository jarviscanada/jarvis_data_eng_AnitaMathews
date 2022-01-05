package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterHttpHelperUnitTest {

    HttpHelper httpHelper;
    private static String CONSUMER_KEY;
    private static String CONSUMER_SECRET;
    private static String ACCESS_TOKEN;
    private static String TOKEN_SECRET;

    @Before
    public void init() {
        CONSUMER_KEY = System.getenv("consumerKey");
        CONSUMER_SECRET = System.getenv("consumerSecret");
        ACCESS_TOKEN = System.getenv("accessToken");
        TOKEN_SECRET = System.getenv("tokenSecret");
        httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    }

    @Test
    public void httpPost() {
        try {
            HttpResponse response = httpHelper.httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status=My_First_Tweet" ));
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("httpPost method failed", e);
        }
    }

    @Test
    public void httpGet() {
        try {
            HttpResponse response = httpHelper.httpGet(new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=BarackObama"));
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (OAuthException | IOException | URISyntaxException e) {
            throw new RuntimeException("httpGet method failed", e);
        }
    }
}