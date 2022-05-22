package com.ferraz.anotacoesjpa.essencial.model;

import javax.persistence.*;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeProprietario;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "conta", orphanRemoval = true)
    private DadosAcesso dadosAcesso;

    public Conta() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public DadosAcesso getDadosAcesso() {
        return dadosAcesso;
    }

    public void setDadosAcesso(DadosAcesso dadosAcesso) {
        this.dadosAcesso = dadosAcesso;
    }
}
