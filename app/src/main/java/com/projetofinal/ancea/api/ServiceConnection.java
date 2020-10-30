package com.projetofinal.ancea.api;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.projetofinal.ancea.data.model.CountListener;

public class ServiceConnection extends Service implements CountListener {

    private int count;
    private boolean active;
    private Controller controller = new Controller();

    public class Controller extends Binder{

        public CountListener getCountListener(){
            return (ServiceConnection.this);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return controller;
    }

    @Override
    public void onCreate() {
        Log.i("Script", "onCreate()");
        super.onCreate();
        setThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setThread();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        active = false;
    }

    public void setThread(){
        count = 0;
        active = true;
        new Thread(){
            public void run(){
                while (active && count < 100){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    Log.i("Script", "count: " + count);
                }
            }
        }.start();
    }

    @Override
    public int getCount() {
        return count;
    }
}
