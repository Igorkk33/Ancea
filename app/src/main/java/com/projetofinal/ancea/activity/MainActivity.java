package com.projetofinal.ancea.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.projetofinal.ancea.R;
import com.projetofinal.ancea.api.NotificacaoService;
import com.projetofinal.ancea.data.model.Notificacao;
import com.projetofinal.ancea.data.model.NotificacaoDados;
import com.projetofinal.ancea.ui.login.CadastroActivity;
import com.projetofinal.ancea.ui.login.LoginActivity;

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

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " +
                                "AAAA7nN4tP0:APA91bFllY6uJU8857blVmaRdNTNWWFjjqRn96_ND0U7acfRxxlaB86qSttEu2vuEE-2s5KshTmNZmSZeeCR4lFoFDL-ItgoiVuk5J8Rob60uRErgtVQRvgOP0ZN13o6Mlkx67m8KBO-")
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        baseUrl = "https://fcm.googleapis.com/v1/projects/ancea-f0334/messages/";
        retrofit = new Retrofit.Builder().client(client).baseUrl(baseUrl)
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
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i("token: ", s);
            }
        });
    }
}


