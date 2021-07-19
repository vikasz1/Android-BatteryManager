package com.example.batterymanagerjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    private TextView battery;
    private TextView charger;
    MediaPlayer player;
    MediaPlayer stop;
    Button button;

    private BroadcastReceiver mBatInfoReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            battery.setText(String.valueOf(level)+"%");
            progressBar = findViewById(R.id.progressBar);
            progressBar.setProgress(level);

// Battery Temprature
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(MainActivity.this, String.valueOf(temperature/10)+" Degree Celcius", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,10,600);
                    toast.show();
                }
            });


            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status ==
                    BatteryManager.BATTERY_STATUS_FULL;
            if (isCharging) {
                charger.setText(String.valueOf("Charger Conected"));
                charger.setTextColor(Color.GREEN);
//                stop.start();
            }
            else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                charger.setText(String.valueOf("Charger Disconected"));
                charger.setTextColor(Color.RED);
//                player.start();
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player=MediaPlayer.create(this,R.raw.in);
        stop=MediaPlayer.create(this,R.raw.out);

        charger = (TextView) this.findViewById(R.id.textView);
        battery = (TextView) this.findViewById(R.id.percentage);
        this.registerReceiver(this.mBatInfoReciever, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        button = findViewById(R.id.btn);

    }

}