package com.projetofinal.ancea.data.model;

public class Ocorrencias {

    private String data;
    private String tipoCrise;
    private String tempoSono;
    private String estadoEmocional;
    private String exposicaoDispositivos;
    private String observacoesExtras;

    public String getTipoCrise() {
        return tipoCrise;
    }

    public void setTipoCrise(String tipoCrise) {
        this.tipoCrise = tipoCrise;
    }

    public String getTempoSono() {
        return tempoSono;
    }

    public void setTempoSono(String tempoSono) {
        this.tempoSono = tempoSono;
    }

    public String getEstadoEmocional() {
        return estadoEmocional;
    }

    public void setEstadoEmocional(String estadoEmocional) {
        this.estadoEmocional = estadoEmocional;
    }

    public String getExposicaoDispositivos() {
        return exposicaoDispositivos;
    }

    public void setExposicaoDispositivos(String exposicaoDispositivos) {
        this.exposicaoDispositivos = exposicaoDispositivos;
    }

    public String getObservacoesExtras() {
        return observacoesExtras;
    }

    public void setObservacoesExtras(String observacoesExtras) {
        this.observacoesExtras = observacoesExtras;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
