package julian.test.twitter;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ItemBridgeAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import julian.test.twitter.api.TwitterApi;
import julian.test.twitter.view.TwitterPresenterSelector;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = App.TAG;

    @Bind(R.id.main_list)
    RecyclerView mList;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private TwitterApi mApi;
    private ArrayObjectAdapter mDataAdapter;
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = TwitterApi.init(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TwitterApi.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // configuration might be changed, rebuild api client
        mApi.buildTwitterClient();
        if (mDataAdapter.size() == 0) {
            readTweets();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Leaving screen but there is pending stuff.
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            Log.d(TAG, "unsubscribed");
        }
        mSubscription = null;
    }

    private void initViews() {
        initToolbar();
        mDataAdapter = new ArrayObjectAdapter(new TwitterPresenterSelector());
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(new ItemBridgeAdapter(mDataAdapter));
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setLogo(R.drawable.ic_logo);
        mToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_msg:
                    break;
                case R.id.menu_ring:
                    break;
                case R.id.menu_search:
                    break;
                case R.id.menu_settings:
                    startActivity(new Intent(this, SettingsActivity.class));
                    break;
            }
            return true;
        });
    }

    private void readTweets() {
        if (mApi.isUsingFake()) {
            Toast.makeText(this, "Read tweets from fake data", Toast.LENGTH_LONG).show();
        }
        // TODO: Scroll down to get more tweets.
        mSubscription = Observable.just("last_id")
                .subscribeOn(Schedulers.newThread())
                .flatMap((String lastId) -> {
                    Log.d(TAG, "Last tweet id: " + lastId);
                    return mApi.readTweets();
                })
                .concatMap(tweets -> Observable.from(tweets))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tweet -> {
                    Log.d(TAG, "got tweet: " + tweet.toString());
                    mDataAdapter.add(tweet);  // adapter will notify itself
                }, throwable -> {
                    throwable.printStackTrace();
                    (new AlertDialog.Builder(this))
                            .setTitle("Error")
                            .setCancelable(true)
                            .setMessage(throwable.toString())
                            .setPositiveButton("Ok", null)
                            .show();
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}
