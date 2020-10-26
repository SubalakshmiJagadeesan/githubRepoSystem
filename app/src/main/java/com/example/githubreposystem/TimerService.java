package com.example.githubreposystem;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class TimerService extends Service {
    public TimerService() {
    }

    int counter;
    boolean isStopped = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        timerFunction();
        return START_NOT_STICKY;
    }

    void timerFunction() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isStopped) {
                    broadcastValue(++counter);
                    timerFunction();
                }
            }
        }, 1000);
    }

    void broadcastValue(int value) {
        Intent intent = new Intent("timer-activity-local-broadcast");
        intent.putExtra("value", value);
        LocalBroadcastManager.getInstance(TimerService.this).sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isStopped = true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
