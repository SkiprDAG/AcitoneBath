package com.example.acitonicbath;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Debug;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Objects;

public class ConnectionService extends Service {
    private final String TAG = "ConnectionService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.wtf(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.wtf(TAG, "onStartCommand");
        update();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                GregorianCalendar.getInstance().getTimeInMillis(),
                1000,
                getAlarmPendingIntent()
        );
        return super.onStartCommand(intent, flags, startId);
    }

    private PendingIntent getAlarmPendingIntent(){
        Intent alarmIntent = new Intent(this, MainActivity.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    void update(){
        new Thread((Runnable) () -> {
            while(true){
                String str = sendRequest();
                if(str.split(",")[1].equals("time1")){
                    sendNotification("Готово", "Твоё время пришло...");
                }
            }
        }).start();
    }

    public String sendRequest() {
        String serverResponse = "";
        try {
            URL website = new URL("http://"+"192.168.4.1"+":"+"80"+"/send?command="+"state,0");
            HttpURLConnection httpURLConnection = (HttpURLConnection)website.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            serverResponse = response.toString();
        } catch (Exception ex){
            System.out.println(Arrays.toString(ex.getStackTrace()));
            Log.i("sendRequest", "" + ex);
            return null;
        }
        return serverResponse;
    }

    public void sendNotification(String title, String text){
        final int NOTIFY_ID = 1;
        final String CHANNEL_ID = "Done";
        final int PRIORITY_HIGH = 1;
        final int DEFAULT_ALL = ~0;
        NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setPriority(PRIORITY_HIGH)
                        .setDefaults(DEFAULT_ALL);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.wtf(TAG, "onDestroy");
    }
}