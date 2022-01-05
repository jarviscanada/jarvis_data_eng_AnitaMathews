package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import java.util.Arrays;

public class TweetUtil {

    private static final String COORD_SEP = ":";

    public static Tweet buildTweet(String text, String lat_lon) {
        String[] coords_lst = lat_lon.split(COORD_SEP);
        if (coords_lst.length != 2 || text.isEmpty() || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Tweet text cannot be empty, 2 values for Lat/Lon are needed");
        }

        //Build tweet if text is not empty and 2 coords are specified
        Tweet tweet = new Tweet();
        tweet.setText(text);
        Coordinates coords = new Coordinates();
        double[] lat_lon_vals = null;
        try {
            lat_lon_vals = Arrays.stream(StringUtils.split(lat_lon, ":")).mapToDouble(Double::parseDouble).toArray();

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Lat/Lon values are not numeric", e);
        }

        //twitter stores (lon, lat) while input is given as (lat, lon)
        ArrayUtils.reverse(lat_lon_vals);
        coords.setCoordinates(lat_lon_vals);
        tweet.setCoordinates(coords);
        return tweet;
    }
}
