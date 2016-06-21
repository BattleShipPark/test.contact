package com.battleshippark.test.contact;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);

        load();
    }

    private void load() {
        Observable.create((Observable.OnSubscribe<List<MainData>>) subscriber -> {
            Log.i("TIME", String.valueOf(System.currentTimeMillis()/100));

            List<MainData> mainDataList = loadBasic();
            subscriber.onNext(mainDataList);
            Log.i("TIME", String.valueOf(System.currentTimeMillis()/100) + ", " +mainDataList.size());

            List<MainData> mainDataListWithName = mainDataList;
            for (int i = 0; i < mainDataList.size(); i++) {
                MainData mainData = mainDataList.get(i);
                mainDataListWithName.set(i, loadName(mainData));
            }
            subscriber.onNext(mainDataListWithName);
            Log.i("TIME", String.valueOf(System.currentTimeMillis()/100));

            List<MainData> mainDataListWithPhone = mainDataListWithName;
            for (int i = 0; i < mainDataListWithName.size(); i++) {
                MainData mainData = mainDataListWithName.get(i);
                mainDataListWithPhone.set(i, loadPhone(mainData));
            }
            subscriber.onNext(mainDataListWithPhone);
            Log.i("TIME", String.valueOf(System.currentTimeMillis()/100));

            List<MainData> mainDataListWithEmail = mainDataListWithPhone;
            for (int i = 0; i < mainDataListWithPhone.size(); i++) {
                MainData mainData = mainDataListWithPhone.get(i);
                mainDataListWithEmail.set(i, loadEmail(mainData));
            }
            subscriber.onNext(mainDataListWithEmail);
            Log.i("TIME", String.valueOf(System.currentTimeMillis()/100));

            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainDataList -> {
                    adapter.setItems(mainDataList);
                    adapter.notifyDataSetChanged();
                });
    }

    private List<MainData> loadBasic() {
        List<MainData> mainDataList = new ArrayList<>();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
        };

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            if(cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    MainData mainData = new MainData();
                    mainData.id = cursor.getLong(0);
                    mainData.displayName = cursor.getString(1);
                    mainDataList.add(mainData);
                }
            }
            cursor.close();
        }
        return mainDataList;
    }

    private MainData loadName(MainData mainData) {
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
                ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME,
                ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, //이름
                ContactsContract.CommonDataKinds.StructuredName.PREFIX, //호칭
                ContactsContract.CommonDataKinds.StructuredName.SUFFIX, //경칭

        };
        String selection = String.format("%s=? AND %s=?", ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID,
                ContactsContract.Data.MIMETYPE);
        String[] where = new String[]{String.valueOf(mainData.id), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
        Cursor cursor = getContentResolver().query(uri, projection, selection, where, null);
        if (cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();

                mainData.nameDisplayName = cursor.getString(0);
                mainData.nameFamilyName = cursor.getString(1);
                mainData.nameMiddleName = cursor.getString(2);
                mainData.nameGivenName = cursor.getString(3);
                mainData.namePrefix = cursor.getString(4);
                mainData.nameSuffix = cursor.getString(5);
            }
            cursor.close();
        }
        return mainData;
    }

    private MainData loadPhone(MainData mainData) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.LABEL, //직접 지정했을 때
        };
        String selection = String.format("%s=?", ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
        String[] where = new String[]{String.valueOf(mainData.id)};
        Cursor cursor = getContentResolver().query(uri, projection, selection, where, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    MainData.Phone phone = new MainData.Phone();
                    phone.phoneNormNumber = cursor.getString(0);
                    phone.phoneNumber = cursor.getString(1);
                    phone.phoneType = cursor.getString(2);
                    phone.phoneLabel = cursor.getString(3);
                    mainData.phones.add(phone);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return mainData;
    }

    private MainData loadEmail(MainData mainData) {
        Uri uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Email.LABEL,
                ContactsContract.CommonDataKinds.Email.TYPE,
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.DISPLAY_NAME,

        };
        String selection = String.format("%s=?", ContactsContract.CommonDataKinds.Email.CONTACT_ID);
        String[] where = new String[]{String.valueOf(mainData.id)};
        Cursor cursor = getContentResolver().query(uri, projection, selection, where, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    MainData.Email email = new MainData.Email();
                    email.emailLabel = cursor.getString(0);
                    email.emailType = cursor.getString(1);
                    email.emailAddres = cursor.getString(2);
                    email.emailDisplayName = cursor.getString(3);
                    mainData.emails.add(email);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return mainData;
    }
}
