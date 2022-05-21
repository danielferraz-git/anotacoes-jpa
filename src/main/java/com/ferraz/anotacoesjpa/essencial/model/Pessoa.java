package com.ferraz.anotacoesjpa.essencial.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Apenas duas anotações são obrigatórias para definir uma entidade JPA.
 * @Entity e @Id (Id representa a chave primária da tabela)
 */
@Entity
public class Pessoa {

    @Id
    private Integer id;
    private String nome;

    //É necessário que exista pelo menos
    //um construtor publico sem parametros.
    public Pessoa(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    //Geralmente equals e hashcode são construidos com base na chave primária,
    //visto que ela será única na tabela do banco.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
