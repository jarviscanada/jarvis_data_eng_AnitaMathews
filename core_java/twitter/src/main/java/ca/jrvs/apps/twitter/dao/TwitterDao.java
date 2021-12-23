package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.common.net.PercentEscaper;

import java.net.URI;
import java.net.URISyntaxException;

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
            PercentEscaper percentEscaper = new PercentEscaper("", false);
            httpHelper.httpPost(new URI(API_BASE_URI + POST_PATH + percentEscaper.escape(status));
            
            return null;
        } catch (URISyntaxException e) {
            throw new RuntimeException("URI post tweet syntax incorrect", e);
        }
    }

    @Override
    public Tweet findById(String s) {
        return null;
    }

    @Override
    public Tweet deleteById(String s) {
        return null;
    }
}
