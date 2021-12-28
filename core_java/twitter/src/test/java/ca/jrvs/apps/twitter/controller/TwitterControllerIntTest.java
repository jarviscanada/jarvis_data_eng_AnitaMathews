package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonParsing;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerIntTest {

    private TwitterController controller;

    @Before
    public void init() {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        TwitterDao twitterDao = new TwitterDao(httpHelper);
        TwitterService twitterService = new TwitterService(twitterDao);

        controller = new TwitterController(twitterService);
    }

    @Test
    public void postTweet() {
        String tweet_text = "post tweet testing text " + System.currentTimeMillis();
        String lat_lon = "45:56";
        Tweet tweet = TweetUtil.buildTweet(tweet_text, lat_lon);
        String[] args = {"post", tweet_text, lat_lon};
        controller.postTweet(args);
    }

    @Test
    public void showTweet() throws JsonProcessingException {
        //show all tweet attributes
        String[] args = {"show", "1474260302854340608"};
        Tweet tweet = controller.showTweet(args);
        System.out.println("full tweet:");
        System.out.println(JsonParsing.toJson(tweet, true, false, null));

        //show only id and coords
        String[] field_args = {"show", "1474260302854340608", "id,coordinates"};
        Tweet tweetFields = controller.showTweet(field_args);
        System.out.println("only id and coords fields");
        System.out.println(JsonParsing.toJson(tweetFields, true, false, null));
    }

    @Test(expected = RuntimeException.class)
    public void deleteTweet() {
        String tweet_text = "post tweet testing text " + System.currentTimeMillis();
        String lat_lon = "45:56";
        Tweet tweet = TweetUtil.buildTweet(tweet_text, lat_lon);
        String[] create_args = {"post", tweet_text, lat_lon};
        Tweet createdTweet = controller.postTweet(create_args);

        //delete created tweet
        String[] del_args = {"delete", createdTweet.getId()};
        List<Tweet> tweets = controller.deleteTweet(del_args);
        assertEquals(tweets.get(0).getId(), createdTweet.getId());
        assertEquals(tweets.get(0).getText(), createdTweet.getText());

        //ensure that it is indeed deleted
        String[] show_args = {"show", tweet.getId()};
        controller.showTweet(show_args);
    }
}