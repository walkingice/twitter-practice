package julian.test.twitter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import com.twitter.sdk.android.core.models.Tweet;

import junit.framework.TestCase;

import org.junit.Test;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TwitterApiTest extends TestCase {

    protected List<Tweet> mTweets;

    protected void setUp() throws Exception {
        JsonStreamParser parser = new JsonStreamParser(new FileReader("./src/test/assets/fake-timeline.json"));
        JsonArray array = parser.next().getAsJsonArray();
        mTweets = new ArrayList<>();
        Gson g = new Gson();
        for (JsonElement elmnt : array) {
            Tweet tweet = g.fromJson(elmnt, Tweet.class);
            mTweets.add(tweet);
        }
    }

    @Test
    public void testTweet() throws Exception {
        assertEquals(30, mTweets.size());
    }
}
