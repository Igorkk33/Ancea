package com.projetofinal.ancea.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.projetofinal.ancea.R;
import com.projetofinal.ancea.activity.MedicoActivity;
import com.projetofinal.ancea.activity.PacienteActivity;
import com.projetofinal.ancea.data.model.Usuario;
import com.projetofinal.ancea.helper.ConfiguracaoFirebase;
import com.projetofinal.ancea.helper.UsuarioFirebase;

import java.util.Objects;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText campoNome, campoEmail, campoSenha;
    private SwitchCompat switchTipoUsuario;
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
        String textoNome  = Objects.requireNonNull(campoNome.getText()).toString();
        String textoEmail = Objects.requireNonNull(campoEmail.getText()).toString();
        String textoSenha = Objects.requireNonNull(campoSenha.getText()).toString();

        if( !textoNome.isEmpty() ) {//verifica nome
            if( !textoEmail.isEmpty() ) {//verifica e-mail
                if( !textoSenha.isEmpty() ) {//verifica senha

                    Usuario usuario = new Usuario();
                    usuario.setNome( textoNome );
                    usuario.setEmail( textoEmail );
                    usuario.setSenha( textoSenha );


                    cadastrarUsuario( usuario );

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

    public void cadastrarUsuario(final Usuario usuario){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    try{

                        String idUsuario = Objects.requireNonNull(task.getResult().getUser()).getUid();
                        usuario.setIdUsuario( idUsuario );
                        usuario.salvar();

                        //Atualizar nome no UserProfile
                        UsuarioFirebase.atualizarNomeUsuario( usuario.getNome() );

                        // Redireciona o usuário com base no seu tipo
                        // Se o usuário for passageiro chama a activity maps
                        // senão chama a activity requisicoes
                        if(verificaTipoUsuario().equals("Paciente")){
                            usuario.setTipo("Paciente");
                            usuario.salvar();
                            startActivity(new Intent(CadastroActivity.this, PacienteActivity.class ));
                            finish();

                            Toast.makeText(CadastroActivity.this,
                                    "Sucesso ao cadastrar Paciente!",
                                    Toast.LENGTH_SHORT).show();

                        }else {
                            usuario.setTipo("Médico");
                            usuario.salvar();
                            startActivity(new Intent(CadastroActivity.this, MedicoActivity.class ));
                            finish();

                            Toast.makeText(CadastroActivity.this,
                                    "Sucesso ao cadastrar Médico!",
                                    Toast.LENGTH_SHORT).show();
                        }

                            Toast.makeText(CadastroActivity.this,
                                    "Usuário cadastrado com sucesso!",
                                    Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {

                    String excecao = "";
                    try {
                        throw Objects.requireNonNull(task.getException());
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
        return switchTipoUsuario.isChecked() ? "Médico" : "Paciente" ;
    }


}
