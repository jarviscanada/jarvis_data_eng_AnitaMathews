package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;

public class TwitterService implements Service {

    private CrdDao dao;

    public TwitterService(CrdDao dao) { this.dao = dao; }

    public void validatePost(Tweet tweet) {
            double lat_low = -90.0;
            double lat_hi = 90.0;
            double lon_low = -180.0;
            double lon_hi = 180.0;
            double[] coords = tweet.getCoordinates().getCoordinates();
            double lon = coords[0];
            double lat = coords[1];

            if (tweet.getText().length() > 140) {
                throw new RuntimeException("Tweet exceeds 140 character limit");
            }

            if (lon < lon_low || lon > lon_hi || lat < lat_low || lat > lat_hi) {
                throw new RuntimeException("Lat/Lon values out of range");
            }

    }

    private void validateTweetID(String id) {
        try {
            double tweetID = Double.parseDouble(id);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Incorrect format of tweet id");
        }
    }

    @Override
    public Tweet postTweet(Tweet tweet) {
        validatePost(tweet);
        return (Tweet) dao.create(tweet);
    }

    @Override
    public Tweet showTweet(String id, String[] fields) {
        validateTweetID(id);
        Tweet tweet = (Tweet) dao.findById(id, fields);
        return tweet;
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (String id : ids) {
            validateTweetID(id);
            tweets.add((Tweet)dao.deleteById(id));
        }
        return tweets;
    }
}
