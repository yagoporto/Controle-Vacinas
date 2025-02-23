package br.com.api.model;

import java.util.Date;

public class Imunizacao {
    private int id;
    private int id_paciente;
    private int id_dose;
    private Date data_aplicacao;
    private String fabricante;
    private String lote;
    private String local_aplicacao;
    private String profissional_aplicador;

    public Imunizacao(){}

    public Imunizacao(int id, int id_paciente, int id_dose, Date data_aplicacao, 
        String fabricante, String lote, String local_aplicacao, String profissional_aplicador) {
        this.id = id;
        this.id_paciente = id_paciente;
        this.id_dose = id_dose;
        this.data_aplicacao = data_aplicacao;
        this.fabricante = fabricante;
        this.lote = lote;
        this.local_aplicacao = local_aplicacao;
        this.profissional_aplicador = profissional_aplicador;
    }

    public Imunizacao(int id_paciente, int id_dose, Date data_aplicacao, 
    String fabricante, String lote, String local_aplicacao, String profissional_aplicador) {
    this.id_paciente = id_paciente;
    this.id_dose = id_dose;
    this.data_aplicacao = data_aplicacao;
    this.fabricante = fabricante;
    this.lote = lote;
    this.local_aplicacao = local_aplicacao;
    this.profissional_aplicador = profissional_aplicador;
}

    public Imunizacao(Date data_aplicacao, 
        String fabricante, String lote, String local_aplicacao, String profissional_aplicador) {
        this.data_aplicacao = data_aplicacao;
        this.fabricante = fabricante;
        this.lote = lote;
        this.local_aplicacao = local_aplicacao;
        this.profissional_aplicador = profissional_aplicador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getid_paciente() {
        return id_paciente;
    }

    public void setid_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public int getid_dose() {
        return id_dose;
    }

    public void setid_dose(int id_dose) {
        this.id_dose = id_dose;
    }

    public Date getdata_aplicacao() {
        return data_aplicacao;
    }

    public void setdata_aplicacao(Date data_aplicacao) {
        this.data_aplicacao = data_aplicacao;
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

    public String getlocal_aplicacao() {
        return local_aplicacao;
    }

    public void setlocal_aplicacao(String local_aplicacao) {
        this.local_aplicacao = local_aplicacao;
    }

    public String getProfissional_Aplicador() {
        return profissional_aplicador;
    }

    public void setProfissional_Aplicador(String profissional_aplicador) {
        this.profissional_aplicador = profissional_aplicador;
    }
}
