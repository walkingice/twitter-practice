package julian.test.twitter.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.JsonArray;

import java.util.List;
import java.util.concurrent.TimeUnit;

import julian.test.twitter.App;
import julian.test.twitter.R;
import julian.test.twitter.api.model.Tweet;
import julian.test.twitter.utils.Util;
import rx.Observable;

public class TwitterApi {
    private static String TAG = App.TAG;
    private static TwitterApi sSingleton;
    private Context mCtx;

    private List<Tweet> mFakeTweets;

    private TwitterClient mClient;

    private TwitterApi(Context ctx) {
        mCtx = ctx;
        prepareFakeData();
    }

    public static TwitterApi init(Context ctx) {
        if (sSingleton == null) {
            sSingleton = new TwitterApi(ctx);
        }
        return sSingleton;
    }

    public static void destroy() {
        sSingleton = null;
    }

    private void prepareFakeData() {
        JsonArray array = Util.readJsonArrayFromAsset(mCtx, "fake-timeline.json");
        mFakeTweets = Util.toTweets(array);
    }

    public void buildTwitterClient() {
        /* Prepare tokens and secrets */
        // if user provides values from Preferences, use it. Otherwise use file *gradle.properties*
        // as default values.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        String consumerKey = prefs.getString(mCtx.getString(R.string.key_consumer_key),
                mCtx.getString(R.string.consumer_key_from_gradle));
        String consumerSecret = prefs.getString(mCtx.getString(R.string.key_consumer_secret),
                mCtx.getString(R.string.consumer_secret_from_gradle));
        String accessToken = prefs.getString(mCtx.getString(R.string.key_access_token),
                mCtx.getString(R.string.access_token_from_gradle));
        String accessSecret = prefs.getString(mCtx.getString(R.string.key_access_secret),
                mCtx.getString(R.string.access_secret_from_gradle));

        mClient = ServiceGenerator.createService(TwitterClient.class,
                consumerKey, consumerSecret, accessToken, accessSecret);
    }

    public boolean isUsingFake() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        return prefs.getBoolean(mCtx.getString(R.string.key_use_fake), false);
    }

    public Observable<List<Tweet>> readTweets() {
        boolean useFake = isUsingFake();
        return useFake ? readTweetsFake() : readTweetsAjax();
    }

    private Observable<List<Tweet>> readTweetsFake() {
        Log.d(TAG, "Providing fake data");
        return Observable.just(mFakeTweets).delay(500, TimeUnit.MILLISECONDS);
    }

    private Observable<List<Tweet>> readTweetsAjax() {
        Observable<List<Tweet>> ob = mClient.getHomeTimeline();
        return ob;
    }
}
