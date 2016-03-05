package julian.test.twitter.api;

import java.util.List;

import julian.test.twitter.api.model.Tweet;
import retrofit2.http.GET;
import rx.Observable;

public interface TwitterClient {

    @GET("/1.1/statuses/home_timeline.json")
    Observable<List<Tweet>> getHomeTimeline();
}
