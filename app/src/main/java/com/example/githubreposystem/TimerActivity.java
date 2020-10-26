package com.example.githubreposystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {
    TextView tvTimer;
    Button btnStop, btnStart;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        btnStart = findViewById(R.id.button);
        btnStop = findViewById(R.id.button2);
        tvTimer = findViewById(R.id.textView);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Thread thread = new Thread() {

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
                btnStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        thread.interrupt();
                    }
                });
            }
        });
    }
}