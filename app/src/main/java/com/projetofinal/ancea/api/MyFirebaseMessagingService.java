package com.projetofinal.ancea.api;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.projetofinal.ancea.HomeActivity;
import com.projetofinal.ancea.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            String titulo = remoteMessage.getNotification().getTitle();
            String corpo = remoteMessage.getNotification().getBody();
            enviarNotificacao(titulo, corpo);
        }

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    private void enviarNotificacao(String titulo, String corpo){
        String canal = getString(R.string.default_notification_channel_id);
        Uri uriSom = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(this, canal)
                .setContentTitle(titulo).setContentText(corpo).setSmallIcon(R.drawable.ic_baseline_info_24)
                .setSound(uriSom).setAutoCancel(true).setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //procedimento necessário para dispositivos a partir da api 26 (Oreo)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(canal, "Aviso", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        //envia notificação
        notificationManager.notify(0, notificacao.build());
    }
}
