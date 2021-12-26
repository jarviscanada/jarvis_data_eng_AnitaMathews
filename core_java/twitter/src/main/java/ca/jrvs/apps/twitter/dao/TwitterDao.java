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
            return parseTweet(EntityUtils.toString(response.getEntity()));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Create tweet error", e);
        }
    }

    @Override
    public Tweet findById(String s, String[] fields) {
        try {
            HttpResponse response = httpHelper.httpGet(new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id=" + s));
            checkReturnCode(response);
            Tweet tweet = parseTweet(EntityUtils.toString(response.getEntity()));
            if (fields == null) {
                return tweet;
            }
            else {
                String filteredTweetStr = JsonParsing.toJson(tweet, true, false, fields);
                Tweet filteredTweet = parseTweet(filteredTweetStr);
                return filteredTweet;
            }
        } catch (URISyntaxException | IOException | OAuthException e) {
            throw new RuntimeException("Find by id error", e);
        }
    }

    @Override
    public Tweet deleteById(String s) {
        Tweet tweet = findById(s, null);
        try {
            HttpResponse response = httpHelper.httpPost(new URI(API_BASE_URI + DELETE_PATH + "/" + s + ".json"));
            checkReturnCode(response);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Deleting tweet by id error", e);
        }
        return tweet;
    }

    public Tweet parseTweet(String tweetStr) throws IOException {
        return JsonParsing.toObjectFromJson(tweetStr, Tweet.class);
    }

    public void checkReturnCode(HttpResponse response) {
        int returnCode = response.getStatusLine().getStatusCode();
        if (returnCode != HTTP_OK) {
            try {
                System.out.println("HTTP Response entity:");
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                throw new RuntimeException("Response has no entity", e);
            }
            throw new RuntimeException("HTTP status error, check tweet ID");
        }
    }
}
