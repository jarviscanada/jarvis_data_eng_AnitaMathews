package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonParsing;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TwitterDaoIntTest {

    //authentication
    private static String CONSUMER_KEY;
    private static String CONSUMER_SECRET;
    private static String ACCESS_TOKEN;
    private static String TOKEN_SECRET;

    //Dao and tweet properties to check
    private TwitterDao dao;
    private String hashTag;
    private String text;
    private String lat_lon;
    private Tweet tweet;

    @BeforeClass
    public static void init() {
        CONSUMER_KEY = System.getenv("consumerKey");
        CONSUMER_SECRET = System.getenv("consumerSecret");
        ACCESS_TOKEN = System.getenv("accessToken");
        TOKEN_SECRET = System.getenv("tokenSecret");
    }

    @Before
    public void setUpTweet() {
        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        this.dao = new TwitterDao(httpHelper);

        //each test requires a created tweet
        hashTag = "#christmas";
        text = "Holiday Time! " + hashTag + " " + System.currentTimeMillis();
        lat_lon = "45:63";
        Tweet newTweet = TweetUtil.buildTweet(text, lat_lon);
        tweet = dao.create(newTweet);
    }

    @Test
    public void create() {
        assertEquals(text, tweet.getText());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
        assertEquals(63, tweet.getCoordinates().getCoordinates()[0], 0.01);
        assertEquals(45, tweet.getCoordinates().getCoordinates()[1], 0.01);
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
    }

    @Test
    public void findById() throws JsonProcessingException {
        String id = tweet.getId();

        //return all fields
        Tweet foundTweet = dao.findById(id, null);
        System.out.println("All fields:");
        System.out.println(JsonParsing.toJson(foundTweet, true, false, null));
        assertEquals(id, foundTweet.getId());
        assertEquals(tweet.getText(), foundTweet.getText());
        assertEquals(tweet.getCoordinates().getCoordinates()[0], foundTweet.getCoordinates().getCoordinates()[0], 0.01);
        assertEquals(tweet.getCoordinates().getCoordinates()[1], foundTweet.getCoordinates().getCoordinates()[1], 0.01);

        //return only id, text field from findById
        String[] fields = {"id", "text"};
        Tweet foundTweetFields = dao.findById(id, fields);
        System.out.println("Only id and text fields:");
        System.out.println(JsonParsing.toJson(foundTweetFields, true, false, null));
        assertEquals(id, foundTweetFields.getId());
        assertEquals(tweet.getText(), foundTweetFields.getText());
    }

    @Test(expected = RuntimeException.class)
    public void deleteById() throws JsonProcessingException {
        //delete created tweet by id
        String id = tweet.getId();
        Tweet delTweet = dao.deleteById(id);
        System.out.println(JsonParsing.toJson(delTweet, true, false, null));
        assertEquals(id, delTweet.getId());
        assertEquals(tweet.getText(), delTweet.getText());
        assertEquals(tweet.getCoordinates().getCoordinates()[0], delTweet.getCoordinates().getCoordinates()[0], 0.01);
        assertEquals(tweet.getCoordinates().getCoordinates()[1], delTweet.getCoordinates().getCoordinates()[1], 0.01);

        //check to ensure that it is deleted by trying to find it again
        //should throw Runtime exception
        Tweet foundDelTweet = dao.findById(id, null);
    }
}