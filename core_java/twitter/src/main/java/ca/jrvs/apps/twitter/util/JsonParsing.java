package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JsonParsing {
    public static final String tweetStr = "{\n" +
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

    public static <T> T toObjectFromJson(String json, Class clazz) throws IOException {
        ObjectMapper m = new ObjectMapper();
        return (T) m.readValue(json, clazz);
    }

    public static String toJson(Object object, boolean prettyJson, boolean includeNullValues) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        if (!includeNullValues) {
            m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        if (prettyJson) {
            m.enable(SerializationFeature.INDENT_OUTPUT);
        }
        return m.writeValueAsString(object);
    }

    public static void main(String[] args) throws IOException {
        Tweet tweet = toObjectFromJson(tweetStr, Tweet.class);
        System.out.println(toJson(tweet, true, false));
    }
}
