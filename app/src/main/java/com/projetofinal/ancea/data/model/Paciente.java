package com.projetofinal.ancea.data.model;

public class Paciente extends Usuario {

    private Medico medico;

    public Paciente(){

    }

    @Override
    public void salvar() {
        super.salvar();
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}
