package julian.test.twitter.view;

import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

import julian.test.twitter.api.model.Tweet;

/* To pick corresponding Presenter, according to content of data*/
public class TwitterPresenterSelector extends PresenterSelector {

    /* No complex logic here, so use Array should be fastest. */
    private Presenter[] mPresenters;
    private final static int INVALID = 0;
    private final static int CONTENT = 1;
    private final static int ONEIMG = 2;
    private final static int TWOIMGS = 3;

    public TwitterPresenterSelector() {
        mPresenters = new Presenter[4];
        mPresenters[INVALID] = new InvalidPresenter();
        mPresenters[CONTENT] = new ContentPresenter();
        mPresenters[ONEIMG] = new OneImgPresenter();
        mPresenters[TWOIMGS] = new TwoImgsPresenter();
    }

    @Override
    public Presenter getPresenter(Object item) {
        if (!(item instanceof Tweet)) {
            // something go wrong, render by fallback
            return mPresenters[INVALID];
        }
        Tweet tweet = (Tweet) item;
        if (tweet.extendedEtities == null) {
            return mPresenters[CONTENT];
        } else if (tweet.extendedEtities.media.size() == 1) {
            return mPresenters[ONEIMG];
        } else {
            // item has more than one image
            return mPresenters[TWOIMGS];
        }
    }

    @Override
    public Presenter[] getPresenters() {
        return mPresenters;
    }
}
