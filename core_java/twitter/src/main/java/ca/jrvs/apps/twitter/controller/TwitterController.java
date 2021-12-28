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
import java.util.Set;
import ca.jrvs.apps.twitter.util.TweetUtil;
import static ca.jrvs.apps.twitter.model.Tweet.getPropertyNames;

public class TwitterController implements Controller {

    private static final String COMMA = ",";

    private Service service;

    public TwitterController(Service service) {this.service = service;}

    @Override
    public Tweet postTweet(String[] args) {
        String tweet_txt = args[1];
        String lat_lon = args[2];
        Tweet tweet = TweetUtil.buildTweet(tweet_txt, lat_lon);
        return service.postTweet(tweet);
    }

    //implement option to show only specified fields
    @Override
    public Tweet showTweet(String[] args) {
        String id = args[1];

        //if no fields specified, return the whole tweet object
        if (args.length == 2) return service.showTweet(id, null);

        //if fields specified, return only those
        else {
            String[] fields = args[2].split(COMMA);
            Set<String> properties = getPropertyNames();
            for (String s : fields) {
                if (!properties.contains(s)) {
                    throw new RuntimeException("Field requested does not exist");
                }
            }
            return service.showTweet(id, fields);
        }
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) {
        String[] tweet_ids = args[1].split(COMMA);
        return service.deleteTweets(tweet_ids);
    }


    public static void main(String[] args) throws JsonProcessingException {
    /*
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        TwitterDao twitterDao = new TwitterDao(httpHelper);

        String[] fields_args = {"show", "1474260302854340608", "id,coordinates"};
        TwitterService twitterService = new TwitterService(twitterDao);
        TwitterController twitterController = new TwitterController(twitterService);
        //Tweet tweet = twitterController.buildTweet("Christmas time is the best time of the year", "43:79");
        String[] fieldTest = {};
        Tweet tweet = twitterController.showTweet(fields_args);
        //Tweet tweet = twitterDao.findById("1474260302854340608",fields);
        System.out.println(tweet.getCreated_at());
        //Tweet tweet = twitterDao.deleteById("1474089618752643087");
        //Tweet newTweet = twitterDao.create(tweet); //this works
        //Tweet tweet = twitterDao.create(JsonParsing.toObjectFromJson(JsonParsing.tweetStr, Tweet.class));
        System.out.println(JsonParsing.toJson(tweet, true, false, null));
    */
    }




}
