package com.example.cjl20.psm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by cjl20 on 2015/8/4.
 */
public class MessengerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 启动Service
        Intent s = new Intent(context, MessengerService.class);
        context.startService(s);
        System.out.println("Receiver start");
    }
}
