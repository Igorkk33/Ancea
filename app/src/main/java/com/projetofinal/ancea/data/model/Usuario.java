package com.projetofinal.ancea.data.model;

import com.google.firebase.database.DatabaseReference;
import com.projetofinal.ancea.helper.ConfiguracaoFirebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {

    private String idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String tipo;
    private Usuario vinculacao;
    private List<Ocorrencias> ocorrencias = new ArrayList<>();

    public Usuario(){

    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference usuarios = firebaseRef.child("usuarios");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("idUsuario",getIdUsuario());
        map.put("nome",getNome());
        map.put("email",getEmail());
        map.put("senha",getSenha());
        map.put("tipo",getTipo());
        map.put("vinculacao",null);
        usuarios.push().setValue(map);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getVinculacao() {
        return vinculacao;
    }

    public void setVinculacao(Usuario vinculacao) {
        this.vinculacao = vinculacao;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Ocorrencias> getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(List<Ocorrencias> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }
}
