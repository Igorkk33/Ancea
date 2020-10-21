package com.projetofinal.ancea.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.projetofinal.ancea.MainActivity;
import com.projetofinal.ancea.HomeActivity;
import com.projetofinal.ancea.R;
import com.projetofinal.ancea.data.model.Medico;
import com.projetofinal.ancea.data.model.Paciente;
import com.projetofinal.ancea.helper.ConfiguracaoFirebase;
import com.projetofinal.ancea.helper.UsuarioFirebase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText campoEmail, campoSenha;
    private Button login;
    private FirebaseAuth autenticacao;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.buttonEntrada);
        campoEmail = findViewById(R.id.editEntradaEmail);
        campoSenha = findViewById(R.id.editEntradaSenha);

    }

    public void validarLogin(View view){

        //Recuperar textos dos campos
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if( !textoEmail.isEmpty() ) {//verifica e-mail
            if( !textoSenha.isEmpty() ) {//verifica senha
                Paciente paciente = new Paciente();
                paciente.setEmail(textoEmail);
                paciente.setSenha(textoSenha);

                Medico medico = new Medico();
                medico.setEmail(textoEmail);
                medico.setSenha(textoSenha);

                reference = reference.child("users");
                Query pesquisaEmail = reference.orderByChild("email").equalTo(textoEmail);
                Query pesquisaSenha = reference.orderByChild("senha").equalTo(textoSenha);
                pesquisaEmail.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getKey().equals(medico)){
                            medico.setEmail(snapshot.getValue().toString());
                        }else {
                            paciente.setEmail(snapshot.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                pesquisaSenha.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getKey().equals(medico)){
                            medico.setSenha(snapshot.getValue().toString());
                            logarMedico(medico);
                        }else{
                            paciente.setSenha(snapshot.getValue().toString());
                            logarPaciente(paciente);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }else{
                Toast.makeText(LoginActivity.this,
                        "Preencha a senha!",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(LoginActivity.this,
                    "Preencha o email!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void logarPaciente(Paciente paciente){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                paciente.getEmail(), paciente.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful() ){

                    //Verificar o tipo de usuário logado
                    // "Motorista" / "Passageiro"
                    UsuarioFirebase.redirecionaUsuarioLogado(LoginActivity.this);

                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthInvalidUserException e ) {
                        excecao = "Usuário não está cadastrado.";
                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void logarMedico(Medico medico){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                medico.getEmail(), medico.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful() ){

                    //Verificar o tipo de usuário logado
                    // "Motorista" / "Passageiro"
                    UsuarioFirebase.redirecionaUsuarioLogado(LoginActivity.this);

                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthInvalidUserException e ) {
                        excecao = "Usuário não está cadastrado.";
                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}


