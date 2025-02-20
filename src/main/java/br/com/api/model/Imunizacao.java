package br.com.api.model;

import java.util.Date;

public class Imunizacao {
    private long id;
    private long idPaciente;
    private long idDose;
    private Date dataAplicacao;
    private String fabricante;
    private String lote;
    private String localAplicacao;
    private String profissionalAplicador;

    public Imunizacao(){}

    public Imunizacao(Long id, Long idPaciente, Long idDose, Date dataAplicacao, 
        String fabricante, String lote, String localAplicacao, String profissionalAplicador) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idDose = idDose;
        this.dataAplicacao = dataAplicacao;
        this.fabricante = fabricante;
        this.lote = lote;
        this.localAplicacao = localAplicacao;
        this.profissionalAplicador = profissionalAplicador;
    }

    public Imunizacao(Long idPaciente, Long idDose, Date dataAplicacao, 
    String fabricante, String lote, String localAplicacao, String profissionalAplicador) {
    this.idPaciente = idPaciente;
    this.idDose = idDose;
    this.dataAplicacao = dataAplicacao;
    this.fabricante = fabricante;
    this.lote = lote;
    this.localAplicacao = localAplicacao;
    this.profissionalAplicador = profissionalAplicador;
}

    public Imunizacao(Date dataAplicacao, 
        String fabricante, String lote, String localAplicacao, String profissionalAplicador) {
        this.dataAplicacao = dataAplicacao;
        this.fabricante = fabricante;
        this.lote = lote;
        this.localAplicacao = localAplicacao;
        this.profissionalAplicador = profissionalAplicador;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public long getIdDose() {
        return idDose;
    }

    public void setIdDose(long idDose) {
        this.idDose = idDose;
    }

    public Date getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(Date dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getLocalAplicacao() {
        return localAplicacao;
    }

    public void setLocalAplicacao(String localAplicacao) {
        this.localAplicacao = localAplicacao;
    }

    public String getProfissionalAplicador() {
        return profissionalAplicador;
    }

    public void setProfissionalAplicador(String profissionalAplicador) {
        this.profissionalAplicador = profissionalAplicador;
    }
}

