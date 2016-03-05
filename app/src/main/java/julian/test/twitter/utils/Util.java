package julian.test.twitter.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import julian.test.twitter.api.model.Tweet;

public class Util {
    // Tue Mar 08 07:00:16 +0000 2016
    public final static String TIMEFORMAT = "EEE MMM d kk:mm:ss Z yyyy";

    // **To specify Local is important.**
    private final static SimpleDateFormat sSDF = new SimpleDateFormat(TIMEFORMAT, Locale.ENGLISH);

    public static JsonArray readJsonArrayFromAsset(Context ctx, String assetPath) {
        JsonArray array = null;
        try {
            AssetManager mgr = ctx.getAssets();
            InputStreamReader reader = new InputStreamReader(mgr.open(assetPath));
            JsonStreamParser parser = new JsonStreamParser(reader);
            array = parser.next().getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static List<Tweet> toTweets(JsonArray array) {
        Gson g = new Gson();
        List<Tweet> tweets = new ArrayList<>();
        for (JsonElement elmnt : array) {
            Tweet tweet = g.fromJson(elmnt, Tweet.class);
            tweets.add(tweet);
        }
        return tweets;
    }

    public static JsonArray toJsonArray(List<Tweet> tweets) {
        JsonElement elmnt = new Gson().toJsonTree(tweets);
        return elmnt.getAsJsonArray();
    }

    /* To build better string such as "3 mins ago" */
    // TODO: i18n
    public static String getDisplayDate(String timestamp) {
        Calendar cal = parseDate(timestamp);
        Calendar now = Calendar.getInstance();

        long stpSec = cal.getTimeInMillis() / 1000;
        long nowSec = now.getTimeInMillis() / 1000;
        long offset = nowSec - stpSec;
        if (offset < 0) {
            // it should not happen
            return timestamp;
        } else if (offset < 120) { // 1 min
            return ((int) (offset / 60)) + " min ago";
        } else if (offset < 3600) { // 2 ~ 50 mins
            return ((int) (offset / 60)) + " mins ago";
        } else if (offset < 7200) { // 1 hr
            return ((int) (offset / 3600)) + " hr ago";
        } else if (offset < 86400) { // 2 ~ 23 hrs
            return ((int) (offset / 3600)) + " hrs ago";
        } else if (offset < 172800) { // 1 day
            return ((int) (offset / 86400)) + " day ago";
        } else if (offset < 2592000) { // 2 ~ 30 days
            return ((int) (offset / 86400)) + " days ago";
        } else if (offset < 5184000) { // 1 month
            return ((int) (offset / 2592000)) + " month ago";
        } else if (offset < 31536000) { // 12 months
            return ((int) (offset / 2592000)) + " months ago";
        } else { // more than one year
            return ((int) (offset / 31536000)) + " yrs ago";
        }
    }

    public static Calendar parseDate(String time) {
        try {
            sSDF.parse(time);
            return sSDF.getCalendar();
        } catch (ParseException e) {
            Log.e("parse date failed", e.toString());
        }

        return null;
    }
}
