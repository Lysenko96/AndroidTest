package com.example.root.smstutorial;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    String SENT_SMS = "SENT_SMS";
    String DELIVER_SMS = "DELIVER_SMS";

    Intent sent_intent = new Intent(SENT_SMS);
    Intent deliver_intent = new Intent(DELIVER_SMS);

    PendingIntent sent_pi, deliver_pi;

    EditText text,adress;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sent_pi = PendingIntent.getBroadcast(this,0, new Intent(SENT_SMS),0);
        deliver_pi = PendingIntent.getBroadcast(this,0,new Intent(DELIVER_SMS),0);


        text = (EditText) findViewById(R.id.adress);
        adress = (EditText) findViewById(R.id.text);

        button = (Button) findViewById(R.id.sentBtm);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(adress.getText().toString(),null,text.getText().toString(),sent_pi,deliver_pi);
                //smsManager.sendMultimediaMessage(adress.getContext(), null, text.getText().toString(), null, deliver_pi);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(sentReciver,new IntentFilter(SENT_SMS));
        registerReceiver(deliverReciver,new IntentFilter(DELIVER_SMS));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(sentReciver);
        unregisterReceiver(deliverReciver);
    }

    BroadcastReceiver sentReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode())
            {
                case Activity.RESULT_OK:
                    Toast.makeText(context,"Sented",Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(context,"Error sent", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    BroadcastReceiver deliverReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode())
            {
                case Activity.RESULT_OK:
                    Toast.makeText(context,"Delivered",Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(context,"Error deliver", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}
