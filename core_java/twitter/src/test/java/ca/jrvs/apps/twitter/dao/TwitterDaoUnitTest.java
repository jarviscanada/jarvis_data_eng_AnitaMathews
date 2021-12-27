package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

import static ca.jrvs.apps.twitter.util.JsonParsing.toObjectFromJson;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

    @Mock
    HttpHelper mockHelper;

    @Mock
    HttpResponse mockResponse;

    @Mock
    HttpEntity entity;

    @InjectMocks
    TwitterDao dao;

    public static String tweetStr = null;
    public static String existingTweetStr = null;
    public TwitterDao spyDao;

    @Before
    public void init() throws IOException, OAuthException {
         tweetStr = "{\n" +
                "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n" +
                "   \"id\":1097607853932564480,\n" +
                "   \"id_str\":\"1097607853932564480\",\n" +
                "   \"text\":\"test with loc223\",\n" +
                "   \"entities\":{\n" +
                "      \"hashtags\":[\n" +
                "         {\n" +
                "            \"text\":\"documentation\",\n" +
                "            \"indices\":[\n" +
                "               211,\n" +
                "               225\n" +
                "            ]\n" +
                "         },\n" +
                "         {\n" +
                "            \"text\":\"parsingJSON\",\n" +
                "            \"indices\":[\n" +
                "               226,\n" +
                "               238\n" +
                "            ]\n" +
                "         },\n" +
                "         {\n" +
                "            \"text\":\"GeoTagged\",\n" +
                "            \"indices\":[\n" +
                "               239,\n" +
                "               249\n" +
                "            ]\n" +
                "         }\n" +
                "      ],\n" +
                "      \"user_mentions\":[\n" +
                "         {\n" +
                "            \"name\":\"Twitter API\",\n" +
                "            \"indices\":[\n" +
                "               4,\n" +
                "               15\n" +
                "            ],\n" +
                "            \"screen_name\":\"twitterapi\",\n" +
                "            \"id\":6253282,\n" +
                "            \"id_str\":\"6253282\"\n" +
                "         }\n" +
                "      ]\n" +
                "   },\n" +
                "   \"coordinates\":{\n" +
                "      \"coordinates\":[\n" +
                "         -75.14310264,\n" +
                "         40.05701649\n" +
                "      ],\n" +
                "      \"type\":\"Point\"\n" +
                "   },\n" +
                "   \"retweet_count\":0,\n" +
                "   \"favorite_count\":0,\n" +
                "   \"favorited\":false,\n" +
                "   \"retweeted\":false\n" +
                "}";

        existingTweetStr = "{\n" +
                "  \"created_at\" : \"Sun Dec 26 06:50:25 +0000 2021\",\n" +
                "  \"id\" : \"1474996003178795010\",\n" +
                "  \"id_str\" : \"1474996003178795010\",\n" +
                "  \"text\" : \"Merry Christmas Everyone!\",\n" +
                "  \"entities\" : {\n" +
                "    \"hashtags\" : [ ],\n" +
                "    \"user_mentions\" : [ ]\n" +
                "  },\n" +
                "  \"coordinates\" : {\n" +
                "    \"type\" : \"Point\",\n" +
                "    \"coordinates\" : [ 45.0, 34.0 ]\n" +
                "  },\n" +
                "  \"retweet_count\" : \"0\",\n" +
                "  \"favorite_count\" : \"0\",\n" +
                "  \"favorited\" : \"false\",\n" +
                "  \"retweeted\" : \"false\"\n" +
                "}";
        when(mockHelper.httpPost(isNotNull())).thenReturn(mockResponse);
        when(mockHelper.httpGet(isNotNull())).thenReturn(mockResponse);
        when(mockResponse.getEntity()).thenReturn(entity);
        spyDao =  Mockito.spy(dao);
        Mockito.doNothing().when(spyDao).checkReturnCode(any());
    }

    @Test
    public void create() throws IOException {
        Tweet expectedTweet = toObjectFromJson(tweetStr, Tweet.class);
        doReturn(expectedTweet).when(spyDao).parseTweet(any());
        Tweet newTweet = spyDao.create(expectedTweet);

        assertNotNull(newTweet);
        assertNotNull(newTweet.getText());
        assertEquals(expectedTweet, newTweet);
    }

    @Test
    public void findById() throws IOException, OAuthException {
        Tweet expectedTweet = toObjectFromJson(existingTweetStr, Tweet.class);
        String id = expectedTweet.getId();
        doReturn(expectedTweet).when(spyDao).parseTweet(any());
        Tweet foundTweet = spyDao.findById(id, null);

        assertNotNull(foundTweet);
        assertEquals(foundTweet.getId(), id);
    }

    @Test
    public void deleteById() throws IOException {
        Tweet expectedTweet = toObjectFromJson(existingTweetStr, Tweet.class);
        String id = expectedTweet.getId();
        doReturn(expectedTweet).when(spyDao).parseTweet(any());
        Tweet delTweet = spyDao.deleteById(id);

        assertNotNull(delTweet);
        assertEquals(expectedTweet, delTweet);
    }
}