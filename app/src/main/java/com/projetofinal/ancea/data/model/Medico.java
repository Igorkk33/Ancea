package com.projetofinal.ancea.data.model;

public class Medico extends Usuario {

    private Paciente idpaciente;

    public Medico(){

    }

    @Override
    public void salvar() {
        super.salvar();

    }

    public Paciente getIdpaciente() {
        return idpaciente;
    }

    public void setIdpaciente(Paciente idpaciente) {
        this.idpaciente = idpaciente;
    }
}
