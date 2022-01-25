package com.stopgroup.stopcar.client.Helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.ChooseAdapter;

import java.util.ArrayList;


/**
 * <h1>Implement reusable dialogs</h1>
 * Dialogs class for all dialogs and toasts
 * <p>
 *
 * @author kemo94
 * @version 1.0
 * @since 2017-08-9
 */

public abstract class Dialogs {


    public static Dialog progressDialog;

    public static Dialog noInternetDialog;



    public static void showToast(String message, Activity activity) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public interface DialogListener {
        void onChoose(int pos);
    }

    public static void showLoadingDialog(Activity activity) {
        dismissLoadingDialog();
        try {
            progressDialog = new Dialog(activity);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setContentView(R.layout.dialog_prog);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } catch (Exception e) {

        }
    }

    public static void dismissLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
            }
        }
    }


    public interface DialogSuccessListener {
        void onSuccess(String notes);
    }

    public static void enterNotes(Activity activity, final DialogSuccessListener listener) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_notes);
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
        dialog.getWindow().setLayout((width * 17 / 20), LinearLayout.LayoutParams.WRAP_CONTENT);

        View send = dialog.findViewById(R.id.send);
        final EditText notes = dialog.findViewById(R.id.notes);
        dialog.show();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSuccess(notes.getText().toString());
                dialog.dismiss();
            }
        });

    }

    public static void chooseDialog(Activity activity, String title, ArrayList<String> itemsStrings, final DialogListener listener) {

        ChooseAdapter chooseListAdapter = new ChooseAdapter(activity, itemsStrings);
        final AlertDialog chooseAlertDialog = new AlertDialog.Builder(activity).create();

        final View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_choose_place, null, false);
        TextView listTitleText = dialogView.findViewById(R.id.list_title_text);

        RecyclerView chooseRecycler = dialogView.findViewById(R.id.choose_recycler);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chooseRecycler.setHasFixedSize(true);
        chooseRecycler.setLayoutManager(linearLayoutManager);


        listTitleText.setText(title);

        chooseRecycler.setAdapter(chooseListAdapter);

        chooseListAdapter.setClickListener(new ChooseAdapter.ClickListener() {
            @Override
            public void onClick(int pos) {
                listener.onChoose(pos);
                chooseAlertDialog.dismiss();
            }
        });

        chooseAlertDialog.setView(dialogView);

        chooseAlertDialog.show();
    }

}
