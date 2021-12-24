package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonParsing;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwitterController implements Controller {

    private Service service;

    public TwitterController(Service service) {this.service = service;}


    @Override
    public Tweet postTweet(String[] args) {
        String tweet_txt = args[1];
        String lat_lon = args[2];
        Tweet tweet = buildTweet(tweet_txt, lat_lon);
        return service.postTweet(tweet);
    }

    //implement option to show only specified fields
    @Override
    public Tweet showTweet(String[] args) {

        return null;
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) {
        return service.deleteTweets(args);
    }

    public Tweet buildTweet(String text, String lat_lon) {
        String[] coords_lst = lat_lon.split(":");
        if (coords_lst.length != 2 || text.isEmpty() || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Tweet text cannot be empty, 2 values for Lat/Lon are needed");
        }

        //Build tweet if text is not empty and 2 coords are specified
        Tweet tweet = new Tweet();
        tweet.setText(text);
        Coordinates coords = new Coordinates();
        double[] lat_lon_vals = null;
        try {
            lat_lon_vals = Arrays.stream(StringUtils.split(lat_lon, ":")).mapToDouble(Double::parseDouble).toArray();

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Lat/Lon values are not numeric", e);
        }

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

    public static void main(String[] args) throws JsonProcessingException {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        TwitterDao twitterDao = new TwitterDao(httpHelper);

        String[] fields = {"id", "text", "coordinates"};
        TwitterService twitterService = new TwitterService(twitterDao);
        TwitterController twitterController = new TwitterController(twitterService);
        //Tweet tweet = twitterController.buildTweet("Christmas time is the best time of the year", "43:79");
        String[] fieldTest = {};
        Tweet tweet = twitterDao.findById("1474260302854340608",null);
        System.out.println(tweet.getCreated_at());
        //Tweet tweet = twitterDao.deleteById("1474089618752643087");
        //Tweet newTweet = twitterDao.create(tweet); //this works
        //Tweet tweet = twitterDao.create(JsonParsing.toObjectFromJson(JsonParsing.tweetStr, Tweet.class));
        System.out.println(JsonParsing.toJson(tweet, true, false, null));

    }
}