package com.projetofinal.ancea.api;

import com.projetofinal.ancea.data.model.NotificacaoDados;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificacaoService {

    @Headers({
            "Authorization: Bearer AAAA7nN4tP0:APA91bFllY6uJU8857blVmaRdNTNWWFjjqRn96_ND0U7acfRxxlaB86qSttEu2vuEE-2s5KshTmNZmSZeeCR4lFoFDL-ItgoiVuk5J8Rob60uRErgtVQRvgOP0ZN13o6Mlkx67m8KBO-",
            "Content-Type:application/json"
    })
    @POST("send")
    Call<NotificacaoDados> salvarNotificacao(@Body NotificacaoDados notificacaoDados);
}
