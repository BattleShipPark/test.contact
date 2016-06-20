package com.battleshippark.test.contact;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class MainViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.name1)
    TextView name1;

    @BindView(R.id.name2)
    TextView name2;

    @BindView(R.id.name3)
    TextView name3;

    @BindView(R.id.name4)
    TextView name4;

    @BindView(R.id.name5)
    TextView name5;

    @BindView(R.id.name6)
    TextView name6;

    @BindView(R.id.name7)
    TextView name7;

    public MainViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(int position, MainData mainData) {
        name1.setText(mainData.displayName);
        name2.setText(mainData.nameDisplayName);
        name3.setText(mainData.nameFamilyName);
        name4.setText(mainData.nameMiddleName);
        name5.setText(mainData.nameGivenName);
        name6.setText(mainData.namePrefix);
        name7.setText(mainData.nameSuffix);
    }
}
