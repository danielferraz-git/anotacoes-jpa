package com.ferraz.anotacoesjpa.essencial.model;

import javax.persistence.*;

@Entity
public class DadosAcesso {

    @Id
    private Integer id;

    private String email;
    private String cpf;
    private String telefone;

    @OneToOne(optional = false)
    @MapsId
    private Conta conta;

    public DadosAcesso() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
}
