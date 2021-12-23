package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "created_at",
        "id",
        "id_str",
        "text",
        "entities",
        "coordinates",
        "retweet_count",
        "favorite_count",
        "favorited",
        "retweeted"
})
public class Tweet {

    @JsonProperty("created_at")
    private String created_at;
    @JsonProperty("id")
    private String id;
    @JsonProperty("id_str")
    private String id_str;
    @JsonProperty("text")
    private String text;
    @JsonProperty("entities")
    private Entities entities = null;
    @JsonProperty("coordinates")
    private Coordinates coordinates = null;
    @JsonProperty("retweet_count")
    private String retweet_count;
    @JsonProperty("favorite_count")
    private String favorite_count;
    @JsonProperty("favorited")
    private String favorited;
    @JsonProperty("retweeted")
    private String retweeted;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getRetweet_count() {
        return retweet_count;
    }

    public void setRetweet_count(String retweet_count) {
        this.retweet_count = retweet_count;
    }

    public String getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(String favorite_count) {
        this.favorite_count = favorite_count;
    }

    public String getFavorited() {
        return favorited;
    }

    public void setFavorited(String favorited) {
        this.favorited = favorited;
    }

    public String getRetweeted() {
        return retweeted;
    }

    public void setRetweeted(String retweeted) {
        this.retweeted = retweeted;
    }

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

    public static void main(String[] args) throws IOException {
        Tweet tweet = toObjectFromJson(tweetStr, Tweet.class);
        System.out.println(toJson(tweet, true, false));
    }

}
