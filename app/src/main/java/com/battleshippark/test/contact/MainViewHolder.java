package com.battleshippark.test.contact;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class MainViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.textView1)
    TextView textView1;

    public MainViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(int position, MainData mainData) {
        textView1.setText(mainData.displayName);
    }
}
