package com.stopgroup.stopcar.client.service;
/**
 * Created by TareQ on 11/15/2016.
 */
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.activity.HomeActivity;
import com.stopgroup.stopcar.client.activity.ReviewTripActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String body;
    String type;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("noti", remoteMessage.getData().toString() + "--");
        JSONObject jo = new JSONObject(remoteMessage.getData());
        if (remoteMessage.getData().toString().contains("\"type\":10")) {
            EventBus.getDefault().post("exit");
        } else {
            if (remoteMessage.getData().toString().contains("extras")) {
                type = "1";
            } else {
                type = "0";
            }
        }
        try {
            String trip_id = null;
            for (String key : remoteMessage.getData().keySet()) {
                if (key.equals("trip_id")) {

                    trip_id = String.valueOf(remoteMessage.getData().get("trip_id"));
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(remoteMessage.getData().get(key));
                        trip_id = jsonObject.getString("trip_id");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            buildNotif(jo.getString("title"), jo.getString("body"), trip_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void buildNotif(String title, String desc, String trip_id) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent notificationIntent ;
        if (type.equals("0") || trip_id != null ) {
            notificationIntent = new Intent(getApplicationContext(), HomeActivity.class);
            if (trip_id != null) {
                notificationIntent.putExtra("trip_id",trip_id);
            }
        } else {
            notificationIntent = new Intent(getApplicationContext(), ReviewTripActivity.class);
        }
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = null;
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.pin);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.pin)
                    .setLargeIcon(bitmap1)
                    .setContentTitle(title)
                    .setContentText(desc)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(contentIntent);
        } else {
            notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.pin)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(contentIntent);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        assert notificationManager != null;
        //Random random = new Random();
        int notificationID = 0;
        notificationManager.notify(notificationID, notificationBuilder.build());
    }
}
//