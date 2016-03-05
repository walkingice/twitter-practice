package julian.test.twitter.view;

import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import julian.test.twitter.R;
import julian.test.twitter.api.model.MediaEntity;
import julian.test.twitter.api.model.Tweet;

/* To present a row with only one image. */
public class OneImgPresenter extends ContentPresenter {

    public OneImgPresenter() {
        this.mLayoutRes = R.layout.row_oneimg_layout;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        super.onBindViewHolder(viewHolder, item);
        View box = viewHolder.view;
        safeFillImg(box, R.id.row_img1, (Tweet) item, 0);
    }

    private void safeFillImg(View box, int viewId, Tweet tweet, int idx) {
        ImageView imgView = (ImageView) box.findViewById(viewId);
        try {
            MediaEntity entity = tweet.extendedEtities.media.get(idx);
            Picasso.with(box.getContext()).load(entity.mediaUrl)
                    .placeholder(R.drawable.empty_image)
                    .error(R.drawable.empty_image)
                    .into(imgView);
        } catch (Exception e) {
            Picasso.with(box.getContext()).load(R.drawable.empty_image).fit().into(imgView);
            e.printStackTrace();
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        super.onUnbindViewHolder(viewHolder);
        ImageView img = (ImageView) viewHolder.view.findViewById(R.id.row_img1);
        Picasso.with(viewHolder.view.getContext()).cancelRequest(img);
    }
}

