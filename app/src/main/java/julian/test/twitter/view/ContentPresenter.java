package julian.test.twitter.view;

import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import julian.test.twitter.R;
import julian.test.twitter.api.model.Tweet;
import julian.test.twitter.utils.Util;

/* To present a row only with text content. */
public class ContentPresenter extends Presenter {

    protected int mLayoutRes;

    public ContentPresenter() {
        mLayoutRes = R.layout.row_content_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(mLayoutRes, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Tweet data = (Tweet) item;
        MyViewHolder myHolder = (MyViewHolder) viewHolder;
        myHolder.mName.setText(data.user.name);
        myHolder.mScreenName.setText(data.user.screenName);
        myHolder.mContent.setText(data.text);
        myHolder.mTime.setText(Util.getDisplayDate(data.createdAt));

        Picasso.with(myHolder.view.getContext())
                .load(data.user.profileImageUrl)
                .fit()
                .placeholder(R.drawable.empty_avatar)
                .error(R.drawable.empty_avatar)
                .into(myHolder.mAvatar);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        View box = viewHolder.view;
        ImageView avatar = (ImageView) box.findViewById(R.id.row_avatar);
        Picasso.with(viewHolder.view.getContext()).cancelRequest(avatar);
    }

    // extends Presenter.ViewHolder
    class MyViewHolder extends ViewHolder {
        @Bind(R.id.row_avatar)
        ImageView mAvatar;

        @Bind(R.id.row_name)
        TextView mName;

        @Bind(R.id.row_screen_name)
        TextView mScreenName;

        @Bind(R.id.row_content)
        TextView mContent;

        @Bind(R.id.row_time)
        TextView mTime;

        MyViewHolder(View box) {
            super(box);
            ButterKnife.bind(this, box);
        }
    }
}

