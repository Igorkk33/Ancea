package com.projetofinal.ancea.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.projetofinal.ancea.R;
import com.projetofinal.ancea.data.model.Usuario;
import com.projetofinal.ancea.helper.ConfiguracaoFirebase;

public class MedicoActivity extends AppCompatActivity {

    private Usuario paciente;
    private DatabaseReference databaseReference;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);
        databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sair){
            autenticacao.signOut();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public Usuario getPaciente() {
        return paciente;
    }

    public void setPaciente(Usuario paciente) {
        this.paciente = paciente;
    }
}
