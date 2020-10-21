package com.projetofinal.ancea;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.projetofinal.ancea.api.NotificacaoService;
import com.projetofinal.ancea.data.model.Notificacao;
import com.projetofinal.ancea.data.model.NotificacaoDados;
import com.projetofinal.ancea.helper.ConfiguracaoFirebase;
import com.projetofinal.ancea.ui.login.CadastroActivity;
import com.projetofinal.ancea.ui.login.LoginActivity;

import java.io.FileInputStream;
import java.io.IOException;

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
            public void onResponse(Call<NotificacaoDados> call, Response<NotificacaoDados> response) {
                
            }

            @Override
            public void onFailure(Call<NotificacaoDados> call, Throwable t) {

            }
        });
    }

    public void recuperarToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                Log.i("getInstanceId", "token getInstanceId" + token);
            }
        });
    }
}


