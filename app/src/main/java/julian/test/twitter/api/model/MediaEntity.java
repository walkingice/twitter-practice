package julian.test.twitter.api.model;

import com.google.gson.annotations.SerializedName;

/* Represent data structure from Twitter JSON API. */
// refer: https://github.com/twitter/twitter-kit-android/blob/master/twitter-core/src/main/java/com/twitter/sdk/android/core/models/MediaEntity.java
public class MediaEntity {
    @SerializedName("media_url")
    public String mediaUrl;
}
