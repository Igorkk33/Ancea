package com.projetofinal.ancea.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.projetofinal.ancea.HomeActivity;
import com.projetofinal.ancea.R;
import com.projetofinal.ancea.data.model.Medico;
import com.projetofinal.ancea.data.model.Paciente;
import com.projetofinal.ancea.helper.ConfiguracaoFirebase;
import com.projetofinal.ancea.helper.UsuarioFirebase;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText campoNome, campoEmail, campoSenha;
    private Switch switchTipoUsuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Inicializar componentes
        campoNome  = findViewById(R.id.editCadastroNome);
        campoEmail = findViewById(R.id.editEntradaEmail);
        campoSenha = findViewById(R.id.editEntradaSenha);
        switchTipoUsuario = findViewById(R.id.switchTipoUsuario);

    }

    public void validarCadastroUsuario(View view){

        //Recuperar textos dos campos
        String textoNome  = campoNome.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if( !textoNome.isEmpty() ) {//verifica nome
            if( !textoEmail.isEmpty() ) {//verifica e-mail
                if( !textoSenha.isEmpty() ) {//verifica senha
                    if (verificaTipoUsuario() == "P"){
                        Paciente paciente = new Paciente();
                        paciente.setNome(textoNome);
                        paciente.setEmail(textoEmail);
                        paciente.setSenha(textoSenha);
                        cadastrarPaciente(paciente);
                    }else {
                        Medico medico = new Medico();
                        medico.setNome(textoNome);
                        medico.setEmail(textoEmail);
                        medico.setSenha(textoSenha);
                        cadastrarMedico(medico);
                    }
                }else {
                    Toast.makeText(CadastroActivity.this,
                            "Preencha a senha!",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(CadastroActivity.this,
                        "Preencha o email!",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(CadastroActivity.this,
                    "Preencha o nome!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void cadastrarPaciente(final Paciente paciente){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                paciente.getEmail(),
                paciente.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    try{

                        String idUsuario = task.getResult().getUser().getUid();
                        paciente.setIdUsuario( idUsuario );
                        paciente.salvar();

                        //Atualizar nome no UserProfile
                        UsuarioFirebase.atualizarNomeUsuario( paciente.getNome() );

                        // Redireciona o usuário com base no seu tipo
                        // Se o usuário for passageiro chama a activity maps
                        // senão chama a activity requisicoes
                            startActivity(new Intent(CadastroActivity.this, HomeActivity.class ));
                            finish();

                            Toast.makeText(CadastroActivity.this,
                                    "Paciente cadastrado com sucesso!",
                                    Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        excecao= "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e){
                        excecao = "Este conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void cadastrarMedico(final Medico medico){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                medico.getEmail(),
                medico.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    try{

                        String idUsuario = task.getResult().getUser().getUid();
                        medico.setIdUsuario( idUsuario );
                        medico.salvar();

                        //Atualizar nome no UserProfile
                        UsuarioFirebase.atualizarNomeUsuario( medico.getNome() );

                        // Redireciona o usuário com base no seu tipo
                        // Se o usuário for passageiro chama a activity maps
                        // senão chama a activity requisicoes
                        startActivity(new Intent(CadastroActivity.this, HomeActivity.class ));
                        finish();

                        Toast.makeText(CadastroActivity.this,
                                "Médico cadastrado com sucesso!",
                                Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        excecao= "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e){
                        excecao = "Este conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public String verificaTipoUsuario(){
        return switchTipoUsuario.isChecked() ? "P" : "M" ;
    }

}
