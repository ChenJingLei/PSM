package com.example.cjl20.psm;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Switch power;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button stop = (Button) findViewById(R.id.stop);
        power = (Switch)findViewById(R.id.switchPower);
        stop.setOnClickListener(stopclick);


        if (isServiceRun(MainActivity.this)) power.setChecked(true);
        power.setChecked(false);

        if (!isServiceRun(MainActivity.this)){
            Intent s = new Intent(MainActivity.this, MessengerService.class);
            MainActivity.this.startService(s);
            System.out.println("服务线程手动开始");
            power.setChecked(true);
        }
    }

    View.OnClickListener stopclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isServiceRun(MainActivity.this)) {
                System.out.print(1111111111);
                Intent s = new Intent(MainActivity.this, MessengerService.class);
                MainActivity.this.stopService(s);
                System.out.println("服务线程手动停止");
            }
        }
    };

    // 判断服务线程是否存在
    public static boolean isServiceRun(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = am.getRunningServices(60);

        for (ActivityManager.RunningServiceInfo info : list) {
            if (info.service.getClassName().equals(
                    "com.example.cjl20.psm.MessengerService")) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("aaaaaaaaaaaaa");
        Intent s = new Intent(MainActivity.this, MessengerService.class);
        MainActivity.this.startService(s);
    }
}



