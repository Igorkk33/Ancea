package com.projetofinal.ancea.data.model;

import com.google.firebase.database.DatabaseReference;
import com.projetofinal.ancea.helper.ConfiguracaoFirebase;

import java.util.HashMap;
import java.util.Map;

public class Medico extends Usuario {

    private Paciente idPaciente;

    public Medico(){

    }

    public void salvar() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference usuarios = firebaseRef.child( "users" );
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("nome",getNome());
        map.put("idUsuario",getIdUsuario());
        map.put("email",getEmail());
        map.put("senha",getSenha());
        usuarios.setValue(map);
    }

    public Paciente getIdpaciente() {
        return idPaciente;
    }

    public void setIdpaciente(Paciente idpaciente) {
        this.idPaciente = idPaciente;
    }
}
