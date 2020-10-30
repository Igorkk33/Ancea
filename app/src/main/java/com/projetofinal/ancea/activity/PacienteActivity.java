package com.projetofinal.ancea.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.projetofinal.ancea.R;
import com.projetofinal.ancea.data.model.Usuario;
import com.projetofinal.ancea.helper.UsuarioFirebase;

public class PacienteActivity extends AppCompatActivity {

    private Usuario usuario;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        usuario = UsuarioFirebase.getDadosUsuarioLogado();
    }
}
