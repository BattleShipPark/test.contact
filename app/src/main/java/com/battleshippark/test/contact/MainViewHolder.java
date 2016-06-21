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

    @BindView(R.id.phone1)
    TextView phone1;

    @BindView(R.id.phone2)
    TextView phone2;

    @BindView(R.id.phone3)
    TextView phone3;

    @BindView(R.id.phone4)
    TextView phone4;

    @BindView(R.id.email1)
    TextView email1;

    @BindView(R.id.email2)
    TextView email2;

    @BindView(R.id.email3)
    TextView email3;

    @BindView(R.id.email4)
    TextView email4;

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

        if (mainData.phones.size() > 0) {
            phone1.setText(makePhoneText(mainData.phones.get(0)));
        }
        if (mainData.phones.size() > 1) {
            phone2.setText(makePhoneText(mainData.phones.get(1)));
        }
        if (mainData.phones.size() > 2) {
            phone3.setText(makePhoneText(mainData.phones.get(2)));
        }
        if (mainData.phones.size() > 3) {
            phone4.setText(makePhoneText(mainData.phones.get(3)));
        }

        if (mainData.emails.size() > 0) {
            email1.setText(makeEmailText(mainData.emails.get(0)));
        }
        if (mainData.emails.size() > 1) {
            email2.setText(makeEmailText(mainData.emails.get(1)));
        }
        if (mainData.emails.size() > 2) {
            email3.setText(makeEmailText(mainData.emails.get(2)));
        }
        if (mainData.emails.size() > 3) {
            email4.setText(makeEmailText(mainData.emails.get(3)));
        }
    }

    private String makePhoneText(MainData.Phone phone) {
        return String.format("%s;%s;%s;%s", phone.phoneNormNumber, phone.phoneNumber, phone.phoneType, phone.phoneLabel);
    }

    private String makeEmailText(MainData.Email email) {
        return String.format("%s;%s;%s;%s", email.emailLabel, email.emailType, email.emailAddres, email.emailDisplayName);
    }
}
