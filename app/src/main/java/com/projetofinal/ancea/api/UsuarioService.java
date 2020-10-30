package com.projetofinal.ancea.api;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService extends Service {

    public List<Worker> threads = new ArrayList<Worker>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("Script","onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Script","onStartCommand");
        Worker worker = new Worker(startId);
        worker.start();
        threads.add(worker);
        return super.onStartCommand(intent, flags, startId);
    }

    class Worker extends Thread{
        int startId;
        int count = 20;
        boolean ativo = true;

        public Worker (int startId){
            this.startId = startId;
        }

        public void run(){
            while (ativo && count < 20){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                Log.i("Script", "COUNT: " + count);
            }
            stopSelf(startId);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (int i = 0, tam = threads.size(); i < tam; i++){
            threads.get(i).ativo = false;
        }
    }
}
