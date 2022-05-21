package com.ferraz.anotacoesjpa.essencial.repositories;

import com.ferraz.anotacoesjpa.essencial.model.Pessoa;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class) //Para executar os métodos na ordem escolhida.
public class TestPessoaRepository {

    @Autowired
    private PessoaRepository repository;

    @Test
    @Order(1)
    void deveSalvarUmaPessoaNoBanco(){
        Pessoa p = new Pessoa();
        p.setId(1);
        p.setNome("João Silva");

        assertDoesNotThrow(()->repository.save(p));
    }

    @Test
    @Order(2)
    void deveObterAPessoaSalva(){
        assertDoesNotThrow(()->{
            Pessoa pessoa = repository.findById(1).orElseThrow(NullPointerException::new);
            assertEquals("João Silva", pessoa.getNome());
        });
    }

    @Test
    @Order(3)
    void deveAlterarONomeDaPessoa(){
        assertDoesNotThrow(()->{
            Pessoa pessoa = repository.findById(1).orElseThrow(NullPointerException::new);
            pessoa.setNome("Marcos Silva");
            repository.save(pessoa);
        });
    }
}
