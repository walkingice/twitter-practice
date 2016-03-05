package julian.test.twitter.view;

import android.graphics.Color;
import android.support.v17.leanback.widget.Presenter;
import android.view.ViewGroup;
import android.widget.TextView;

/* Any data we don't know how to render, go here */
public class InvalidPresenter extends Presenter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        TextView v = new TextView(parent.getContext());
        v.setBackgroundColor(Color.RED); // be aware of me, please!
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        TextView textView = (TextView) viewHolder.view;
        textView.setText("Invalid data: " + item.toString());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        // I am simple!
    }
}

