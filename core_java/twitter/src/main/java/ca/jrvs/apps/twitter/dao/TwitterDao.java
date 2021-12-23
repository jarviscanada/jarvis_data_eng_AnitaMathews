package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonParsing;
import com.google.common.net.PercentEscaper;
import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import oauth.signpost.exception.OAuthException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static ca.jrvs.apps.twitter.util.JsonParsing.toJson;

public class TwitterDao implements CrdDao<Tweet, String>{

    //URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";

    //URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    //response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    public TwitterDao(HttpHelper httpHelper) { this.httpHelper = httpHelper; }

    //check the response status code
    //int status = response.getStatusLine().getStatusCode();
    @Override
    public Tweet create(Tweet entity) {
        try {
            String status = entity.getText();
            double[] coords = entity.getCoordinates().getCoordinates();
            double lon = coords[0];
            double lat = coords[1];
            PercentEscaper percentEscaper = new PercentEscaper("", false);
            HttpResponse response = httpHelper.httpPost(new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status="
                    + percentEscaper.escape(status) + AMPERSAND + "lat=" + lat + AMPERSAND + "long=" + lon));
            checkReturnCode(response);
            Tweet tweet =  JsonParsing.toObjectFromJson(EntityUtils.toString(response.getEntity()), Tweet.class);
            return tweet;
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Create tweet error", e);
        }
    }

    @Override
    public Tweet findById(String s) {
        try {
            HttpResponse response = httpHelper.httpGet(new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id=" + s));
            checkReturnCode(response);
            Tweet tweet = JsonParsing.toObjectFromJson(EntityUtils.toString(response.getEntity()), Tweet.class);
            return tweet;
        } catch (URISyntaxException | IOException | OAuthException e) {
            throw new RuntimeException("Find by id error", e);
        }
    }

    @Override
    public Tweet deleteById(String s) {
        try {
            HttpResponse response = httpHelper.httpPost(new URI(API_BASE_URI + DELETE_PATH + "/" + s + ".json"));
            checkReturnCode(response);
            Tweet tweet = JsonParsing.toObjectFromJson(EntityUtils.toString(response.getEntity()), Tweet.class);
            return tweet;
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Deleting tweet by id error", e);
        }
    }

    private void checkReturnCode(HttpResponse response) {
        int returnCode = response.getStatusLine().getStatusCode();
        if (returnCode != HTTP_OK) {
            try {
                System.out.println("HTTP Response entity:");
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                throw new RuntimeException("Response has no entity", e);
            }
            throw new RuntimeException("HTTP status error, code: " + returnCode);
        }
    }

    public Tweet buildTweet(String text, String lat_lon) {
        Tweet tweet = new Tweet();
        tweet.setText(text);
        Coordinates coords = new Coordinates();
        double[] lat_lon_vals = Arrays.stream(StringUtils.split(lat_lon, ":")).mapToDouble(Double::parseDouble).toArray();
        //System.out.println(Arrays.toString(lon_lat_vals));
        //double[] lon_lat_vals = {79.0, 43.0};

        //twitter stores (lon, lat) while input is given as (lat, lon)
        ArrayUtils.reverse(lat_lon_vals);
        coords.setCoordinates(lat_lon_vals);
        //System.out.println(Arrays.toString(coords.getCoordinates()));
        tweet.setCoordinates(coords);
        //System.out.println(Arrays.toString(tweet.getCoordinates().getCoordinates()));
        return tweet;
    }

    public static void main(String[] args) throws IOException {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        TwitterDao twitterDao = new TwitterDao(httpHelper);

        Tweet tweet = twitterDao.buildTweet("Christmas is almost here!", "43:79");
        //Tweet tweet = twitterDao.findById("1474089618752643087");
        //Tweet tweet = twitterDao.deleteById("1474089618752643087");
        Tweet newTweet = twitterDao.create(tweet);
        //Tweet tweet = twitterDao.create(JsonParsing.toObjectFromJson(JsonParsing.tweetStr, Tweet.class));
        System.out.println(JsonParsing.toJson(newTweet, true, true));

    }
}
