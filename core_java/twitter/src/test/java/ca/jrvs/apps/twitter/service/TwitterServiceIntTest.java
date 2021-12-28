package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonParsing;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwitterServiceIntTest {

    private TwitterService service;

    @Before
    public void init() {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        TwitterDao dao = new TwitterDao(httpHelper);
        this.service = new TwitterService(dao);
    }

    @Test
    public void postTweet() {
        service.postTweet(TweetUtil.buildTweet("post tweet test text", "56:78"));
    }

    @Test
    public void showTweet() throws JsonProcessingException {
        Tweet tweet = service.postTweet(TweetUtil.buildTweet("show tweet test text", "56:78"));

        //show all fields
        Tweet foundTweet = service.showTweet(tweet.getId(), null);
        System.out.println("All fields of tweet:");
        System.out.println(JsonParsing.toJson(foundTweet, true, false, null));

        //show only id and coords
        String[] fields = {"id", "coordinates"};
        Tweet foundTweetFields = service.showTweet(tweet.getId(), fields);
        System.out.println("Only id and coords");
        System.out.println(JsonParsing.toJson(foundTweetFields, true, false, null));
    }

    @Test(expected = RuntimeException.class)
    public void deleteTweets() {
        Tweet tweet = service.postTweet(TweetUtil.buildTweet("delete tweet test text", "56:78"));
        String[] ids = {tweet.getId()};
        service.deleteTweets(ids);

        //ensure that tweet is deleted, should throw a Runtime error
        service.showTweet(tweet.getId(), null);
    }
}