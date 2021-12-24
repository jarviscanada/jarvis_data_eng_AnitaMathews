package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Arrays;
@JsonFilter("tweetFields")
@JsonIgnoreProperties(ignoreUnknown = true)
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



}
