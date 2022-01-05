package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.BeforeClass;
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

    public TwitterDao spyDao;
    private Tweet expectedTweet;

    @Before
    public void init() throws IOException, OAuthException {
        when(mockHelper.httpPost(isNotNull())).thenReturn(mockResponse);
        when(mockHelper.httpGet(isNotNull())).thenReturn(mockResponse);
        when(mockResponse.getEntity()).thenReturn(entity);
        spyDao =  Mockito.spy(dao);
        Mockito.doNothing().when(spyDao).checkReturnCode(any());

        //set up tweet
        expectedTweet = TweetUtil.buildTweet("Unit testing tweet", "45:56");
        expectedTweet.setId("123"); //testID
        doReturn(expectedTweet).when(spyDao).parseTweet(any());
    }

    @Test
    public void create() throws IOException {
        Tweet newTweet = spyDao.create(expectedTweet);

        assertNotNull(newTweet);
        assertNotNull(newTweet.getText());
        assertEquals(expectedTweet, newTweet);
    }

    @Test
    public void findById() throws IOException {
        String id = expectedTweet.getId();
        Tweet foundTweet = spyDao.findById(id, null);

        assertNotNull(foundTweet);
        assertEquals(foundTweet.getId(), id);
    }

    @Test
    public void deleteById() throws IOException {
        String id = expectedTweet.getId();
        Tweet delTweet = spyDao.deleteById(id);

        assertNotNull(delTweet);
        assertEquals(expectedTweet, delTweet);
    }
}