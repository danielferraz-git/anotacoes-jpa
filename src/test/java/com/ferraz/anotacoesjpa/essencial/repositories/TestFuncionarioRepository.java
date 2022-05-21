package com.ferraz.anotacoesjpa.essencial.repositories;

import com.ferraz.anotacoesjpa.essencial.model.Funcionario;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TestFuncionarioRepository {

    @Autowired
    private FuncionarioRepository repository;

    @Test
    @Order(1)
    void deveSalvarUmFuncionario(){
        //Não precisa setar funcionario.id porque é auto incremento.
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf("123.456.789.11");
        funcionario.setNome("João Silva");
        assertDoesNotThrow(()->repository.save(funcionario));
    }

    @Test
    @Order(2)
    void naoDeveAceitarOMesmoCpfDuasVezes(){
        //Este método só irá lançar erro(passar no teste) se for executado após o @Order(1)
        assertDoesNotThrow(()->{
            Funcionario funcionario = new Funcionario();
            funcionario.setCpf("123.456.789.11");
            funcionario.setNome("Marcos Silva");
            assertThrows(DataIntegrityViolationException.class, ()->repository.save(funcionario));
        });
    }

    @Test
    @Order(3)
    void deveEncontrarOPrimeiroFuncionarioInserido(){
        assertDoesNotThrow(()->{
            Funcionario funcionario = repository.findById(1).orElseThrow(NullPointerException::new);
            assertNotNull(funcionario);
            System.out.println(funcionario);
        });
    }
}
