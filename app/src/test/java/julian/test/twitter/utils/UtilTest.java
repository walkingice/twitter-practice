package julian.test.twitter.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import com.twitter.sdk.android.core.models.Tweet;

import junit.framework.TestCase;

import org.junit.Test;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UtilTest extends TestCase {
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
    public void testRead() throws Exception {
        assertEquals(30, mTweets.size());
    }

    @Test
    public void testToJson() throws Exception {
        JsonArray array = Util.toJsonArray(mTweets);
        assertNotNull("Should got converted json array", array);
        assertEquals(30, array.size());
    }

    @Test
    public void testParseDate() {
        String dateStr = "Tue Jan 08 17:34:10 +0000 2008";
        Calendar calendar = Util.parseDate(dateStr);
        assertNotNull("Should parse smoothly", calendar);
    }
}
