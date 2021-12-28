package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
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
}
