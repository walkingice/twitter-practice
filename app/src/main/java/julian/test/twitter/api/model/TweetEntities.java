package julian.test.twitter.api.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/* Represent data structure from Twitter JSON API. */
// refer: https://github.com/twitter/twitter-kit-android/blob/master/twitter-core/src/main/java/com/twitter/sdk/android/core/models/TweetEntities.java
public class TweetEntities {
    @SerializedName("media")
    public List<MediaEntity> media;
}
