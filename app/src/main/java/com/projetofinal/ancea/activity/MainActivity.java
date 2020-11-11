package com.projetofinal.ancea.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.projetofinal.ancea.R;
import com.projetofinal.ancea.api.NotificacaoService;
import com.projetofinal.ancea.data.model.Notificacao;
import com.projetofinal.ancea.data.model.NotificacaoDados;
import com.projetofinal.ancea.helper.UsuarioFirebase;
import com.projetofinal.ancea.ui.login.CadastroActivity;
import com.projetofinal.ancea.ui.login.LoginActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Retrofit retrofit;
    private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseUrl = "https://fcm.googleapis.com/fcm/";
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public void abrirTelaLogin(View view){
        startActivity( new Intent(this, LoginActivity.class));
    }

    public void abrirTelaCadastro(View view){
        startActivity( new Intent(this, CadastroActivity.class));
    }

    public void enviarNotificacao(View view){
        String to = "";
        Notificacao notificacao = new Notificacao("título", "corpo");
        NotificacaoDados notificacaoDados = new NotificacaoDados(to, notificacao);
        NotificacaoService notificacaoService = retrofit.create(NotificacaoService.class);
        Call<NotificacaoDados> call = notificacaoService.salvarNotificacao(notificacaoDados);

        call.enqueue(new Callback<NotificacaoDados>() {
            @Override
            public void onResponse(@NotNull Call<NotificacaoDados> call, @NotNull Response<NotificacaoDados> response) {
                
            }

            @Override
            public void onFailure(@NotNull Call<NotificacaoDados> call, @NotNull Throwable t) {

            }
        });
    }

    public void recuperarToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Resultado", "Fetching FCM registration token failed", task.getException());
                        }

                    }
                });
    }
}


