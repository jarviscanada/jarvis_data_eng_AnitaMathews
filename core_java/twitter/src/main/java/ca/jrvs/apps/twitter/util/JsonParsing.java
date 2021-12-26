package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.io.IOException;
import java.util.Collections;

public class JsonParsing {

    public static <T> T toObjectFromJson(String json, Class clazz) throws IOException {
        ObjectMapper m = new ObjectMapper();
        return (T) m.readValue(json, clazz);
    }

    public static String toJson(Object object, boolean prettyJson, boolean includeNullValues, String[] fields) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        if (!includeNullValues) {
            m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        if (prettyJson) {
            m.enable(SerializationFeature.INDENT_OUTPUT);
        }

        if (fields != null) {
            SimpleFilterProvider filterProvider = new SimpleFilterProvider();
            filterProvider.addFilter("tweetFields", SimpleBeanPropertyFilter.filterOutAllExcept(fields));
            return m.writer(filterProvider).writeValueAsString(object);
        }
        else {
            SimpleFilterProvider filterProviderFull = new SimpleFilterProvider();
            filterProviderFull.addFilter("tweetFields", SimpleBeanPropertyFilter.serializeAllExcept((Collections.emptySet())));
            return m.writer(filterProviderFull).writeValueAsString(object);
        }

    }
/*
    public static void main(String[] args) throws IOException {
        String[] fields = {"id", "text", "coordinates"};
        Tweet tweet = toObjectFromJson(tweetStr, Tweet.class);
        System.out.println(toJson(tweet, true, false, null));
    }

 */
}
