package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonParsing;
import com.fasterxml.jackson.core.JsonProcessingException;

public class TwitterCLIApp {

    TwitterController controller;

    public TwitterCLIApp(TwitterController controller) {this.controller = controller;}

    //print out tweet contents
    public void run(String[] args) {
        //check the args
        String operation = args[0];
        switch (operation) {
            case "post":
                if (args.length != 3) {
                    throw new IllegalArgumentException("To post tweet, 3 args required. Usage: post tweet_text lat:lon");
                }
                controller.postTweet(args);
                break;
            case "show":
                if (args.length > 3 || args.length < 2) {
                    throw new IllegalArgumentException("To show tweet, 2-3 args required. Usage: show tweet_id [field1,field2]");
                }
                controller.showTweet(args);
                break;
            default: throw new IllegalArgumentException("Commands are one of: post, show, delete");
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        TwitterDao twitterDao = new TwitterDao(httpHelper);
        TwitterService twitterService = new TwitterService(twitterDao);
        TwitterController twitterController = new TwitterController(twitterService);
        TwitterCLIApp twitterCLIApp = new TwitterCLIApp(twitterController);

        twitterCLIApp.run(args);
     //   String[] fields = {"id", "text", "coordinates"};

        //Tweet tweet = twitterController.buildTweet("Christmas time is the best time of the year", "43:79");
        //String[] fieldTest = {};
        //Tweet tweet = twitterDao.findById("1474260302854340608",null);
        //System.out.println(tweet.getCreated_at());
        //Tweet tweet = twitterDao.deleteById("1474089618752643087");
        //Tweet newTweet = twitterDao.create(tweet); //this works
        //Tweet tweet = twitterDao.create(JsonParsing.toObjectFromJson(JsonParsing.tweetStr, Tweet.class));
        //System.out.println(JsonParsing.toJson(tweet, true, false, null));
    }
}
