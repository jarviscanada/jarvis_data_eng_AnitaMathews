package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

    @Mock
    TwitterService service;

    @InjectMocks
    TwitterController controller;


    @Test
    public void postTweet() {
        String[] args = {"post", "post test", "34:45"};
        Tweet tweet = TweetUtil.buildTweet("post test", "34:45");
        when(service.postTweet(any())).thenReturn(tweet);
        Tweet postedTweet = controller.postTweet(args);

        assertEquals(tweet.getText(), postedTweet.getText());
    }

    @Test
    public void showTweet() {
        String[] args = {"show", "123"};
        Tweet tweet = TweetUtil.buildTweet("show test", "34:45");
        when(service.showTweet(any(), eq(null))).thenReturn(tweet);
        Tweet foundTweet = controller.showTweet(args);

        assertEquals(tweet.getText(), foundTweet.getText());
    }

    @Test
    public void deleteTweet() {
        String[] args = {"delete", "123"};
        Tweet firstTweet = TweetUtil.buildTweet("delete test", "34:45");
        Tweet secondTweet = TweetUtil.buildTweet("delete test two", "34:45");
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(firstTweet);
        tweets.add(secondTweet);
        when(service.deleteTweets(any())).thenReturn(tweets);
        List<Tweet> delTweets = controller.deleteTweet(args);

        assertEquals(tweets, delTweets);

    }
}