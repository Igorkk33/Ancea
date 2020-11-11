package com.projetofinal.ancea.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.projetofinal.ancea.R;
import com.projetofinal.ancea.data.model.Ocorrencias;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OcorrenciaActivity extends AppCompatActivity {

    private TextView ocorrencia;
    private TextView crise;
    private TextView sono;
    private TextView estadoEmocional;
    private TextView exposicao;
    private TextView observacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocorrencia);
        ocorrencia = findViewById(R.id.textData);
        crise = findViewById(R.id.textCrise);
        sono = findViewById(R.id.textSono);
        estadoEmocional = findViewById(R.id.textEmocional);
        exposicao = findViewById(R.id.textEletronicos);
        observacoes = findViewById(R.id.textObservacoes);
    }


}