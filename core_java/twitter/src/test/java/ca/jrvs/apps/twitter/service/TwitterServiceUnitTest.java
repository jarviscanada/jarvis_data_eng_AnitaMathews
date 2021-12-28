package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonParsing;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService service;

    @Test(expected = RuntimeException.class)
    public void postTweet() {
        when(dao.create(any())).thenReturn(new Tweet());
        service.postTweet(TweetUtil.buildTweet("test text", "45:63"));

        //throws Runtime exception if tweet text is longer than 140 chars
        String longText = "These are sentences that exceed the one hundred and forty character limit by twitter. Twitter is a great tool to communicate with other people.";
        service.postTweet(TweetUtil.buildTweet(longText, "45:63"));

        //throws Runtime exception if lat/lon are out of range
        String lat_lon = "-93:190";
        service.postTweet(TweetUtil.buildTweet("test text", lat_lon));
    }

    @Test(expected = RuntimeException.class)
    public void showTweet() {
        String idTest = "1234";
        when(dao.findById(anyString(), eq(null))).thenReturn(new Tweet());
        service.showTweet(idTest, null);

        //throws Runtime exception, ID is not a
        String idErr = "A12345";
        service.showTweet(idErr, null);
    }

    @Test
    public void deleteTweets() {
        String[] tweetIDs = {"123", "345"};
        when(dao.deleteById(anyCollection().toArray())).thenReturn(tweetIDs);
        service.deleteTweets(tweetIDs);
    }
}