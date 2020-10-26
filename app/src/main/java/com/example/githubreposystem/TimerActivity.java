package com.example.githubreposystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {
    TextView tvTimer;
    Button btnStop, btnStart,btnServiceStart, btnServiceStop;
    int i = 0;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        btnStart = findViewById(R.id.button);
        btnStop = findViewById(R.id.button2);
        tvTimer = findViewById(R.id.textView);
        btnServiceStart = findViewById(R.id.btnStartService);
        btnServiceStop = findViewById(R.id.btnStopService);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 0;
                thread = new Thread() {

                    @Override
                    public void run() {
                        super.run();
                        try {
                            while (!isInterrupted()) {
                                Thread.sleep(1000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvTimer.setText(String.valueOf(++i));
                                    }
                                });
                            }
                        } catch (Exception e) {}
                    }
                };
                thread.start();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thread.interrupt();
            }
        });

        btnServiceStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(TimerActivity.this,TimerService.class));
            }
        });
        btnServiceStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(TimerActivity.this,TimerService.class));
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("timer-activity-local-broadcast"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int vallue = intent.getIntExtra("value",0);
            tvTimer.setText(String.valueOf(vallue));
        }
    };
}