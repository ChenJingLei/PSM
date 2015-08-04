package com.example.cjl20.psm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by cjl20 on 2015/8/4.
 */
public class MessengerService extends Service {

    private SensorManager sm;
    private Sensor ligthSensor;

    @Override
    public void onCreate() {

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        ligthSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

        sm.registerListener(new SensorListener(), ligthSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public class SensorListener implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            float acc = event.accuracy;

            float lux = event.values[0];

            StringBuffer sb = new StringBuffer();

            sb.append("acc ----> " + acc);
            sb.append("\n");
            sb.append("lux ----> " + lux);
            sb.append("\n");

            if (lux == 0){
                System.out.println(11111111);
                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "My Tag");
                wl.acquire();
//                wl.release();
            }

            System.out.println(sb.toString());
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    public void onDestroy() {

        Intent localIntent = new Intent();
        localIntent.setClass(this, MessengerService.class); //销毁时重新启动Service
        this.startService(localIntent);

        // Tell the user we stopped.
        Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT).show();
    }

    /**
     * When binding to the service, we return an interface to our messenger
     * for sending messages to the service.
     */
    @Override
    public IBinder onBind(Intent intent) {

        sm.registerListener(new SensorListener(), ligthSensor, SensorManager.SENSOR_DELAY_NORMAL);
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, START_STICKY, startId);
    }


}