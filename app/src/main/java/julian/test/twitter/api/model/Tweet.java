package julian.test.twitter.api.model;

import com.google.gson.annotations.SerializedName;

/* Represent data structure from Twitter JSON API. */
// refer: https://github.com/twitter/twitter-kit-android/blob/master/twitter-core/src/main/java/com/twitter/sdk/android/core/models/Tweet.java
public class Tweet {
    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("lang")
    public String lang;

    @SerializedName("source")
    public String source;

    @SerializedName("text")
    public String text;

    @SerializedName("id")
    public long id;

    @SerializedName("user")
    public User user;

    @SerializedName("extended_entities")
    public TweetEntities extendedEtities;
}
