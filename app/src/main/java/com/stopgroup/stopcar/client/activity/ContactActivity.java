package com.stopgroup.stopcar.client.activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stopgroup.stopcar.client.Helper.Closure;
import com.stopgroup.stopcar.client.Helper.CurrentActivity;
import com.stopgroup.stopcar.client.Helper.Dialogs;
import com.stopgroup.stopcar.client.Helper.HttpHelper;
import com.stopgroup.stopcar.client.Helper.camera.ImageConverter;
import com.stopgroup.stopcar.client.Modules.Contact;
import com.stopgroup.stopcar.client.Modules.ContactsJson;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.Contactadapter;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    ArrayList<Contact> mContacts = new ArrayList<>();
    private ImageView back;
    private TextView title;
    private RecyclerView list;
    Contactadapter catogaryadapter;
    private ImageView search;
    private ImageView save;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CurrentActivity.setInstance(this);
        setContentView(R.layout.activity_contact);
        initView();
        onclick();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        list = findViewById(R.id.list);
        LinearLayoutManager layoutManage = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(layoutManage);
        catogaryadapter = new Contactadapter(mContacts);
        list.setAdapter(catogaryadapter);
        new FetchContacts().execute();
        search = findViewById(R.id.search);
        save = findViewById(R.id.save);
        title.setText(getString(R.string.emergency_contacts));

        progress = findViewById(R.id.progress);
    }

    private class FetchContacts extends AsyncTask<Void, Void, ArrayList<Contact>> {

        private final String DISPLAY_NAME = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME;

        private final String FILTER = DISPLAY_NAME + " NOT LIKE '%@%'";

        private final String ORDER = String.format("%1$s COLLATE NOCASE", DISPLAY_NAME);

        @SuppressLint("InlinedApi")
        private final String[] PROJECTION = {
                ContactsContract.Contacts._ID,
                DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };
        private int counter = 0;
        @Override
        protected ArrayList<Contact> doInBackground(Void... params) {
            try {

                ArrayList<Contact> contacts = new ArrayList<>();

                ContentResolver cr = getContentResolver();
                Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, FILTER, null, ORDER);
                if (cursor != null && cursor.moveToFirst()) {

                    do {
                        // get the contact's information
                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                        Integer hasPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        // get the user's email address
                        String email = null;
                        Cursor ce = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                        if (ce != null && ce.moveToFirst()) {
                            email = ce.getString(ce.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            ce.close();
                        }

                        // get the user's phone number
                        String phone = null;
                        if (hasPhone > 0) {
                            Cursor cp = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                            if (cp != null && cp.moveToFirst()) {
                                phone = cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                cp.close();
                            }
                        }

                        Uri u = getPhotoUri(cr , id );

                        // if the user user has an email or phone then add it to contacts
                        if ((!TextUtils.isEmpty(phone))) {
                            Contact contact = new Contact();
                            contact.name = name;
                            contact.email = email;
                            contact.phone = phone;
                            contact.image = u;
                            contacts.add(contact);
                        }

                        counter++;
                      //  if (counter == 10){
                     //       return contacts;
                     //   }

                    } while (cursor.moveToNext());

                    // clean up cursor
                    cursor.close();
                }

                return contacts;
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Contact> contacts) {
            if (contacts != null) {
                // success
                mContacts.addAll(contacts);
            } else {
                // show failure
                // syncFailed();
            }
            catogaryadapter.notifyDataSetChanged();
            progress.setVisibility(View.GONE);
        }
    }

    private void onclick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (catogaryadapter.pos == -1) {
                    Dialogs.showToast(getString(R.string.select_contact), ContactActivity.this);
                } else {
                    addcontact();
                }
            }
        });

    }

    public Uri getPhotoUri(ContentResolver ctx , String id) {
        try {

            Cursor cur = this.getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + "=" + id + " AND "
                            + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,
                    null);

            if (cur != null) {
                if (!cur.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }
            cur.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long
                .parseLong(id));
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }

    private void addcontact() {
        ContactsJson model = new ContactsJson();

        for (int i = 0; i < catogaryadapter.selected.size(); i++) {
            try{
                ContactsJson.ResultContactsModel item = new ContactsJson.ResultContactsModel();
                item.name = mContacts.get(catogaryadapter.selected.get(i)).name;
                item.mobile = mContacts.get(catogaryadapter.selected.get(i)).phone;
                item.imageData = ImageConverter.convert(mContacts.get(catogaryadapter.selected.get(i)).bitMap);
                model.result.add(item);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        String json = new Gson().toJson(model);
        HttpHelper.httpHelper.paramters.put("contacts",json);

        HttpHelper.httpHelper.post("client_contacts/store/contacts", new Closure<String>() {
            @Override
            public void run(String args) {
                super.run(args);
                //EmergencyContacts data = new Parsing<EmergencyContacts>().model(args);
                Dialogs.showToast(getString(R.string.add_success),ContactActivity.this);
                onBackPressed();
            }
        });
//        RequestParams requestParams = new RequestParams();
//        requestParams.put("name", mContacts.get(catogaryadapter.pos).name);
//        requestParams.put("mobile", mContacts.get(catogaryadapter.pos).phone);
//        APIModel.postMethod(ContactActivity.this, "client_contacts", requestParams, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//
//                APIModel.handleFailure(ContactActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
//                    @Override
//                    public void onRefresh() {
//                        addcontact();
//                    }
//                });
//
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                Dialogs.showToast(getString(R.string.add_success),ContactActivity.this);
//                onBackPressed();
//            }
//        });
    }


}
