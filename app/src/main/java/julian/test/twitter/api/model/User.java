package julian.test.twitter.api.model;

import com.google.gson.annotations.SerializedName;

/* Represent data structure from Twitter JSON API. */
// refer: https://github.com/twitter/twitter-kit-android/blob/master/twitter-core/src/main/java/com/twitter/sdk/android/core/models/User.java
public class User {
    @SerializedName("name")
    public String name;

    @SerializedName("screen_name")
    public String screenName;

    @SerializedName("profile_image_url")
    public String profileImageUrl;
}
